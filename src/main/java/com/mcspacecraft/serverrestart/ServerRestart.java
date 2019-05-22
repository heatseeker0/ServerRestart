package com.mcspacecraft.serverrestart;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class ServerRestart extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("Minecraft.ServerRestart");
    private ShutdownController shutdownController;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        shutdownController = new ShutdownController(this);
        shutdownController.load();
    }

    public ShutdownController getShutdownController() {
        return shutdownController;
    }

    public void logInfoMessage(final String msg, final Object... args) {
        if (args == null || args.length == 0) {
            logger.info(String.format("[%s] %s", getDescription().getName(), msg));
        } else {
            logger.info(String.format("[%s] %s", getDescription().getName(), String.format(msg, args)));
        }
    }

    public void logErrorMessage(final String msg, final Object... args) {
        if (args == null || args.length == 0) {
            logger.severe(String.format("[%s] %s", getDescription().getName(), msg));
        } else {
            logger.severe(String.format("[%s] %s", getDescription().getName(), String.format(msg, args)));
        }
    }
}
