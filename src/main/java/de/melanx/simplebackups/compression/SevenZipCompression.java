package de.melanx.simplebackups.compression;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZMethodConfiguration;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileStore;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class SevenZipCompression extends CompressionBase {

    public SevenZipCompression(FileStore fileStore, boolean doFullBackup, long lastSaved) {
        super(fileStore, doFullBackup, lastSaved);
    }

    @Override
    public void makeBackup(Path levelName, Path levelPath, Path outputFile) throws IOException {
        try (SevenZOutputFile sevenZOutput = new SevenZOutputFile(outputFile.toFile())) {
            sevenZOutput.setContentMethods(List.of(new SevenZMethodConfiguration(SevenZMethod.LZMA2, CompressionBase.createXzOptions())));

            Files.walkFileTree(levelPath, new CompressionFileVisitor(levelPath) {

                @Nonnull
                @Override
                public FileVisitResult visitFile_(@Nonnull Path file, @Nonnull BasicFileAttributes attrs) throws IOException {
                    String entryName = this.getNormalizedPath(levelName.resolve(levelPath.relativize(file)));
                    SevenZArchiveEntry entry = sevenZOutput.createArchiveEntry(file.toFile(), entryName);
                    sevenZOutput.putArchiveEntry(entry);
                    try (InputStream in = Files.newInputStream(file)) {
                        byte[] buffer = new byte[65536];
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            sevenZOutput.write(buffer, 0, read);
                        }
                    } catch (IOException e) {
                        this.visitFileFailed(file, e);
                    } finally {
                        sevenZOutput.closeArchiveEntry();
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    @Override
    public String getExtension() {
        return BackupFormat.SEVEN_ZIP.getExtension();
    }
}
