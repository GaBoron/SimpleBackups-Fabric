package de.melanx.simplebackups.restore;

import de.melanx.simplebackups.ToolsLoader;
import de.melanx.simplebackups.compression.CompressionBase.BackupFormat;
import de.melanx.simplebackups.sbk.SbkReader;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/** Reads backup archives without involving the running server or chain merger. */
public final class BackupArchives {

    private BackupArchives() {}

    public static boolean containsWorld(Path archive, BackupFormat format, String worldId) throws IOException {
        return switch (format) {
            case ZIP -> {
                try (ZipFile zip = new ZipFile(archive.toFile())) {
                    yield zip.getEntry(worldId + "/level.dat") != null;
                }
            }
            case SBK -> SbkReader.info(archive).entries().stream()
                    .anyMatch(entry -> entry.path().equals(worldId + "/level.dat"));
            case ZSTD -> {
                boolean found = false;
                try (TarArchiveInputStream tar = openTar(archive)) {
                    TarArchiveEntry entry;
                    while ((entry = tar.getNextEntry()) != null) {
                        if (entry.getName().equals(worldId + "/level.dat") && entry.isFile()) {
                            found = true;
                            break;
                        }
                    }
                }
                yield found;
            }
        };
    }

    public static void extract(Path archive, BackupFormat format, String worldId, Path output,
                               Consumer<String> progress) throws IOException {
        if (!Files.isRegularFile(archive) || Files.isSymbolicLink(archive)) {
            throw new IOException("Missing or unsafe backup: " + archive);
        }
        switch (format) {
            case ZIP -> extractZip(archive, worldId, output, progress);
            case ZSTD -> extractTar(archive, worldId, output, progress);
            case SBK -> extractSbk(archive, worldId, output, progress);
        }
    }

    private static void extractZip(Path archive, String worldId, Path output, Consumer<String> progress) throws IOException {
        try (ZipInputStream zip = new ZipInputStream(new BufferedInputStream(Files.newInputStream(archive)))) {
            java.util.zip.ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                Path target = ArchivePaths.destination(output, worldId, entry.getName());
                if (target == null) continue;
                if (entry.isDirectory()) {
                    Files.createDirectories(target);
                } else {
                    copy(zip, target);
                    if (entry.getLastModifiedTime() != null) {
                        Files.setLastModifiedTime(target, entry.getLastModifiedTime());
                    }
                    progress.accept(entry.getName());
                }
                zip.closeEntry();
            }
        }
    }

    private static void extractTar(Path archive, String worldId, Path output, Consumer<String> progress) throws IOException {
        try (TarArchiveInputStream tar = openTar(archive)) {
            TarArchiveEntry entry;
            while ((entry = tar.getNextEntry()) != null) {
                Path target = ArchivePaths.destination(output, worldId, entry.getName());
                if (target == null) continue;
                if (entry.isDirectory()) {
                    Files.createDirectories(target);
                } else if (entry.isFile() && !entry.isLink() && !entry.isSymbolicLink() && tar.canReadEntryData(entry)) {
                    copy(tar, target);
                    Files.setLastModifiedTime(target, FileTime.from(entry.getLastModifiedDate().toInstant()));
                    progress.accept(entry.getName());
                } else {
                    throw new IOException("Unsupported TAR entry: " + entry.getName());
                }
            }
        }
    }

    private static void extractSbk(Path archive, String worldId, Path output, Consumer<String> progress) throws IOException {
        var paths = new HashSet<String>();
        for (var entry : SbkReader.info(archive).entries()) {
            Path target = ArchivePaths.destination(output, worldId, entry.path());
            if (target != null) paths.add(entry.path());
        }
        // SBK paths include the original world folder; validation above precedes all writes.
        SbkReader.extract(archive, output.getParent(), paths,
                (completed, total, path) -> progress.accept(path));
    }

    private static TarArchiveInputStream openTar(Path archive) throws IOException {
        InputStream source = new BufferedInputStream(Files.newInputStream(archive));
        try {
            return new TarArchiveInputStream(ToolsLoader.wrapZstdInput(source));
        } catch (IOException | RuntimeException e) {
            source.close();
            throw e;
        }
    }

    private static void copy(InputStream input, Path target) throws IOException {
        Files.createDirectories(target.getParent());
        Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
