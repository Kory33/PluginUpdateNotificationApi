package com.gethub.kory33.githubbukkitpluginupdateapi;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.gethub.kory33.githubbukkitpluginupdateapi.config.ConfigHandler;
import com.gethub.kory33.githubbukkitpluginupdateapi.listener.EventListener;

public abstract class UpdateNotifyPlugin extends JavaPlugin {
    /** Path to the notification config file */
    public static final String UPDATE_NOTIFICATION_CONFIG_FILEPATH = "update_notification_config.yml";
    
    private ConfigHandler configHandler;
    private EventListener listener;
    
    /**
     * Returns true if the plugin's newer version is released
     * @return true if the plugin's newer version is released
     */
    public abstract boolean getUpdateStatus();
    
    
    /**
     * Get the string that will be logged to the server console.
     * @return
     */
    public abstract String getUpdateLogString();
    
    
    /**
     * Returns the plugin name that will be displayed to the log
     * @return plugin name
     */
    public abstract String getPluginName();
    
    
    /**
     * Log the update status to the server console
     */
    public void logUpdateStatus(){
        boolean isUpdated = getUpdateStatus();
        
        if(isUpdated){
            this.getLogger().info(getUpdateLogString());
        }
    };
    
    
    @Override
    public void onEnable() {
        this.configHandler = new ConfigHandler(this, UPDATE_NOTIFICATION_CONFIG_FILEPATH);
        this.listener = new EventListener(this, this.configHandler);
        
        super.getServer().getLogger().info("Embedded UpdateNotifyPlugin is enabled for " + getPluginName());
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(listener);
        
        super.getServer().getLogger().info("Disabled UpdateNotifyPlugin is disabled for " + getPluginName());
        super.onDisable();
    }
}
