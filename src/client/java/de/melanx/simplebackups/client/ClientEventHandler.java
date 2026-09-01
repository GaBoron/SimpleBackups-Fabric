/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ClientEventHandler {

    private static final MutableComponent COMPONENT = Component.translatable("simplebackups.backups_paused").withStyle(ChatFormatting.RED);
    private static boolean isPaused = false;

    public static void setPaused(boolean paused) {
        isPaused = paused;
    }

    public static boolean isPaused() {
        return isPaused;
    }

    public static void register() {
        HudRenderCallback.EVENT.register(ClientEventHandler::renderText);
    }

    private static void renderText(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (isPaused) {
            guiGraphics.drawString(Minecraft.getInstance().font, COMPONENT, 3, 3, -1, true);
        }
    }
}
