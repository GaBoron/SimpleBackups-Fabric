package de.melanx.simplebackups.restore;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.melanx.simplebackups.compression.CompressionBase.BackupFormat;
import de.melanx.simplebackups.config.BackupType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** A read-only catalog, independent of the server's mutable chain cache. */
public final class BackupCatalog {

    private BackupCatalog() {}

    public record Result(List<RestorePoint> points, List<String> problems) {
        public Result {
            points = List.copyOf(points);
            problems = List.copyOf(problems);
        }
    }

    public static Result load(Path directory, String worldId) throws IOException {
        var points = new ArrayList<RestorePoint>();
        var problems = new ArrayList<String>();
        if (!Files.isDirectory(directory)) return new Result(points, problems);
        try (var entries = Files.list(directory)) {
            for (Path entry : entries.sorted().toList()) {
                try {
                    if (Files.isDirectory(entry, LinkOption.NOFOLLOW_LINKS)
                            && Files.isRegularFile(entry.resolve("metadata.json"), LinkOption.NOFOLLOW_LINKS)) {
                        readChain(entry, worldId, points, problems);
                    } else if (Files.isRegularFile(entry, LinkOption.NOFOLLOW_LINKS)) {
                        BackupFormat format = formatOf(entry);
                        if (format != null && BackupArchives.containsWorld(entry, format, worldId)) {
                            points.add(point(entry.getFileName().toString(), format, BackupType.FULL_BACKUPS, List.of(entry)));
                        }
                    }
                } catch (IOException | RuntimeException e) {
                    problems.add(entry + ": " + e.getMessage());
                }
            }
        }
        points.sort(Comparator.comparingLong(RestorePoint::timestamp).reversed());
        return new Result(points, problems);
    }

    private static void readChain(Path directory, String worldId, List<RestorePoint> points, List<String> problems) throws IOException {
        JsonObject json;
        try (var reader = Files.newBufferedReader(directory.resolve("metadata.json"))) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        }
        BackupType type = BackupType.valueOf(json.get("backupType").getAsString());
        BackupFormat format = json.has("format")
                ? BackupFormat.valueOf(json.get("format").getAsString().toUpperCase(Locale.ROOT)) : BackupFormat.ZIP;
        Path full = archivePath(directory, json.get("fullBackup").getAsString());
        if (!BackupArchives.containsWorld(full, format, worldId)) return;
        var chain = new ArrayList<Path>();
        chain.add(full);
        String name = directory.getFileName().toString();
        boolean beforeRestore = json.has("beforeRestore") && json.get("beforeRestore").getAsBoolean();
        points.add(point(name + "/" + full.getFileName(), format, BackupType.FULL_BACKUPS, chain, beforeRestore));
        for (var child : json.getAsJsonArray("children")) {
            try {
                Path archive = archivePath(directory, child.getAsString());
                if (type == BackupType.INCREMENTAL) {
                    chain.add(archive);
                } else if (type == BackupType.DIFFERENTIAL) {
                    chain = new ArrayList<>(List.of(full, archive));
                } else {
                    throw new IOException("Unexpected children in a full backup chain");
                }
                points.add(point(name + "/" + archive.getFileName(), format, type, chain));
            } catch (IOException | RuntimeException e) {
                problems.add(directory + ": " + e.getMessage());
                // Incremental snapshots require every predecessor; differential ones do not.
                if (type != BackupType.DIFFERENTIAL) break;
            }
        }
    }

    private static Path archivePath(Path directory, String filename) throws IOException {
        Path relative = Path.of(filename);
        Path path = directory.resolve(relative).normalize();
        if (relative.isAbsolute() || !path.startsWith(directory.normalize())
                || !Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)
                || !path.toRealPath().startsWith(directory.toRealPath())) {
            throw new IOException("Missing or unsafe chain archive: " + filename);
        }
        return path;
    }

    private static RestorePoint point(String name, BackupFormat format, BackupType type, List<Path> archives) throws IOException {
        return point(name, format, type, archives, false);
    }

    private static RestorePoint point(String name, BackupFormat format, BackupType type, List<Path> archives, boolean beforeRestore) throws IOException {
        long size = 0;
        for (Path archive : archives) size += Files.size(archive);
        return new RestorePoint(name, Files.getLastModifiedTime(archives.getLast()).toMillis(), size, format, type, archives, beforeRestore);
    }

    private static BackupFormat formatOf(Path path) {
        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
        for (BackupFormat format : BackupFormat.values()) {
            if (name.endsWith(format.getExtension())) return format;
        }
        return null;
    }
}
