package com.gethub.kory33.githubbukkitpluginupdateapi;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class UpdateNotifyPlugin extends JavaPlugin {
    /**
     * Returns true if the plugin's newer version is released
     * @return true if the plugin's newer version is released
     */
    public abstract boolean getUpdateStatus();
    
    @Override
    public void onEnable() {
        super.getServer().getLogger().info("Embedded UpdateNotifyPlugin is enabled for " + getPluginName());
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.getServer().getLogger().info("Disabled UpdateNotifyPlugin is disabled for " + getPluginName());
        super.onDisable();
    }
    
    /**
     * Returns the plugin name that will be displayed to the log
     * @return plugin name
     */
    public abstract String getPluginName();
}
