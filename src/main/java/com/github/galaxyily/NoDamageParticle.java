package com.github.galaxyily;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.impl.PacketSendEvent;
import com.github.retrooper.packetevents.manager.settings.PacketEventsSettings;
import com.github.retrooper.packetevents.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWorldParticles;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Cấu hình PacketEvents (không check update, tắt bStats)
        PacketEvents.get().getSettings()
                .bStats(false)
                .checkForUpdates(false);
        PacketEvents.get().load();

        // Đăng ký listener packet
        PacketEvents.get().getEventManager().registerListener(new PacketListener());

        // Khởi chạy PacketEvents
        PacketEvents.get().init();
        getLogger().info("Plugin đã được kích hoạt.");
    }

    @Override
    public void onDisable() {
        PacketEvents.get().terminate();
        getLogger().info("Plugin đã bị vô hiệu hóa.");
    }

    public static class PacketListener extends PacketListenerAbstract {
        @Override
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() == PacketType.Play.Server.WORLD_PARTICLES) {
                WrapperPlayServerWorldParticles wrapper = new WrapperPlayServerWorldParticles(event);
                if (wrapper.getParticle().name().equalsIgnoreCase("damage_indicator")) {
                    event.setCancelled(true); // Chặn particle DAMAGE_INDICATOR
                }
            }
        }
    }
}
