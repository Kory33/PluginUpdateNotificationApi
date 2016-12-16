package com.github.kory33.updatenotificationplugin.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import com.github.kory33.updatenotificationplugin.bukkit.UpdateNotificationPlugin;
import com.github.kory33.updatenotificationplugin.bukkit.config.ConfigHandler;

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
            BukkitScheduler scheduler = this.plugin.getServer().getScheduler();
            scheduler.runTaskAsynchronously(this.plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.updateReleaseCache();
                    scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            EventListener.this.sendUpdateStatus(joinedPlayer);
                        }
                    });
                }
            });
        }else{
            this.sendUpdateStatus(joinedPlayer);
        }
        
        return;
    }
    
    /**
     * Send the update status to the player who has joined the server
     * @param joinedPlayer
     * @param isUpdated
     */
    private void sendUpdateStatus(Player joinedPlayer){
        if(this.plugin.getUpdateStatus()){
            joinedPlayer.sendMessage(this.plugin.getUpdatePlayerLogString());
            return;
        }
        
        if(this.configHandler.shouldLogUpToDate()){
            joinedPlayer.sendMessage(this.plugin.getUpToDatePlayerLogString());
            return;
        }
    }
}
