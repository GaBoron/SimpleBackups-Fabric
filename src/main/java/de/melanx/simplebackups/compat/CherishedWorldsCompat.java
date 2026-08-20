/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups.compat;

import de.melanx.simplebackups.config.CommonConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.Method;

public class CherishedWorldsCompat {

    public static boolean isFavorite(String worldName) {
        if (!CommonConfig.onlyFavorites() || !isLoaded()) {
            return true;
        }

        // Favorites are stored on the physical client. Dedicated servers have no equivalent
        // list, so the compatibility filter intentionally becomes a no-op there.
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return true;
        }

        try {
            Class<?> favorites = Class.forName("com.illusivesoulworks.cherishedworlds.client.favorites.FavoritesList");
            Method contains = favorites.getMethod("contains", String.class);
            return (boolean) contains.invoke(null, worldName);
        } catch (ReflectiveOperationException | LinkageError e) {
            return true;
        }
    }

    public static boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded("cherishedworlds");
    }
}
