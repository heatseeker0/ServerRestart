package com.mcspacecraft.serverrestart;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.mcspacecraft.serverrestart.util.MessageUtils;

public class ShutdownController {
    private final ServerRestart plugin;

    private boolean debug = false;
    private String shutdownAnnounceMsg;

    public ShutdownController(ServerRestart plugin) {
        this.plugin = plugin;
    }

    public void load() {
        final FileConfiguration config = plugin.getConfig();

        if (config.contains("debug")) {
            debug = config.getBoolean("debug");
        }

        final int restartMinutes = config.getInt("restart-time", 720);
        plugin.logInfoMessage("The server will attempt to shutdown in %d minutes.", restartMinutes);

        shutdownAnnounceMsg = MessageUtils.parseColors(config.getString("restart-message"));

        BukkitScheduler scheduler = Bukkit.getScheduler();
        // Programatically schedule the restart
        scheduler.runTaskLater(plugin, new ShutdownTimer(), 20 * restartMinutes * 60);
        scheduler.runTaskLater(plugin, new ShutdownAnnouncer(), 20 * (restartMinutes - 1) * 60);
    }

    public boolean isDebug() {
        return debug;
    }

    private class ShutdownAnnouncer implements Runnable {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(shutdownAnnounceMsg);
            }
        }
    }

    private class ShutdownTimer implements Runnable {
        @Override
        public void run() {
            plugin.logInfoMessage("Server programatically shut down at specified time.");
            Bukkit.shutdown();
        }
    }
}
