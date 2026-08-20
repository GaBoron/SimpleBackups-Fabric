/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups;

import de.melanx.simplebackups.commands.BackupCommand;
import de.melanx.simplebackups.commands.MergeCommand;
import de.melanx.simplebackups.commands.PauseCommand;
import de.melanx.simplebackups.config.CommonConfig;
import de.melanx.simplebackups.config.ServerConfig;
import de.melanx.simplebackups.network.Pause;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;

public class EventListener {

    private boolean doBackup;

    public static void register() {
        EventListener listener = new EventListener();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> listener.registerCommands(dispatcher));
        ServerTickEvents.END_SERVER_TICK.register(listener::onServerTick);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> listener.onPlayerConnect(handler.player));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> listener.onPlayerDisconnect(handler.player));
    }

    private void registerCommands(com.mojang.brigadier.CommandDispatcher<net.minecraft.commands.CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(SimpleBackups.MODID)
                .requires(stack -> ServerConfig.commandsCheatsDisabled() || stack.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                .then(BackupCommand.register())
                .then(PauseCommand.register())
                .then(MergeCommand.register()));
    }

    private void onServerTick(MinecraftServer server) {
        if (CommonConfig.backupsDisabledByJvmArg()) {
            return;
        }

        ServerLevel level = server.overworld();
        if (level.getGameTime() % 20 == 0) {
            EventListener.checkForTickCounterConfigUpdate(server);

            boolean arePlayersOnline = !server.getPlayerList().getPlayers().isEmpty();
            if (!arePlayersOnline && CommonConfig.doNoPlayerBackups()) {
                this.doBackup = !(CommonConfig.noPlayerBackupCount() == 0 || BackupData.get(level).backupsSinceLastPlayerJoined() >= CommonConfig.noPlayerBackupCount());
            }

            if (arePlayersOnline || this.doBackup) {
                BackupData backupData = BackupData.get(level);
                this.doBackup = !(CommonConfig.noPlayerBackupCount() == 0 || backupData.backupsSinceLastPlayerJoined() >= CommonConfig.noPlayerBackupCount());

                boolean done = BackupThread.tryCreateBackup(server);
                if (done) {
                    SimpleBackups.LOGGER.info("Backup done.");
                    if (!arePlayersOnline) {
                        backupData.incrementBackupsSinceLastPlayerJoined();
                    }
                }
            }
        }
    }

    private void onPlayerConnect(ServerPlayer player) {
        BackupData.get(player.level()).resetBackupsSinceLastPlayerJoined();
        if (CommonConfig.isEnabled() && !CommonConfig.backupsDisabledByJvmArg() && ServerPlayNetworking.canSend(player, Pause.TYPE)) {
            ServerPlayNetworking.send(player, new Pause(BackupData.get(player.level().getServer()).isPaused()));
        }
    }

    private void onPlayerDisconnect(ServerPlayer player) {
        MinecraftServer server = player.level().getServer();
        boolean noOtherPlayers = server.getPlayerList().getPlayers().stream().noneMatch(other -> other != player);
        if (noOtherPlayers) {
            this.doBackup = !(CommonConfig.noPlayerBackupCount() == 0 || BackupData.get(player.level()).backupsSinceLastPlayerJoined() >= CommonConfig.noPlayerBackupCount());
        }
    }

    private static void checkForTickCounterConfigUpdate(MinecraftServer server) {
        BackupData backupData = BackupData.get(server);
        boolean usesTickCounter = CommonConfig.useTickCounter();

        if (usesTickCounter != backupData.usesTickCounter()) {
            SimpleBackups.LOGGER.info("Tick counter config updated, usesTickCounter: {}", usesTickCounter);
            backupData.setUsesTickCounter(usesTickCounter);

            long lastTimeSaved = backupData.getLastSaved();
            long commonConfigTimer = CommonConfig.getTimer(true);

            SimpleBackups.LOGGER.info("Initial lastTimeSaved: {}", lastTimeSaved);
            SimpleBackups.LOGGER.info("Config timer in minutes: {}", commonConfigTimer);

            if (usesTickCounter) {
                long millisecondsTimeDifference = System.currentTimeMillis() - lastTimeSaved;
                long tickTimeDifference = millisecondsTimeDifference / 50L;
                lastTimeSaved = server.overworld().getGameTime() - tickTimeDifference;
                long timerInTicks = commonConfigTimer * 60L * 20L;

                SimpleBackups.LOGGER.info("Milliseconds difference: {}, Tick difference: {}", millisecondsTimeDifference, tickTimeDifference);
                SimpleBackups.LOGGER.info("Updated lastTimeSaved in ticks: {}", lastTimeSaved);
                SimpleBackups.LOGGER.info("Timer value in ticks: {}", timerInTicks);

                lastTimeSaved = Math.max(lastTimeSaved, timerInTicks);
                SimpleBackups.LOGGER.info("Final lastTimeSaved after max comparison (ticks): {}", lastTimeSaved);
            } else {
                long tickTimeDifference = server.overworld().getGameTime() - lastTimeSaved;
                long millisecondsTimeDifference = tickTimeDifference * 50L;
                lastTimeSaved = System.currentTimeMillis() - millisecondsTimeDifference;
                long timerInMilliseconds = commonConfigTimer * 60L * 1000L;

                SimpleBackups.LOGGER.info("Tick difference: {}, Milliseconds difference: {}", tickTimeDifference, millisecondsTimeDifference);
                SimpleBackups.LOGGER.info("Updated lastTimeSaved in milliseconds: {}", lastTimeSaved);
                SimpleBackups.LOGGER.info("Timer value in milliseconds: {}", timerInMilliseconds);

                lastTimeSaved = Math.max(lastTimeSaved, timerInMilliseconds);
                SimpleBackups.LOGGER.info("Final lastTimeSaved after max comparison (milliseconds): {}", lastTimeSaved);
            }
        }
    }
}
