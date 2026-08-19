package de.melanx.simplebackups.client;

import de.melanx.simplebackups.SimpleBackups;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class ClientEventHandler {

    private static final MutableComponent COMPONENT = Component.translatable("simplebackups.backups_paused").withStyle(ChatFormatting.DARK_RED);
    private static boolean isPaused = false;

    public static void setPaused(boolean paused) {
        isPaused = paused;
    }

    public static boolean isPaused() {
        return isPaused;
    }

    public static void register() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.HOTBAR,
                Identifier.fromNamespaceAndPath(SimpleBackups.MODID, "pause"),
                ClientEventHandler::renderText
        );
    }

    private static void renderText(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (!isPaused) {
            return;
        }

        guiGraphics.fill(3, 3, 20, 20, 0);
        guiGraphics.text(Minecraft.getInstance().font, COMPONENT, 3, 3, -1, true);
    }
}
