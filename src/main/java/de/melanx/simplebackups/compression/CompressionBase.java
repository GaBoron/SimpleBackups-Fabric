package de.melanx.simplebackups.compression;

import de.melanx.simplebackups.BackupResult;
import de.melanx.simplebackups.SimpleBackups;
import de.melanx.simplebackups.ToolsLoader;
import de.melanx.simplebackups.config.CommonConfig;
import de.melanx.simplebackups.exception.NotEnoughDiskSpaceException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.tukaani.xz.LZMA2Options;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public abstract class CompressionBase {

    public static final long BACKUP_BUFFER_SIZE = 128L * 1024 * 1024; // 128 MB
    protected final List<Path> errors = new ArrayList<>();
    protected final FileStore fileStore;
    protected final boolean doFullBackup;
    protected final long lastSaved;

    public CompressionBase(FileStore fileStore, boolean doFullBackup, long lastSaved) {
        this.fileStore = fileStore;
        this.doFullBackup = doFullBackup;
        this.lastSaved = lastSaved;
    }

    public static BackupResult makeBackup(MinecraftServer server, LevelStorageSource.LevelStorageAccess storageAccess, Path backupPath, Path backupFilePath, boolean doFullBackup, BackupFormat format, long lastSaved) throws IOException {
        storageAccess.checkLock();
        if (CommonConfig.saveAll()) {
            server.executeBlocking(() -> server.saveEverything(true, false, true));
        }

        FileStore fileStore = Files.getFileStore(backupPath);
        CompressionBase compressor = switch(format) {
            case ZIP -> new ZipCompression(fileStore, doFullBackup, lastSaved);
            case SEVEN_ZIP -> new SevenZipCompression(fileStore, doFullBackup, lastSaved);
            case ZSTD -> {
                if (!ZstdCompression.isAvailable()) {
                    throw new IOException("ZSTD compression is selected but zstd-jni is not installed. Download it and place it in " + ToolsLoader.RELATIVE_TOOLS_DIR + ".");
                }
                yield new ZstdCompression(fileStore, doFullBackup, lastSaved);
            }
        };

        Path levelName = Paths.get(storageAccess.getLevelId());
        Path levelPath = storageAccess.getWorldDir().resolve(storageAccess.getLevelId()).toRealPath();

        compressor.makeBackup(levelName, levelPath, backupFilePath);

        return new BackupResult(backupFilePath, Files.size(backupFilePath), compressor.errors);
    }

    public abstract void makeBackup(Path levelName, Path levelPath, Path outputFile) throws IOException;

    public abstract String getExtension();

    protected static LZMA2Options createXzOptions() {
        int lvl = CommonConfig.getCompressionLevel();
        int preset = Math.max(0, Math.min(9, lvl));
        try {
            LZMA2Options opt = new LZMA2Options();
            opt.setPreset(preset);
            return opt;
        } catch (Exception e) {
            return new LZMA2Options();
        }
    }

    protected abstract class CompressionFileVisitor extends SimpleFileVisitor<Path> {

        protected final List<Path> ignoredPaths = CommonConfig.getIgnoredPaths();
        protected final List<Path> ignoredFiles = CommonConfig.getIgnoredFiles();
        protected final String ignoredFilesRegex = CommonConfig.getIgnoredFilesRegex();
        protected final boolean ignoreSomething = !this.ignoredPaths.isEmpty() || !this.ignoredFiles.isEmpty() || !this.ignoredFilesRegex.isEmpty();
        private final Path levelPath;

        public CompressionFileVisitor(Path levelPath) {
            this.levelPath = levelPath;
        }

        @Nonnull
        @Override
        public FileVisitResult preVisitDirectory(@Nonnull Path dir, @Nonnull BasicFileAttributes attrs) {
            if (dir.equals(this.levelPath)) return FileVisitResult.CONTINUE;

            Path rel = this.levelPath.relativize(dir);

            // skip the whole directory subtree if configured
            if (this.ignoreSomething && this.shouldSkipDirectory(rel)) {
                SimpleBackups.LOGGER.debug("Skipping directory: {}", dir);
                return FileVisitResult.SKIP_SUBTREE;
            }

            return FileVisitResult.CONTINUE;
        }

        @Nonnull
        @Override
        public final FileVisitResult visitFile(@Nonnull Path file, @Nonnull BasicFileAttributes attrs) throws IOException {
            if (file.endsWith("session.lock")) {
                return FileVisitResult.CONTINUE;
            }

            if (file.endsWith("biomancy.spatial.db")) {
                SimpleBackups.LOGGER.info("Skipping \"{}\" - see https://github.com/Elenterius/Biomancy/issues/175", this.levelPath.relativize(file));
                return FileVisitResult.CONTINUE;
            }

            if (this.ignoreSomething && this.shouldSkipFile(this.levelPath.relativize(file))) {
                SimpleBackups.LOGGER.debug("Skipping file: {}", file);
                return FileVisitResult.CONTINUE;
            }

            long lastModified = attrs.lastModifiedTime().toMillis();
            if (CompressionBase.this.doFullBackup || lastModified - CompressionBase.this.lastSaved > 0) {
                if (CompressionBase.this.fileStore.getUsableSpace() - attrs.size() - BACKUP_BUFFER_SIZE < 0L) {
                    throw new NotEnoughDiskSpaceException("Not enough space on disk to create backup");
                }

                return this.visitFile_(file, attrs);
            }

            return FileVisitResult.CONTINUE;
        }

        protected abstract FileVisitResult visitFile_(@Nonnull Path file, @Nonnull BasicFileAttributes attrs) throws IOException;

        @Nonnull
        @Override
        public FileVisitResult visitFileFailed(@Nonnull Path file, @Nonnull IOException exc) throws IOException {
            if (exc instanceof NoSuchFileException || exc instanceof FileNotFoundException) {
                SimpleBackups.LOGGER.debug("Skipped vanished file: {}", file);
                return FileVisitResult.CONTINUE;
            }

            if (CommonConfig.collectErrors()) {
                SimpleBackups.LOGGER.error("Failed to backup file: {}", file, exc);
                CompressionBase.this.errors.add(this.levelPath.relativize(file));
                return FileVisitResult.CONTINUE;
            }

            IOException detailedException = new IOException("Failed to backup file: " + file, exc);
            return super.visitFileFailed(file, detailedException);
        }

        protected boolean shouldSkipFile(Path relativePath) {
            return this.ignoredPaths.contains(relativePath.getParent())
                    || this.ignoredFiles.contains(relativePath)
                    || (!this.ignoredFilesRegex.isEmpty() && this.getNormalizedPath(relativePath).matches(this.ignoredFilesRegex));
        }

        private boolean shouldSkipDirectory(Path relativeDir) {
            // If the directory itself is in ignoredPaths, skip its subtree
            return this.ignoredPaths.contains(relativeDir);
        }

        protected String getNormalizedPath(Path path) {
            return path.toString().replace('\\', '/');
        }
    }

    public enum BackupFormat {
        ZIP(".zip"),
        SEVEN_ZIP(".7z"),
        ZSTD(".tar.zst");

        private final String extension;

        BackupFormat(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return this.extension;
        }
    }
}
