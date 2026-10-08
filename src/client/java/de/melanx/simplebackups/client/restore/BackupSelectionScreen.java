package de.melanx.simplebackups.client.restore;

import de.melanx.simplebackups.SimpleBackups;
import de.melanx.simplebackups.StorageSize;
import de.melanx.simplebackups.config.CommonConfig;
import de.melanx.simplebackups.restore.BackupCatalog;
import de.melanx.simplebackups.restore.RestorePoint;
import de.melanx.simplebackups.restore.WorldRestore;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/** Presents restore points; archive I/O and installation run outside the render thread. */
public final class BackupSelectionScreen extends Screen {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());
    private final Screen parent;
    private final LevelSummary world;
    private final Runnable reloadWorlds;
    private final Path backupDirectory;
    private List<RestorePoint> points = List.of();
    private final Map<RestorePoint, Button> rowButtons = new LinkedHashMap<>();
    private RestorePoint selected;
    private Component status = text("loading");
    private volatile String progress = "";
    private int page;
    private int loadGeneration;
    private boolean loading = true;
    private boolean restoring;
    private Button copyButton;
    private Button replaceButton;

    public BackupSelectionScreen(Screen parent, LevelSummary world, Runnable reloadWorlds) {
        super(text("title"));
        this.parent = parent;
        this.world = world;
        this.reloadWorlds = reloadWorlds;
        this.backupDirectory = CommonConfig.getOutputPath(world.getLevelId()).toAbsolutePath().normalize();
        loadBackups();
    }

    private void loadBackups() {
        int generation = ++this.loadGeneration;
        this.loading = true;
        this.status = text("loading");
        CompletableFuture.supplyAsync(() -> {
            try {
                return BackupCatalog.load(this.backupDirectory, this.world.getLevelId());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }).whenComplete((catalog, error) -> this.minecraft.execute(() -> {
            if (generation != this.loadGeneration) return;
            this.loading = false;
            this.selected = null;
            this.page = 0;
            if (error != null) {
                SimpleBackups.LOGGER.error("Failed to list backups for {}", this.world.getLevelId(), error);
                this.points = List.of();
                this.status = text("load_failed");
            } else {
                this.points = catalog.points();
                catalog.problems().forEach(problem -> SimpleBackups.LOGGER.warn("Backup catalog: {}", problem));
                this.status = catalog.problems().isEmpty()
                        ? text(this.points.isEmpty() ? "empty" : "choose") : text("unreadable", catalog.problems().size());
            }
            if (this.minecraft.gui.screen() == this) rebuildWidgets();
        }));
    }

    @Override
    protected void init() {
        this.rowButtons.clear();
        int width = Math.min(440, this.width - 24);
        int left = (this.width - width) / 2;
        int rows = rowsPerPage();
        this.page = Math.min(this.page, pageCount() - 1);
        if (!this.loading && !this.restoring) {
            for (int i = this.page * rows; i < Math.min(this.points.size(), (this.page + 1) * rows); i++) {
                RestorePoint point = this.points.get(i);
                Button row = Button.builder(rowLabel(point), ignored -> select(point))
                        .bounds(left, 66 + (i % rows) * 26, width, 24).build();
                row.setTooltip(Tooltip.create(Component.literal(point.name()).append("\n")
                        .append(point.beforeRestore() ? text("rollback_tooltip").copy().append("\n") : Component.empty())
                        .append(text("archives", point.archives().size()))));
                addRenderableWidget(row);
                this.rowButtons.put(point, row);
            }
        }
        addButton(text("previous"), left, this.height - 76, 80, () -> changePage(-1))
                .active = !this.loading && !this.restoring && this.page > 0;
        addButton(text("next"), left + width - 80, this.height - 76, 80, () -> changePage(1))
                .active = !this.loading && !this.restoring && this.page + 1 < pageCount();
        int half = (width - 4) / 2;
        this.copyButton = addButton(text("copy"), left, this.height - 50, half, () -> confirm(WorldRestore.Mode.COPY));
        this.replaceButton = addButton(text("replace"), left + half + 4, this.height - 50, half,
                () -> confirm(WorldRestore.Mode.REPLACE));
        addButton(CommonComponents.GUI_BACK, left, this.height - 26, half, this::onClose).active = !this.restoring;
        addButton(text("refresh"), left + half + 4, this.height - 26, half, () -> {
            loadBackups();
            rebuildWidgets();
        }).active = !this.loading && !this.restoring;
        updateActions();
    }

    @Override
    protected void repositionElements() {
        rebuildWidgets();
    }

    private int rowsPerPage() {
        return Math.max(1, (this.height - 154) / 26);
    }

    private int pageCount() {
        return Math.max(1, (this.points.size() + rowsPerPage() - 1) / rowsPerPage());
    }

    private void changePage(int delta) {
        this.page += delta;
        rebuildWidgets();
    }

    private void select(RestorePoint point) {
        this.selected = point;
        this.rowButtons.forEach((snapshot, button) -> button.setMessage(rowLabel(snapshot)));
        updateActions();
    }

    private void updateActions() {
        boolean enabled = this.selected != null && !this.loading && !this.restoring;
        this.copyButton.active = enabled;
        this.replaceButton.active = enabled;
    }

    private Component rowLabel(RestorePoint point) {
        return Component.literal(point.equals(this.selected) ? "> " : "")
                .append(text("entry", DATE.format(Instant.ofEpochMilli(point.timestamp())),
                        text(point.beforeRestore() ? "type.before_restore" : "type." + point.type().name().toLowerCase(java.util.Locale.ROOT)),
                        point.format().name(), StorageSize.getFormattedSize(point.size())));
    }

    private void confirm(WorldRestore.Mode mode) {
        RestorePoint point = this.selected;
        if (point == null || this.restoring) return;
        Component message = text(mode == WorldRestore.Mode.COPY ? "confirm_copy" : "confirm_replace",
                this.world.getLevelName(), DATE.format(Instant.ofEpochMilli(point.timestamp())));
        this.minecraft.gui.setScreen(new ConfirmScreen(confirmed -> {
            this.minecraft.gui.setScreen(this);
            if (confirmed) startRestore(point, mode);
        }, text("title"), message, text(mode == WorldRestore.Mode.COPY ? "copy" : "replace"), CommonComponents.GUI_CANCEL));
    }

    private void startRestore(RestorePoint point, WorldRestore.Mode mode) {
        if (this.minecraft.level != null || this.minecraft.hasSingleplayerServer()) {
            this.status = text("world_open");
            return;
        }
        this.restoring = true;
        this.status = text("working");
        this.progress = "";
        rebuildWidgets();
        Path saves = this.minecraft.getLevelSource().getBaseDir();
        CompletableFuture.supplyAsync(() -> {
            try {
                return RestoreWorldAction.restore(saves, this.world, point, mode, name -> this.progress = name);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }).whenComplete((result, error) -> this.minecraft.execute(() -> {
            this.restoring = false;
            this.reloadWorlds.run();
            if (error != null) {
                SimpleBackups.LOGGER.error("Failed to restore backups for {}", this.world.getLevelId(), error);
                this.minecraft.gui.setScreen(new AlertScreen(() -> this.minecraft.gui.setScreen(this),
                        text("failed_title"), text("failed")));
            } else {
                Component message = text("success", result.world().getFileName().toString());
                if (result.previousBackup() != null) {
                    message = message.copy().append("\n\n").append(text("rollback_created", result.previousBackup().toString()));
                }
                this.minecraft.gui.setScreen(new AlertScreen(this::onClose, text("success_title"), message));
            }
        }));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        graphics.centeredText(this.font, this.title, this.width / 2, 10, 0xFFFFFFFF);
        graphics.centeredText(this.font, this.world.getLevelName(), this.width / 2, 28, 0xFFAAAAAA);
        graphics.centeredText(this.font, this.status, this.width / 2, 46, 0xFFDDDDDD);
        if (this.restoring) {
            String filename = this.font.plainSubstrByWidth(this.progress.equals("rollback")
                    ? text("creating_rollback").getString() : this.progress, this.width - 24);
            graphics.centeredText(this.font, filename, this.width / 2, 78, 0xFFAAAAAA);
        } else if (!this.loading) {
            graphics.centeredText(this.font, text("page", this.page + 1, pageCount()), this.width / 2,
                    this.height - 70, 0xFFAAAAAA);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return !this.restoring;
    }

    @Override
    public void onClose() {
        if (!this.restoring) this.minecraft.gui.setScreen(this.parent);
    }

    private Button addButton(Component label, int x, int y, int width, Runnable action) {
        return addRenderableWidget(Button.builder(label, ignored -> action.run()).bounds(x, y, width, 20).build());
    }

    private static Component text(String key, Object... arguments) {
        return Component.translatable("simplebackups.restore." + key, arguments);
    }
}
