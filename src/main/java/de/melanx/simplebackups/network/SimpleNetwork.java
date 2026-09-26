/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public final class SimpleNetwork {

    private SimpleNetwork() {
    }

    public static void pause(boolean paused, Iterable<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            pause(player, paused);
        }
    }

    public static void pause(ServerPlayer player, boolean paused) {
        if (ServerPlayNetworking.canSend(player, Pause.ID)) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(paused);
            ServerPlayNetworking.send(player, Pause.ID, buf);
        }
    }
}
