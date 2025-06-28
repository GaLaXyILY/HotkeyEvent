package com.github.galaxyily;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.simple.SimplePacketAdapter;
import com.github.retrooper.packetevents.packettype.clientbound.Particle;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    private Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();

        // Cấu hình PacketEvents
        PacketEvents.get().getSettings()
                .bStats(false)
                .checkForUpdates(false);
        PacketEvents.get().load();

        // Đăng ký listener chặn DAMAGE_INDICATOR
        PacketEvents.get().getEventManager().registerListener(new SimplePacketAdapter(this) {
            @Override
            public void onPacketPlayOutWorldParticles(WrapperPlayServerWorldParticles packet) {
                if (packet.getParticle() == Particle.DAMAGE_INDICATOR) {
                    packet.setCancelled(true);
                }
            }
        });

        PacketEvents.get().init();
        logger.info("NoDamageParticle enabled with PacketEvents 2.8.0");
    }

    @Override
    public void onDisable() {
        PacketEvents.get().terminate();
        logger.info("NoDamageParticle disabled.");
    }
}
