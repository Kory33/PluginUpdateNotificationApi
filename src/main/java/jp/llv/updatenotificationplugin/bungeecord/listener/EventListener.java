/*
 * The MIT License
 *
 * Copyright 2016 toyblocks.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
package jp.llv.updatenotificationplugin.bungeecord.listener;

import jp.llv.updatenotificationplugin.bungeecord.UpdateNotificationPlugin;
import jp.llv.updatenotificationplugin.bungeecord.config.ConfigHandler;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author toyblocks, Kory33
 */


public class EventListener implements Listener {

    private final UpdateNotificationPlugin plugin;
    private final ConfigHandler configHandler;

    public EventListener(UpdateNotificationPlugin plugin, ConfigHandler cHandler) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);

        this.plugin = plugin;
        this.configHandler = cHandler;
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer joinedPlayer = event.getPlayer();

        // if the updates don't need to be logged
        if (!this.configHandler.shouldLogToNonOp() /*&& !joinedPlayer.isOp()*/) {
            return;
        }

        if (this.configHandler.isUpdateCheckFrequent()) {
            this.sendAsyncUpdateStatus(joinedPlayer);
        } else {
            this.sendUpdateStatus(joinedPlayer);
        }
    }

    private void sendAsyncUpdateStatus(ProxiedPlayer joinedPlayer) {
        this.plugin.getProxy().getScheduler().runAsync(this.plugin, () -> {
            EventListener.this.plugin.updateReleaseCache();
            EventListener.this.sendUpdateStatus(joinedPlayer);
        });
    }

    /**
     * Send the update status to the player who has joined the server
     *
     * @param joinedPlayer
     * @param isUpdated
     */
    private void sendUpdateStatus(ProxiedPlayer joinedPlayer) {
        if (this.plugin.getUpdateStatus()) {
            joinedPlayer.sendMessage(TextComponent.fromLegacyText(plugin.getUpdatePlayerLogString()));
            return;
        }

        if (this.configHandler.shouldLogUpToDate()) {
            joinedPlayer.sendMessage(TextComponent.fromLegacyText(this.plugin.getUpToDatePlayerLogString()));
        }
    }
}
