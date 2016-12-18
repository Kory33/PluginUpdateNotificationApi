package com.github.kory33.updatenotificationplugin.bukkit.config;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.kory33.updatenotificationplugin.bukkit.UpdateNotificationPlugin;

/**
 * Bukkit-configuration handler
 * @author Kory33
 *
 */
public class ConfigHandler {
    private static final String LOG_UP_TO_DATE_PATH = "logUpToDate";
    private static final String LOG_UPDATES_TO_SERVER = "logUpdatesToServer";
    private static final String UPDATE_CHECK_FREQUENT_PATH = "updateCheckFrequent";
    private static final String LOG_UPDATES_TO_NON_OP = "logUpdatesToNonOp";
    
    private static final Map<String, Boolean> DEFAULT_CONFIG_BOOL_VALUES;
    static{
        Map<String, Boolean> map = new HashMap<>();
        map.put(LOG_UP_TO_DATE_PATH, false);
        map.put(LOG_UPDATES_TO_SERVER, true);
        map.put(UPDATE_CHECK_FREQUENT_PATH, false);
        map.put(LOG_UPDATES_TO_NON_OP, false);
        
        DEFAULT_CONFIG_BOOL_VALUES = Collections.unmodifiableMap(map);
    }
    
    /** Path to the configuration file */
    private FileConfiguration fConfiguration = null;
    
    
    public ConfigHandler(UpdateNotificationPlugin plugin, String configPath){
        if(!(new File(configPath)).exists()){
            if(plugin.getResource(configPath) == null) {
                return;
            }
            
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
        if(this.shouldUseDefaultValue(LOG_UP_TO_DATE_PATH)) {
            return DEFAULT_CONFIG_BOOL_VALUES.get(LOG_UP_TO_DATE_PATH);
        }
        
        return this.fConfiguration.getBoolean(LOG_UP_TO_DATE_PATH);
    }
    
    /**
     * Return true, in reference to the configuration file, if the plugin should check for update frequently.
     * @return value of {@value #UPDATE_CHECK_FREQUENT_PATH} in the config file
     */
    public boolean isUpdateCheckFrequent(){
        if(this.shouldUseDefaultValue(UPDATE_CHECK_FREQUENT_PATH)) {
            return DEFAULT_CONFIG_BOOL_VALUES.get(UPDATE_CHECK_FREQUENT_PATH);
        }
        
        return this.fConfiguration.getBoolean(UPDATE_CHECK_FREQUENT_PATH);
    }
    
    /**
     * Return true, in reference to the configuration file, if the plugin should log updates to the server console
     * @return value of {@value #UPDATE_CHECK_FREQUENT_PATH} in the config file
     */
    public boolean shouldLogToServer(){
        if(this.shouldUseDefaultValue(LOG_UPDATES_TO_SERVER)) {
            return DEFAULT_CONFIG_BOOL_VALUES.get(LOG_UPDATES_TO_SERVER);
        }
        
        return this.fConfiguration.getBoolean(LOG_UPDATES_TO_SERVER);
    }

    /**
     * Return true, in reference to the configuration file, if the plugin should log updates to non-op players
     * @return value of {@value #LOG_UPDATES_TO_NON_OP} in the config file
     */
    public boolean shouldLogToNonOp() {
        if(this.shouldUseDefaultValue(LOG_UPDATES_TO_NON_OP)) {
            return DEFAULT_CONFIG_BOOL_VALUES.get(LOG_UPDATES_TO_NON_OP);
        }
        
        return this.fConfiguration.getBoolean(LOG_UPDATES_TO_NON_OP);
    }
    
    private boolean shouldUseDefaultValue(String path) {
        return this.fConfiguration == null || this.fConfiguration.contains(path);
    }
}
