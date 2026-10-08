package de.melanx.simplebackups.restore;

import de.melanx.simplebackups.BackupChain;
import de.melanx.simplebackups.BackupChainManager;
import de.melanx.simplebackups.BackupRetention;
import de.melanx.simplebackups.compression.CompressionBase;
import de.melanx.simplebackups.config.BackupType;
import de.melanx.simplebackups.config.CommonConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/** Registers a compressed rollback snapshot as an ordinary full backup chain. */
final class ReplacementBackup {

    private ReplacementBackup() {}

    static Path create(Path world, String worldId, Consumer<String> progress) throws IOException {
        Path output = CommonConfig.getOutputPath(worldId).toAbsolutePath().normalize();
        if (output.startsWith(world.toRealPath())) throw new IOException("Backup directory is inside the world");
        Files.createDirectories(output);
        output = output.toRealPath();
        if (output.startsWith(world.toRealPath())) throw new IOException("Backup directory is inside the world");
        String name = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + "-rollback";
        Path directory = output.resolve(name);
        for (int number = 2; Files.exists(directory); number++) directory = output.resolve(name + "-" + number);
        Files.createDirectory(directory);
        var format = CommonConfig.getBackupFormat();
        BackupChain chain = new BackupChain(directory, Path.of("full" + format.getExtension()), BackupType.FULL_BACKUPS, format);
        Path partial = directory.resolve("full" + format.getExtension() + ".partial");
        try {
            progress.accept("rollback");
            var result = CompressionBase.makeFullBackup(world, worldId, partial, format);
            if (result.hasErrors() || !BackupArchives.containsWorld(partial, format, worldId)) {
                throw new IOException("Rollback backup is incomplete; current world was not replaced");
            }
            Files.move(partial, chain.getFullBackup());
            chain.markBeforeRestore();
            chain.writeMetadataOrThrow();
        } catch (IOException | RuntimeException e) {
            try {
                RestoreWorkspace.discard(directory);
            } catch (IOException cleanupError) {
                e.addSuppressed(cleanupError);
            }
            throw e;
        }
        // Reload also invalidates the chain cache used when this world next starts.
        BackupChainManager.get(worldId).reloadAllChains();
        return chain.getFullBackup();
    }

    static void applyRetention(String worldId) {
        BackupChainManager manager = BackupChainManager.get(worldId);
        manager.reloadAllChains();
        BackupRetention.apply(manager);
    }
}
