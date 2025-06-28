package com.github.galaxyily;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.impl.PacketSendEvent;
import com.github.retrooper.packetevents.manager.settings.PacketEventsSettings;
import com.github.retrooper.packetevents.packettype.PacketTypePlayServer;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWorldParticles;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Cấu hình PacketEvents
        PacketEventsSettings settings = PacketEvents.get().getSettings();
        settings.bStats(false);
        settings.checkForUpdates(false);

        // Load PacketEvents
        PacketEvents.get().load();

        // Đăng ký PacketListener
        PacketEvents.get().getEventManager().registerListener(new PacketListener());

        // Init PacketEvents
        PacketEvents.get().init();

        getLogger().info("NoDamageParticle enabled (PacketEvents 2.8.0, Java 21)");
    }

    @Override
    public void onDisable() {
        PacketEvents.get().terminate();
        getLogger().info("NoDamageParticle disabled.");
    }

    private static class PacketListener extends PacketListenerAbstract {
        @Override
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() == PacketTypePlayServer.WORLD_PARTICLES) {
                WrapperPlayServerWorldParticles packet = new WrapperPlayServerWorldParticles(event);
                if (packet.getParticle().name().equalsIgnoreCase("damage_indicator")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
