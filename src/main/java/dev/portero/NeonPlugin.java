package dev.portero;

import dev.portero.command.CommandHandler;
import dev.portero.config.ConfigManager;
import dev.portero.locale.LocaleManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;

public class NeonPlugin extends JavaPlugin {

    private static NeonPlugin instance;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        Instant count = Instant.now();
        new CommandHandler(this);
        LocaleManager localeManager = new LocaleManager();
        localeManager.reload();
        this.configManager = new ConfigManager(this);
        count = Instant.now().minusMillis(count.toEpochMilli());
        getLogger().info("Enabled in " + count.toEpochMilli() + "ms");
        this.setInstance(this);
    }

    @Override
    public void onDisable() {
        this.setInstance(null);
    }

    public static NeonPlugin getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    private void setInstance(NeonPlugin instance) {
        NeonPlugin.instance = instance;
    }
}