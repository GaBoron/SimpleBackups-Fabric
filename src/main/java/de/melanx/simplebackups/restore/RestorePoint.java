package de.melanx.simplebackups.restore;

import de.melanx.simplebackups.compression.CompressionBase.BackupFormat;
import de.melanx.simplebackups.config.BackupType;

import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** A snapshot and the ordered archives needed to reconstruct it. */
public record RestorePoint(String name, long timestamp, long size, BackupFormat format,
                           BackupType type, List<Path> archives, boolean beforeRestore) {

    private static final DateTimeFormatter NAME_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
            .withZone(ZoneId.systemDefault());

    public RestorePoint {
        archives = List.copyOf(archives);
        if (archives.isEmpty()) {
            throw new IllegalArgumentException("A restore point needs a full backup");
        }
    }

    public RestorePoint(String name, long timestamp, long size, BackupFormat format, BackupType type, List<Path> archives) {
        this(name, timestamp, size, format, type, archives, false);
    }

    public String restoredName(String originalName) {
        return originalName + "-" + NAME_TIME.format(Instant.ofEpochMilli(this.timestamp));
    }
}
