/*
 * Modified by the Simple Backups Fabric project in 2026.
 * This file was adapted from upstream SimpleBackups for the Fabric platform.
 */
package de.melanx.simplebackups.client;

import de.melanx.simplebackups.SimpleBackups;
import de.melanx.simplebackups.network.Pause;
import fuzs.forgeconfigapiport.fabric.api.v5.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigScreenFactoryRegistry.INSTANCE.register(SimpleBackups.MODID, ConfigurationScreen::new);
        ClientPlayNetworking.registerGlobalReceiver(Pause.TYPE, (payload, context) -> ClientEventHandler.setPaused(payload.pause()));
        ClientEventHandler.register();
    }
}
