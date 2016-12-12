package com.github.kory33.UpdateNotificationPlugin.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.kory33.UpdateNotificationPlugin.UpdateNotificationPlugin;

/**
 * Bukkit-configuration handler
 * @author Kory33
 *
 */
public class ConfigHandler {
    private static final String LOG_UP_TO_DATE_PATH = "logUpToDate";
    private static final String LOG_UPDATES_TO_SERVER = "logUpdatesToServer";
    private static final String UPDATE_CHECK_FREQUENT_PATH = "updateCheckFrequent";
    
    /** Path to the configuration file */
    private final FileConfiguration fConfiguration;
    
    
    public ConfigHandler(UpdateNotificationPlugin plugin, String configPath){
        if(!(new File(configPath)).exists()){
            plugin.saveResource(configPath, false);
        }
        
        File file = new File(plugin.getDataFolder(), configPath);
        this.fConfiguration = YamlConfiguration.loadConfiguration(file);
    }
    
    /**
     * Return true, in reference to the configuration file, if the plugin should log up-to-date status
     * @return value of {@value #LOG_UP_TO_DATE_PATH} in the config file
     */
    public boolean shouldLogUpToDate(){
        return this.fConfiguration.getBoolean(LOG_UP_TO_DATE_PATH, false);
    }
    
    /**
     * Return true, in reference to the configuration file, if the plugin should check for update frequently.
     * @return value of {@value #UPDATE_CHECK_FREQUENT_PATH} in the config file
     */
    public boolean isUpdateCheckFrequent(){
        return this.fConfiguration.getBoolean(UPDATE_CHECK_FREQUENT_PATH, false);
    }
    
    /**
     * Return true, in reference to the configuration file, if the plugin should log updates to the server console
     * @return value of {@value #UPDATE_CHECK_FREQUENT_PATH} in the config file
     */
    public boolean shouldLogToServer(){
        return this.fConfiguration.getBoolean(LOG_UPDATES_TO_SERVER, true);
    }
}
