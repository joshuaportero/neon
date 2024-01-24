package dev.portero.config;


import dev.portero.NeonPlugin;

public class ConfigKeys {

    private static final NeonPlugin plugin = NeonPlugin.getInstance();
    private static final ConfigManager configManager = plugin.getConfigManager();

    public static final String SQL_TABLE_PREFIX = plugin.getConfig().getString("data.table-prefix");

}
