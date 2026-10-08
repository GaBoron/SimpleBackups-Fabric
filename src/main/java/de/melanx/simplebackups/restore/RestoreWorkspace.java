package de.melanx.simplebackups.restore;

import de.melanx.simplebackups.SimpleBackups;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/** Short-lived installation files; only a failed rollback retains an original world here. */
final class RestoreWorkspace implements AutoCloseable {

    private final Path directory;
    private boolean preserveOriginal;

    RestoreWorkspace(Path saves) throws IOException {
        this.directory = Files.createTempDirectory(saves, ".simplebackups-restore-");
    }

    Path prepare(String worldId) throws IOException {
        return Files.createDirectories(this.directory.resolve("prepared").resolve(worldId));
    }

    Path previousWorld() {
        return this.directory.resolve("previous-world");
    }

    void preserveOriginal() {
        this.preserveOriginal = true;
    }

    @Override
    public void close() {
        if (this.preserveOriginal) return;
        try {
            discard(this.directory);
        } catch (IOException e) {
            SimpleBackups.LOGGER.warn("Could not remove restore workspace {}", this.directory, e);
        }
    }

    static void discard(Path directory) throws IOException {
        // The caller passes only its freshly created workspace or incomplete backup chain.
        // walkFileTree does not follow links outside that directory.
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException error) throws IOException {
                if (error != null) throw error;
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
