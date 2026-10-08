/*
 * Modified by the Simple Backups Fabric project in 2026.
 * Adapted from upstream SimpleBackups for Fabric; licensed under Apache-2.0.
 */
package de.melanx.simplebackups.client;

import de.melanx.simplebackups.SimpleBackups;
import de.melanx.simplebackups.client.restore.WorldSelectionRestoreButton;
import de.melanx.simplebackups.network.Pause;
import fuzs.forgeconfigapiport.fabric.api.v5.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigScreenFactoryRegistry.INSTANCE.register(SimpleBackups.MODID, ConfigurationScreen::new);
        ClientPlayNetworking.registerGlobalReceiver(Pause.TYPE, (payload, _) -> ClientEventHandler.setPaused(payload.pause()));
        ClientEventHandler.register();
        WorldSelectionRestoreButton.register();
    }
}
