package com.github.kory33.PluginUpdateNotificationAPI.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.kory33.PluginUpdateNotificationAPI.UpdateNotifyPlugin;
import com.github.kory33.PluginUpdateNotificationAPI.config.ConfigHandler;

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
        if(this.configHandler.isUpdateCheckFrequent()){
            this.plugin.updateReleaseCacheAsync();
        }else{
            this.sendUpdateStatus(event.getPlayer(), this.plugin.getUpdateStatus());
        }
        
        return;
    }
    
    /**
     * Send the update status to the player who has joined the server
     * @param joinedPlayer
     * @param isUpdated
     */
    private void sendUpdateStatus(Player joinedPlayer, boolean isUpdated){
        if(isUpdated){
            joinedPlayer.sendMessage(this.plugin.getUpdatePlayerLogString());
            return;
        }
        
        if(this.configHandler.shouldLogUpToDate()){
            joinedPlayer.sendMessage(this.plugin.getUpToDatePlayerLogString());
            return;
        }
    }
}
