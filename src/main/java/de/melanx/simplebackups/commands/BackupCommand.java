package de.melanx.simplebackups.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.melanx.simplebackups.BackupThread;
import de.melanx.simplebackups.compression.CompressionBase;
import de.melanx.simplebackups.config.CommonConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.server.command.EnumArgument;

public class BackupCommand implements Command<CommandSourceStack> {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("backup")
                .then(Commands.literal("start")
                        .executes(new BackupCommand())
                        .then(Commands.argument("quiet", BoolArgumentType.bool())
                                .executes(new BackupCommand())
                                .then(Commands.argument("format", EnumArgument.enumArgument(CompressionBase.BackupFormat.class))
                                        .executes(new BackupCommand()))
                        )
                );
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        boolean quiet = false;
        CompressionBase.BackupFormat format;
        try {
            quiet = BoolArgumentType.getBool(context, "quiet");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
        try {
            format = context.getArgument("format", CompressionBase.BackupFormat.class);
        } catch (IllegalArgumentException e) {
            format = CommonConfig.getBackupFormat();
        }

        MinecraftServer server = context.getSource().getServer();
        BackupThread.createBackup(server, quiet, format);
        return 1;
    }
}
