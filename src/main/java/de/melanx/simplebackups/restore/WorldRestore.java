package de.melanx.simplebackups.restore;

import de.melanx.simplebackups.SimpleBackups;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.AccessDeniedException;
import java.util.function.Consumer;

/** Stages a restore, backing up the closed current world before replacement. */
public final class WorldRestore {

    private WorldRestore() {}

    public enum Mode { COPY, REPLACE }

    public record Result(Path world, Path previousBackup) {}

    @FunctionalInterface
    public interface WorldValidation {
        void validate(Path world, Mode mode) throws IOException;
    }

    public static Result restore(RestorePoint point, Path savesDirectory, String worldId, Mode mode,
                                 WorldValidation validation, Consumer<String> progress) throws IOException {
        Path saves = savesDirectory.toRealPath();
        Path source = saves.resolve(worldId).normalize();
        if (worldId.isBlank() || !source.getParent().equals(saves)
                || Files.isSymbolicLink(source) || !Files.isDirectory(source, LinkOption.NOFOLLOW_LINKS)) {
            throw new IOException("Unsafe world directory: " + source);
        }
        Path operationLock = saves.resolve(".simplebackups-restore.lock");
        if (Files.isSymbolicLink(operationLock)) throw new IOException("Unsafe restore lock");
        try (FileChannel operation = FileChannel.open(operationLock, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            FileLock lock = operation.tryLock();
            if (lock == null) throw new IOException("Another restore is in progress");
            try (lock) {
                return restoreLocked(point, saves, source, worldId, mode, validation, progress);
            }
        }
    }

    private static Result restoreLocked(RestorePoint point, Path saves, Path source, String worldId, Mode mode,
                                        WorldValidation validation, Consumer<String> progress) throws IOException {
        try (RestoreWorkspace workspace = new RestoreWorkspace(saves)) {
            return install(point, saves, source, worldId, mode, validation, progress, workspace);
        }
    }

    private static Result install(RestorePoint point, Path saves, Path source, String worldId, Mode mode,
                                  WorldValidation validation, Consumer<String> progress, RestoreWorkspace workspace) throws IOException {
        Path prepared = workspace.prepare(worldId);
        Path previousBackup;
        Path lockPath = source.resolve("session.lock");
        if (Files.isSymbolicLink(lockPath)) throw new IOException("Unsafe world lock");

        // Hold Minecraft's session lock throughout extraction and validation.
        try (FileChannel channel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            FileLock lock = channel.tryLock();
            if (lock == null) throw new IOException("World is currently open");
            try (lock) {
                for (Path archive : point.archives()) {
                    progress.accept(archive.getFileName().toString());
                    BackupArchives.extract(archive, point.format(), worldId, prepared, progress);
                }
                if (!Files.isRegularFile(prepared.resolve("level.dat")) || Files.size(prepared.resolve("level.dat")) == 0) {
                    throw new IOException("Backup has no level.dat");
                }
                validation.validate(prepared, mode);
                if (mode == Mode.COPY) {
                    Path destination = availableCopyPath(saves, point.restoredName(worldId));
                    moveWorld(prepared, destination);
                    return new Result(destination, null);
                }
                // Any compression, metadata or file-read failure must leave the current world intact.
                previousBackup = ReplacementBackup.create(source, worldId, progress);
            }
        } catch (IOException | RuntimeException e) {
            throw new IOException("Restore preparation failed; current world was not replaced", e);
        }

        // Windows cannot rename a directory while its session.lock is open.
        Path previous = workspace.previousWorld();
        moveWorld(source, previous);
        try {
            moveWorld(prepared, source);
        } catch (IOException installError) {
            Path retained = previous;
            try {
                moveWorld(previous, source);
                retained = source;
            } catch (IOException rollbackError) {
                installError.addSuppressed(rollbackError);
                workspace.preserveOriginal();
            }
            throw new IOException("Restore installation failed; original world retained at " + retained, installError);
        }
        // The selected archives are no longer needed, so pruning cannot invalidate this restore.
        try {
            ReplacementBackup.applyRetention(worldId);
        } catch (RuntimeException e) {
            SimpleBackups.LOGGER.warn("Could not apply backup retention for {}", worldId, e);
        }
        return new Result(source, previousBackup);
    }

    private static Path availableCopyPath(Path saves, String name) {
        Path destination = saves.resolve(name);
        for (int number = 2; Files.exists(destination, LinkOption.NOFOLLOW_LINKS); number++) {
            destination = saves.resolve(name + "-" + number);
        }
        return destination;
    }

    private static void moveWorld(Path source, Path destination) throws IOException {
        for (int attempt = 0; ; attempt++) {
            try {
                Files.move(source, destination);
                return;
            } catch (AccessDeniedException e) {
                // Windows readers can briefly hold freshly written world files open.
                if (attempt >= 10 || Files.exists(destination) || !Files.exists(source)) throw e;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw new IOException("World restoration interrupted", interrupted);
                }
            }
        }
    }
}
