/*
 * The MIT License
 *
 * Copyright 2016 toyblocks.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
re and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.llv.updatenotificationplugin.bungeecord;

import com.github.kory33.updatenotificationplugin.dataWrapper.PluginRelease;
import java.util.logging.Level;

import jp.llv.updatenotificationplugin.bungeecord.config.ConfigHandler;
import jp.llv.updatenotificationplugin.bungeecord.listener.EventListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * The class that represents a bungeecord Java plugin.
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
 * @author toyblocks, Kory33
 *
 */
public abstract class UpdateNotificationPlugin extends Plugin {
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
        super.getLogger().log(Level.INFO, "Embedded UpdateNotifyPlugin is enabled for {0}", getPluginName());
        
        updateReleaseCache();
        
        if(this.configHandler.shouldLogToServer()){
            this.logUpdateStatus();
        }
        
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.getProxy().getPluginManager().unregisterListener(listener);
        
        super.getLogger().log(Level.INFO, "Disabled UpdateNotifyPlugin for {0}", getPluginName());
        super.onDisable();
    }
}
