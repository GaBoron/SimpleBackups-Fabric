package de.melanx.simplebackups.client;

import de.melanx.simplebackups.network.Pause;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientEventHandler.register();
        ClientPlayNetworking.registerGlobalReceiver(Pause.ID, (client, handler, buf, sender) -> {
            boolean paused = buf.readBoolean();
            client.execute(() -> ClientEventHandler.setPaused(paused));
        });
    }
}
