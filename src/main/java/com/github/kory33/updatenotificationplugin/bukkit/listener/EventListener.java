package com.github.kory33.updatenotificationplugin.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import com.github.kory33.updatenotificationplugin.bukkit.UpdateNotificationPlugin;
import com.github.kory33.updatenotificationplugin.bukkit.config.ConfigHandler;

public class EventListener implements Listener {
    private final UpdateNotificationPlugin plugin;
    private final ConfigHandler configHandler;

    public EventListener(UpdateNotificationPlugin plugin, ConfigHandler cHandler) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.configHandler = cHandler;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();
        
        // if the updates don't need to be logged
        if(!this.configHandler.shouldLogToNonOp() && !joinedPlayer.isOp()){
            return;
        }
        
        if(this.configHandler.isUpdateCheckFrequent()){
            this.sendAsyncUpdateStatus(joinedPlayer);
        }else{
            this.sendUpdateStatus(joinedPlayer);
        }
    }
    
    /**
     * Update the release cache of the plugin asynchronously, and then send the update status to the player.
     * @param joinedPlayer to whom the notification would be sent, if needed
     */
    private void sendAsyncUpdateStatus(Player joinedPlayer) {
        BukkitScheduler scheduler = this.plugin.getServer().getScheduler();
        
        scheduler.runTaskAsynchronously(this.plugin, () -> {
            EventListener.this.plugin.updateReleaseCache();
            scheduler.scheduleSyncDelayedTask(EventListener.this.plugin, () -> EventListener.this.sendUpdateStatus(joinedPlayer));
        });
    }

    /**
     * Send the update status to the player who has joined the server
     * @param joinedPlayer player who has joined the server
     */
    private void sendUpdateStatus(Player joinedPlayer){
        if(this.plugin.getUpdateStatus()){
            joinedPlayer.sendMessage(this.plugin.getUpdatePlayerLogString());
            return;
        }
        
        if(this.configHandler.shouldLogUpToDate()){
            joinedPlayer.sendMessage(this.plugin.getUpToDatePlayerLogString());
        }
    }
}
