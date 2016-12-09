package com.github.kory33.PluginUpdateNotificationAPI;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.kory33.PluginUpdateNotificationAPI.config.ConfigHandler;
import com.github.kory33.PluginUpdateNotificationAPI.listener.EventListener;

public abstract class UpdateNotifyPlugin extends JavaPlugin {
    /** Path to the notification config file */
    public static final String UPDATE_NOTIFICATION_CONFIG_FILEPATH = "update_notification_config.yml";
    
    private final ConfigHandler configHandler = new ConfigHandler(this, UPDATE_NOTIFICATION_CONFIG_FILEPATH);
    
    private EventListener listener;
    
    /** if the update is available */
    protected boolean isUpdateAvailable;
    
    /**
     * Returns true if the plugin's newer version is released.
     * Unlike {@link #getUpdateStatus()}, this method actually checks for the update.
     * @return true if the plugin's newer version is released
     */
    public abstract boolean checkForUpdate();
    
    
    /**
     * Get the string that will be logged to the server console when an update is available.
     * @return string that will be logged to the server console when an update is available.
     */
    public abstract String getUpdateLogString();
    
    
    /**
     * Get the string that will be displayed to the players who logged in when an update is available.
     * @return string that will be displayed to the players who logged in when an update is available.
     */
    public abstract String getUpdatePlayerLogString();
    
    
    /**
     * Get the string that will be logged to the server console when the plugin is up-to-date.
     * @return string that will be logged to the server console when the plugin is up-to-date.
     */
    public abstract String getUpToDateLogString();
    
    
    /**
     * Get the string that will be displayed to the server console when the plugin is up-to-date.
     * @return string that will be displayed to the server console when the plugin is up-to-date.
     */
    public abstract String getUpToDatePlayerLogString();
    
    
    /**
     * Returns the plugin name that will be displayed to the log
     * @return plugin name
     */
    public abstract String getPluginName();
    
    /**
     * Returns true if the plugin's newer version is released.
     * @return true if the plugin's newer version is released
     */
    public boolean getUpdateStatus() {
        if(this.configHandler.isUpdateCheckFrequent()){
            this.isUpdateAvailable = this.checkForUpdate();
        }
        
        return this.isUpdateAvailable;
    }
    
    /**
     * Log the update status to the server console
     */
    private void logUpdateStatus(){
        boolean isUpdated = getUpdateStatus();
        
        if(isUpdated){
            this.getLogger().info(this.getUpdateLogString());
            return;
        }
        
        if(this.configHandler.shouldLogUpToDate()){
            this.getLogger().info(this.getUpToDateLogString());
        }
    };
    
    
    @Override
    public void onEnable() {
        this.listener = new EventListener(this, this.configHandler);
        super.getServer().getLogger().info("Embedded UpdateNotifyPlugin is enabled for " + getPluginName());
        
        if(this.configHandler.shouldLogToServer()){
            this.logUpdateStatus();
        }
        
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(listener);
        
        super.getServer().getLogger().info("Disabled UpdateNotifyPlugin is disabled for " + getPluginName());
        super.onDisable();
    }
}
