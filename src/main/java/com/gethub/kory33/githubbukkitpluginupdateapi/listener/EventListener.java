package com.gethub.kory33.githubbukkitpluginupdateapi.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gethub.kory33.githubbukkitpluginupdateapi.UpdateNotifyPlugin;

public class EventListener implements Listener {
    UpdateNotifyPlugin plugin;

    public EventListener(UpdateNotifyPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO implementation
    }
}
