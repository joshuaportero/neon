package dev.portero.config;

import dev.portero.NeonPlugin;
import dev.portero.config.ConfigFileNames;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class ConfigManager {

    private final NeonPlugin plugin;
    private final Map<ConfigFileNames, FileConfiguration> configs;

    public ConfigManager(NeonPlugin plugin) {
        this.plugin = plugin;
        this.configs = new EnumMap<>(ConfigFileNames.class);
        reloadConfigs();
    }

    public void reloadConfigs() {
        if (!configs.isEmpty())
            configs.clear();
        loadConfigs();
    }

    public void loadConfigs() {
        for (ConfigFileNames configFileNames : ConfigFileNames.values()) {
            File configFile = new File(plugin.getDataFolder(), configFileNames.getFileName());
            if (!configFile.exists()) {
                plugin.saveResource(configFileNames.getFileName(), false);
            }
            configs.put(configFileNames, YamlConfiguration.loadConfiguration(configFile));
        }
    }

    public void saveConfigs() {
        for (ConfigFileNames configFileNames : ConfigFileNames.values()) {
            File configFile = new File(plugin.getDataFolder(), configFileNames.getFileName());
            try {
                configs.get(configFileNames).save(configFile);
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save " + configFileNames.getFileName() + "!");
                plugin.getLogger().severe(e.getMessage());
            }
        }
    }

    public FileConfiguration getConfig(ConfigFileNames file) {
        return configs.get(file);
    }
}