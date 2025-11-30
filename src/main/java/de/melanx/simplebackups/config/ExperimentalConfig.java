package de.melanx.simplebackups.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ExperimentalConfig {

    public static final ModConfigSpec CONFIG;
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        init(BUILDER);
        CONFIG = BUILDER.build();
    }

    private static ModConfigSpec.BooleanValue enabled;
    private static ModConfigSpec.EnumValue<ExperimentalBackupType> backupType;
    private static ModConfigSpec.IntValue backupChainsToKeep;

    public static void init(ModConfigSpec.Builder builder) {
        enabled = builder.comment("Only enable if you want to test the experimental backup system.")
                .define("enabled", false);
        backupType = builder.comment("Defines the backup type.",
                        "- FULL_BACKUPS - every backup is a full backup.",
                        "- INCREMENTAL  - backup only files changed since the last backup",
                        "- DIFFERENTIAL - backup only files changed since the last full backup")
                .defineEnum("backupType", ExperimentalBackupType.INCREMENTAL);
        backupChainsToKeep = builder.comment("The max amount of backup chains to keep.")
                .defineInRange("backupChainsToKeep", 10, 1, Short.MAX_VALUE);
    }

    public static boolean isEnabled() {
        return enabled.get();
    }

    public static ExperimentalBackupType backupType() {
        return backupType.get();
    }

    public static int backupChainsToKeep() {
        return backupChainsToKeep.get();
    }

    public enum ExperimentalBackupType {
        FULL_BACKUPS(BackupType.FULL_BACKUPS),
        INCREMENTAL(BackupType.MODIFIED_SINCE_LAST),
        DIFFERENTIAL(BackupType.MODIFIED_SINCE_FULL);

        private final BackupType backupType;

        ExperimentalBackupType(BackupType backupType) {
            this.backupType = backupType;
        }

        public BackupType asLegacyType() {
            return this.backupType;
        }
    }
}
