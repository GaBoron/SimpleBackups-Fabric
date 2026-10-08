package de.melanx.simplebackups.restore;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Consumer;

/** Stages a restore before installing it, preserving the previous world for replacement. */
public final class WorldRestore {

    private WorldRestore() {}

    public enum Mode { COPY, REPLACE }

    public record Result(Path world, Path previousWorld) {}

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
        Path recovery = saves.getParent().resolve("simplebackups-restores");
        Files.createDirectories(recovery);
        if (Files.isSymbolicLink(recovery)) throw new IOException("Recovery directory must not be a symbolic link");
        Path operationLock = recovery.resolve("restore.lock");
        if (Files.isSymbolicLink(operationLock)) throw new IOException("Unsafe restore lock");
        try (FileChannel operation = FileChannel.open(operationLock, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            FileLock lock = operation.tryLock();
            if (lock == null) throw new IOException("Another restore is in progress");
            try (lock) {
                return restoreLocked(point, saves, source, worldId, mode, validation, progress, recovery);
            }
        }
    }

    private static Result restoreLocked(RestorePoint point, Path saves, Path source, String worldId, Mode mode,
                                        WorldValidation validation, Consumer<String> progress, Path recovery) throws IOException {
        String suffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
                + "-" + UUID.randomUUID().toString().substring(0, 8);
        Path transaction = Files.createDirectory(recovery.resolve(suffix));
        Path prepared = Files.createDirectories(transaction.resolve("prepared").resolve(worldId));
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
            }
        } catch (IOException | RuntimeException e) {
            throw new IOException("Restore preparation failed; staged files retained at " + transaction, e);
        }

        // Windows cannot rename a directory while its session.lock is open.
        Path previous = transaction.resolve("previous-world");
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
            }
            throw new IOException("Restore installation failed; original world retained at " + retained, installError);
        }
        return new Result(source, previous);
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
