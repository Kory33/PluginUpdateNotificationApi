package com.gethub.kory33.githubbukkitpluginupdateapi.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gethub.kory33.githubbukkitpluginupdateapi.UpdateNotifyPlugin;

/**
 * Bukkit-configuration handler
 * @author Kory33
 *
 */
public class ConfigHandler {
    /** Path to the configuration file */
    private final FileConfiguration fConfiguration;

    public ConfigHandler(UpdateNotifyPlugin plugin, String configPath){
        File file = new File(plugin.getDataFolder(), configPath);
        this.fConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
