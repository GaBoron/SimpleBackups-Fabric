/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups;

import de.melanx.simplebackups.config.CommonConfig;
import de.melanx.simplebackups.config.ServerConfig;
import de.melanx.simplebackups.network.Pause;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleBackups implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(SimpleBackups.class);
    public static final String MODID = "simplebackups";

    @Override
    public void onInitialize() {
        ConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, CommonConfig.CONFIG, MODID + "/common.toml");
        ConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.SERVER, ServerConfig.CONFIG, MODID + "/server.toml");
        PayloadTypeRegistry.playS2C().register(Pause.TYPE, Pause.CODEC);
        EventListener.register();

        if (CommonConfig.backupsDisabledByJvmArg()) {
            LOGGER.info("##########################################");
            LOGGER.info("#  Backups are disabled by JVM argument  #");
            LOGGER.info("##########################################");
        }

    }
}
