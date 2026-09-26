/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups.network;

import de.melanx.simplebackups.SimpleBackups;
import net.minecraft.resources.ResourceLocation;

public record Pause(boolean pause) {
    public static final ResourceLocation ID = new ResourceLocation(SimpleBackups.MODID, "pause");
}
