package com.github.kory33.updatenotificationplugin.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.kory33.updatenotificationplugin.bukkit.config.ConfigHandler;
import com.github.kory33.updatenotificationplugin.bukkit.listener.EventListener;
import com.github.kory33.updatenotificationplugin.dataWrapper.PluginRelease;

/**
 * The class that represents a bukkit Java plugin.
 * This class features automated update check and logging.
 * 
 * To modify the string that will be shown to the server log, override:
 * {@link #getUpdateLogString()}
 * {@link #getUpToDateLogString()}
 * 
 * To modify the string that will be shown to the player, override:
 * {@link #getUpdatePlayerLogString()}
 * {@link #getUpToDatePlayerLogString()}
 * 
 * @author Kory33
 *
 */
public abstract class UpdateNotificationPlugin extends JavaPlugin {
    /** Path to the notification config file */
    public static final String UPDATE_NOTIFICATION_CONFIG_FILEPATH = "update_notification_config.yml";
    
    private final ConfigHandler configHandler = new ConfigHandler(this, UPDATE_NOTIFICATION_CONFIG_FILEPATH);
    
    private EventListener listener;
    
    private PluginRelease latestRelease;
    
    /**
     * Update the release cache(latestRelease member)
     */
    public abstract void updateReleaseCache();
    
    /**
     * Get the string that will be logged to the server console when an update is available.
     * @return string that will be logged to the server console when an update is available.
     */
    public String getUpdateLogString() {
        return "New version available! " + this.getPluginName() + "-" + this.latestRelease.getVersionString()
             + " [ " + this.latestRelease.getLink() + " ]";
    }
    
    
    /**
     * Get the string that will be displayed to the players who logged in when an update is available.
     * @return string that will be displayed to the players who logged in when an update is available.
     */
    public String getUpdatePlayerLogString() {
        return ChatColor.YELLOW + "New version available! "
        	 + ChatColor.GREEN + this.getPluginName() + "-" + this.latestRelease.getVersionString()
             + ChatColor.GRAY + " [ " + this.latestRelease.getLink() + " ]";
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
     * Get the version of the plugin
     * @return the version of the plugin
     */
    public String getVersionString() {
        return this.getDescription().getVersion();
    }
    
    /**
     * Returns true if the plugin's newer version is released.
     * @return true if the plugin's newer version is released
     */
    public boolean getUpdateStatus() {
        return this.latestRelease != null && this.latestRelease.getVersion().isNewerThan(this.getVersionString());
    }
    
    /**
     * update the release cache to the given argument
     * @param release new release to be cached.
     */
    public final void updateReleaseCache(PluginRelease release) {
        this.latestRelease = release;
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
        super.getServer().getLogger().info("Embedded UpdateNotifyPlugin is enabled for " + this.getPluginName());
        
        updateReleaseCache();
        
        if(this.configHandler.shouldLogToServer()){
            this.logUpdateStatus();
        }
        
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(listener);
        
        super.getServer().getLogger().info("Disabled UpdateNotifyPlugin is disabled for " + this.getPluginName());
        super.onDisable();
    }
}
