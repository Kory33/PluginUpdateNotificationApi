package com.github.kory33.UpdateNotificationPlugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.kory33.UpdateNotificationPlugin.UpdateNotificationPlugin;
import com.github.kory33.UpdateNotificationPlugin.config.ConfigHandler;

public class EventListener implements Listener {
    private UpdateNotificationPlugin plugin;
    private ConfigHandler configHandler;

    public EventListener(UpdateNotificationPlugin plugin, ConfigHandler cHandler) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.configHandler = cHandler;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();
        
        if(this.configHandler.isUpdateCheckFrequent()){
            
        }else{
            this.sendUpdateStatus(joinedPlayer, this.plugin.getUpdateStatus());
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
