package com.github.kory33.UpdateNotificationPlugin.bukkit.github;

import com.github.kory33.UpdateNotificationPlugin.bukkit.UpdateNotificationPlugin;
import com.github.kory33.UpdateNotificationPlugin.github.GithubVersionManager;

public abstract class GithubUpdateNotifyPlugin extends UpdateNotificationPlugin {
    private final GithubVersionManager gVersionManager = new GithubVersionManager(this);
    
    @Override
    public void updateReleaseCache(){
        this.updateReleaseCache(this.gVersionManager.getLatestVersionRelease());
    }
    
    /**
     * Return the repository location in a format of [owner]/[repository]
     * @return
     */
    public abstract String getGithubRepository();
}
