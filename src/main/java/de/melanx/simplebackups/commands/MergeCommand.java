package de.melanx.simplebackups.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import de.melanx.simplebackups.BackupChain;
import de.melanx.simplebackups.BackupChainManager;
import de.melanx.simplebackups.BackupData;
import de.melanx.simplebackups.SimpleBackups;
import de.melanx.simplebackups.config.BackupType;
import de.melanx.simplebackups.config.CommonConfig;
import de.melanx.simplebackups.config.ExperimentalConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class MergeCommand implements Command<CommandSourceStack> {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("mergeBackups")
                .executes(new MergeCommand())
                .then(Commands.argument("chain", StringArgumentType.string()).suggests((stack, builder) -> {
                            String levelId = stack.getSource().getServer().storageSource.getLevelId();
                            BackupChainManager manager = BackupChainManager.get(levelId);

                            return SharedSuggestionProvider.suggest(manager.getChains().stream().map(chain -> chain.getParentFolder().getFileName().toString()), builder);
                        })
                        .executes(new MergeCommand())
                        .requires(stack -> ExperimentalConfig.isEnabled()));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
        // Check if only modified files should be backed up
        if (CommonConfig.backupType() == BackupType.FULL_BACKUPS) {
            throw new SimpleCommandExceptionType(Component.translatable("simplebackups.commands.only_modified")).create();
        }

        BackupData data = BackupData.get(commandContext.getSource().getServer());

        // Check if a merge operation is already in progress
        if (data.isMerging()) {
            throw new SimpleCommandExceptionType(Component.translatable("simplebackups.commands.is_merging")).create();
        }

        if (ExperimentalConfig.isEnabled()) {
            try {
                String chainName = commandContext.getArgument("chain", String.class);
                BackupChainManager manager = BackupChainManager.get(commandContext.getSource().getServer().storageSource.getLevelId());
                List<Path> zipFiles = new ArrayList<>();
                for (BackupChain chain : manager.getChains()) {
                    if (chain.getParentFolder().getFileName().toString().equals(chainName)) {
                        ExperimentalConfig.ExperimentalBackupType backupType = chain.getBackupType();
                        switch(backupType) {
                            case FULL_BACKUPS ->
                                    throw new SimpleCommandExceptionType(Component.translatable("simplebackups.commands.only_modified")).create();
                            case INCREMENTAL -> {
                                zipFiles.add(chain.getFullBackup());
                                zipFiles.addAll(chain.getChildren());
                            }
                            case DIFFERENTIAL -> {
                                zipFiles.add(chain.getFullBackup());
                                zipFiles.add(chain.getChildren().getLast());
                            }
                        }

                        MergingThread mergingThread = new MergingThread(zipFiles, commandContext);
                        data.startMerging();
                        mergingThread.start();
                    }
                }
            } catch (IllegalArgumentException e) {
                SimpleBackups.LOGGER.error("Invalid chain name: {}", commandContext.getArgument("chain", String.class), e);
                data.stopMerging();
                return 0;
            }

            data.stopMerging();
            return 1;
        }

        try {
            List<Path> backupSources = Files.list(CommonConfig.getOutputPath(commandContext.getSource().getServer().storageSource.getLevelId())).toList();
            MergingThread mergingThread = new MergingThread(backupSources, commandContext);
            data.startMerging();
            mergingThread.start();
        } catch (Exception e) {
            SimpleBackups.LOGGER.error("Failed to merge backups", e);
            data.stopMerging();
            return 0;
        }

        data.stopMerging();
        return 1;
    }

    private static class MergingThread extends Thread {

        private final List<Path> backupSources;
        private final CommandContext<CommandSourceStack> commandContext;

        public MergingThread(List<Path> backupSources, CommandContext<CommandSourceStack> commandContext) {
            this.backupSources = backupSources;
            this.commandContext = commandContext;
        }

        @Override
        public void run() {
            Path mainBackupsDir = CommonConfig.getOutputPath("ignore").getParent();
            Path mergedBackupPath = mainBackupsDir.resolve("merged_backup-" + UUID.randomUUID() + ".zip");
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(mergedBackupPath.toFile()))) {
                Map<String, Path> dataFiles = new HashMap<>();

                // Walk the file tree of the output path
                for (Path backupSource : this.backupSources) {
                    this.processFile(backupSource, dataFiles);
                }

                // Write the merged zip file
                this.writeMergedZipFile(zos, dataFiles);
            } catch (IOException e) {
                throw new IllegalStateException("Error while processing backups", e);
            } finally {
                this.commandContext.getSource().sendSuccess(() -> Component.translatable("simplebackups.commands.finished", mainBackupsDir.getParent().relativize(mergedBackupPath).toString()), false);
            }
        }

        private void processFile(Path file, Map<String, Path> zipFiles) throws IOException {
            if (file.toString().endsWith(".zip")) {
                try (ZipFile zipFile = new ZipFile(file.toFile())) {
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();

                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        String name = entry.getName();

                        zipFiles.merge(name, file, this::getLatestModifiedFile);
                    }
                }
            }
        }

        private Path getLatestModifiedFile(Path existingFile, Path newFile) {
            try {
                FileTime existingFileTime = Files.getLastModifiedTime(existingFile);
                FileTime newFileTime = Files.getLastModifiedTime(newFile);
                return existingFileTime.compareTo(newFileTime) > 0 ? existingFile : newFile;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private void writeMergedZipFile(ZipOutputStream zos, Map<String, Path> zipFiles) throws IOException {
            for (Map.Entry<String, Path> entry : zipFiles.entrySet()) {
                String fileName = entry.getKey();
                Path zipFilePath = entry.getValue();

                try (ZipFile zipFile = new ZipFile(zipFilePath.toFile())) {
                    ZipEntry zipEntry = zipFile.getEntry(fileName);
                    if (zipEntry != null) {
                        zos.putNextEntry(new ZipEntry(fileName));

                        try (InputStream is = zipFile.getInputStream(zipEntry)) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = is.read(buffer)) > 0) {
                                zos.write(buffer, 0, len);
                            }
                        }

                        zos.closeEntry();
                    }
                }
            }
        }
    }
}
