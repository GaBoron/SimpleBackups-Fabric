package de.melanx.simplebackups.client.restore;

import de.melanx.simplebackups.restore.RestorePoint;
import de.melanx.simplebackups.restore.WorldRestore;
import de.melanx.simplebackups.config.CommonConfig;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.LevelSummary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.function.Consumer;

/** Minecraft-specific checks and the display name for a restored copy. */
final class RestoreWorldAction {

    private RestoreWorldAction() {}

    static WorldRestore.Result restore(Path saves, LevelSummary world, RestorePoint point, WorldRestore.Mode mode,
                                       Consumer<String> progress) throws IOException {
        return WorldRestore.restore(point, saves, world.getLevelId(), mode, (prepared, restoreMode) -> {
            Path levelFile = prepared.resolve("level.dat");
            var tag = NbtIo.readCompressed(levelFile, NbtAccounter.create(100L * 1024 * 1024));
            var data = tag.getCompound("Data").orElseThrow(() -> new IOException("Invalid world data"));
            if (data.getIntOr("DataVersion", -1) > SharedConstants.getCurrentVersion().dataVersion().version()) {
                throw new IOException("Backup was created by a newer Minecraft version");
            }
            if (restoreMode == WorldRestore.Mode.COPY) {
                data.putString("LevelName", point.restoredName(world.getLevelName()));
                NbtIo.writeCompressed(tag, levelFile);
            }
            resetBackupHistory(prepared);
        }, progress);
    }

    private static void resetBackupHistory(Path world) throws IOException {
        Path file = world.resolve("dimensions/minecraft/overworld/data/simplebackups/data.dat");
        CompoundTag tag = Files.isRegularFile(file)
                ? NbtIo.readCompressed(file, NbtAccounter.create(1024 * 1024)) : new CompoundTag();
        CompoundTag data = tag.getCompoundOrEmpty("data");
        data.putLong("lastSaved", 0);
        // Both real-time and game-tick clocks must begin a fresh full chain after restoration.
        data.putLong("lastFullBackup", Long.MIN_VALUE);
        data.putBoolean("merging", false);
        data.putInt("backupsSinceLastPlayerJoined", 0);
        if (!data.contains("paused")) data.putBoolean("paused", false);
        if (!data.contains("usesTickCounter")) data.putBoolean("usesTickCounter", CommonConfig.useTickCounter());
        tag.put("data", data);
        tag.putInt("DataVersion", SharedConstants.getCurrentVersion().dataVersion().version());
        Files.createDirectories(file.getParent());
        NbtIo.writeCompressed(tag, file);
    }
}
