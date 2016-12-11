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
    
    private PluginRelease latestRelease;
    
    /**
     * Returns a reference to the latest plugin release
     * @return a reference to the latest plugin release 
     */
    public abstract PluginRelease getLatestRelease();
    
    /**
     * Get the string that will be logged to the server console when an update is available.
     * @return string that will be logged to the server console when an update is available.
     */
    public String getUpdateLogString() {
        return "New version available! " + this.getPluginName() + this.latestRelease.getVersion()
             + "[" + this.latestRelease.getLink() + "]";
    }
    
    
    /**
     * Get the string that will be displayed to the players who logged in when an update is available.
     * @return string that will be displayed to the players who logged in when an update is available.
     */
    public String getUpdatePlayerLogString() {
        return "New version available! " + this.getPluginName() + this.latestRelease.getVersion()
             + "[" + this.latestRelease.getLink() + "]";
    }
    
    
    /**
     * Get the string that will be logged to the server console when the plugin is up-to-date.
     * @return string that will be logged to the server console when the plugin is up-to-date.
     */
    public String getUpToDateLogString() {
        return this.getPluginName() + " is up-to-date.";
    }
    
    
    /**
     * Get the string that will be displayed to the server console when the plugin is up-to-date.
     * @return string that will be displayed to the server console when the plugin is up-to-date.
     */
    public String getUpToDatePlayerLogString() {
        return this.getPluginName() + " is up-to-date.";
    }
    
    
    /**
     * Returns the plugin name that will be displayed to the log
     * @return plugin name
     */
    public String getPluginName() {
        return this.getDescription().getName();
    }
    
    /**
     * Returns true if the plugin's newer version is released.
     * @return true if the plugin's newer version is released
     */
    public boolean getUpdateStatus() {
        return this.latestRelease != null && this.latestRelease.isNewerThanCurrent(this);
    }

    /**
     * Update the internal cache of the latest release.
     */
    public void updateUpdateRelease(){
        this.latestRelease = getLatestRelease();
    }
    
    
    /**
     * Log the update status to the server console
     */
    private void logUpdateStatus(){
        if(this.getUpdateStatus()){
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
        
        updateUpdateRelease();
        
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
