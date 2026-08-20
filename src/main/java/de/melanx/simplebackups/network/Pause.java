package de.melanx.simplebackups.network;

import de.melanx.simplebackups.SimpleBackups;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import javax.annotation.Nonnull;

public record Pause(boolean pause) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(SimpleBackups.MODID, "pause");
    public static final CustomPacketPayload.Type<Pause> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, Pause> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, Pause::pause, Pause::new
    );

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Pause.TYPE;
    }

}
