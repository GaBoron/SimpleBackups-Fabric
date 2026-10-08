package de.melanx.simplebackups.client.restore;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.Component;

/** Adds the restore shortcut using Fabric screen events, with no vanilla mixins. */
public final class WorldSelectionRestoreButton {

    private WorldSelectionRestoreButton() {}

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof SelectWorldScreen)) return;
            var list = screen.children().stream().filter(WorldSelectionList.class::isInstance)
                    .map(WorldSelectionList.class::cast).findFirst().orElse(null);
            var search = Screens.getWidgets(screen).stream().filter(EditBox.class::isInstance)
                    .map(EditBox.class::cast).findFirst().orElse(null);
            if (list == null || search == null) return;
            Component label = Component.translatable("simplebackups.restore.open");
            Button existing = Screens.getWidgets(screen).stream().filter(Button.class::isInstance)
                    .map(Button.class::cast).filter(widget -> widget.getMessage().equals(label)).findFirst().orElse(null);
            int totalWidth = search.getWidth() + (existing == null ? 0 : existing.getWidth() + 4);
            int buttonWidth = Math.min(100, totalWidth / 3);
            Button button = existing != null ? existing : Button.builder(label, ignored ->
                    list.getSelectedOpt().ifPresent(entry -> client.gui.setScreen(
                            new BackupSelectionScreen(screen, entry.getLevelSummary(), list::reloadWorldList))))
                    .bounds(0, search.getY(), buttonWidth, 20).build();
            if (existing == null) {
                search.setWidth(totalWidth - buttonWidth - 4);
                button.setTooltip(Tooltip.create(Component.translatable("simplebackups.restore.open_tooltip")));
                Screens.getWidgets(screen).add(button);
            }
            ScreenEvents.beforeExtract(screen).register((current, graphics, mouseX, mouseY, partialTick) -> {
                // Vanilla re-centers the search field on resize; keep both controls in its original row.
                search.setX((current.width - totalWidth) / 2);
                button.setX(search.getX() + search.getWidth() + 4);
                button.setY(search.getY());
                button.active = client.level == null && !client.hasSingleplayerServer()
                        && list.getSelectedOpt().map(entry -> !entry.getLevelSummary().isLocked()).orElse(false);
            });
        });
    }
}
