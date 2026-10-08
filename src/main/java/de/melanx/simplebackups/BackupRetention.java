/* Modified by Simple Backups Fabric in 2026; adapted from upstream SimpleBackups; Apache-2.0. */
package de.melanx.simplebackups;

import de.melanx.simplebackups.config.CommonConfig;

/** Shared chain-count and disk-space limits for server and offline backups. */
public final class BackupRetention {

    private BackupRetention() {}

    public static void limitChainCount(BackupChainManager manager) {
        while (manager.getChains().size() > CommonConfig.getBackupsToKeep()) {
            BackupChain chain = manager.getFirstChain();
            SimpleBackups.LOGGER.info("Deleting backup chain directory \"{}\"", chain.getParentFolder());
            manager.removeChain(chain);
        }
    }

    public static void limitStorageSize(BackupChainManager manager) {
        try {
            while (manager.getFileSize() > CommonConfig.getMaxDiskSize()) {
                var chains = manager.getChains();
                if (chains.size() <= 1) {
                    SimpleBackups.LOGGER.error("Cannot delete old chains to save disk space. Only one chain directory left!");
                    return;
                }
                BackupChain victim = chains.getFirst();
                SimpleBackups.LOGGER.info("Deleting backup chain directory \"{}\" to save disk space", victim.getParentFolder());
                manager.removeChain(victim);
            }
        } catch (NullPointerException e) {
            SimpleBackups.LOGGER.error("Cannot delete old files to save disk space", e);
        }
    }

    public static void apply(BackupChainManager manager) {
        limitChainCount(manager);
        limitStorageSize(manager);
    }
}
