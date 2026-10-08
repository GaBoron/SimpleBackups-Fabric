package de.melanx.simplebackups.restore;

import java.io.IOException;
import java.nio.file.Path;

final class ArchivePaths {

    private ArchivePaths() {}

    static Path relative(String worldId, String name) throws IOException {
        String normalized = name.replace('\\', '/');
        if (normalized.startsWith("/") || normalized.contains(":") || normalized.indexOf('\0') >= 0) {
            throw new IOException("Unsafe archive entry: " + name);
        }
        String[] parts = normalized.split("/");
        for (String part : parts) {
            if (part.equals("..") || part.equals(".") || part.isEmpty()) {
                throw new IOException("Unsafe archive entry: " + name);
            }
        }
        if (parts.length == 0 || !parts[0].equals(worldId)) {
            throw new IOException("Archive belongs to a different world: " + name);
        }
        return parts.length == 1 ? Path.of("") : Path.of(normalized.substring(worldId.length() + 1));
    }

    static Path destination(Path output, String worldId, String entry) throws IOException {
        Path relative = relative(worldId, entry);
        Path target = output.resolve(relative).normalize();
        if (!target.startsWith(output) || relative.toString().equals("session.lock")) {
            return null;
        }
        return target;
    }
}
