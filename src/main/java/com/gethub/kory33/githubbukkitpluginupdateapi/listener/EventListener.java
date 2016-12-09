package com.gethub.kory33.githubbukkitpluginupdateapi.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gethub.kory33.githubbukkitpluginupdateapi.UpdateNotifyPlugin;
import com.gethub.kory33.githubbukkitpluginupdateapi.config.ConfigHandler;

public class EventListener implements Listener {
    private UpdateNotifyPlugin plugin;
    private ConfigHandler configHandler;

    public EventListener(UpdateNotifyPlugin plugin, ConfigHandler cHandler) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.configHandler = cHandler;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO implementation
    }
}
