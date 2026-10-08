package de.melanx.simplebackups.restore;

import de.melanx.simplebackups.compression.CompressionBase.BackupFormat;
import de.melanx.simplebackups.config.BackupType;

import java.nio.file.Path;
import java.util.List;

/** A snapshot and the ordered archives needed to reconstruct it. */
public record RestorePoint(String name, long timestamp, long size, BackupFormat format,
                           BackupType type, List<Path> archives) {

    public RestorePoint {
        archives = List.copyOf(archives);
        if (archives.isEmpty()) {
            throw new IllegalArgumentException("A restore point needs a full backup");
        }
    }
}
