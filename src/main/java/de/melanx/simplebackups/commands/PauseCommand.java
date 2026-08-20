/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.melanx.simplebackups.BackupData;
import de.melanx.simplebackups.network.Pause;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class PauseCommand implements Command<CommandSourceStack> {

    private final boolean paused;

    private PauseCommand(boolean paused) {
        this.paused = paused;
    }

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("backup")
                .then(Commands.literal("pause")
                        .executes(new PauseCommand(true)))
                .then(Commands.literal("unpause")
                        .executes(new PauseCommand(false)));
    }


    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        BackupData data = BackupData.get(context.getSource().getServer());
        data.setPaused(this.paused);
        context.getSource().getServer().getPlayerList().getPlayers().forEach(player -> {
            if (ServerPlayNetworking.canSend(player, Pause.TYPE)) {
                ServerPlayNetworking.send(player, new Pause(this.paused));
            }
        });
        return this.paused ? 1 : 0;
    }
}
