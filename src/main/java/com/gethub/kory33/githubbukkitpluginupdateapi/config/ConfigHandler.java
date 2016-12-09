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
    private static final String LOG_UP_TO_DATE_PATH = "logUpToDate";
    
    /** Path to the configuration file */
    private final FileConfiguration fConfiguration;

    public ConfigHandler(UpdateNotifyPlugin plugin, String configPath){
        File file = new File(plugin.getDataFolder(), configPath);
        this.fConfiguration = YamlConfiguration.loadConfiguration(file);
    }
    
    /**
     * Return true, in reference to the configuration file, if the plugin should log up-to-date status
     * @return value of LOG_UP_TO_DATE_PATH defined in ConfigHandler
     */
    public boolean shouldLogUpToDate(){
        return this.fConfiguration.getBoolean(LOG_UP_TO_DATE_PATH, false);
    }
}
