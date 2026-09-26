/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups.compat;

import de.melanx.simplebackups.SimpleBackups;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;

public class Mc2DiscordCompat {

    public static void announce(Component text) {
        if (!isLoaded()) {
            return;
        }

        try {
            Class<?> manager = Class.forName("fr.denisd3d.mc2discord.core.MessageManager");
            Method send = manager.getMethod("sendInfoMessage", String.class, String.class);
            Object completion = send.invoke(null, SimpleBackups.MODID, text.getString());
            if (completion != null) {
                completion.getClass().getMethod("subscribe").invoke(completion);
            }
        } catch (ReflectiveOperationException | LinkageError e) {
            SimpleBackups.LOGGER.warn("Mc2Discord is installed, but its announcement API is unavailable", e);
        }
    }

    public static boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded("mc2discord");
    }
}
