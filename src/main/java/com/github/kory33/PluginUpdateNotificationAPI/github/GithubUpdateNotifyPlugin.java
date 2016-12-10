package com.github.kory33.PluginUpdateNotificationAPI.github;

import com.github.kory33.PluginUpdateNotificationAPI.UpdateNotifyPlugin;

public abstract class GithubUpdateNotifyPlugin extends UpdateNotifyPlugin {
    private final GithubVersionManager gVersionManager = new GithubVersionManager();
    
    /**
     * Get the reference to the latest version that is released on Github
     * @return
     */
    private GithubRelease getLatestRelease(){
        return this.gVersionManager.getLatestVersionRelease();
    }
    
    @Override
    public boolean checkForUpdate() {
        return this.getLatestRelease() != null;
    }
    
    @Override
    public String getUpdateLogString() {
        GithubRelease latestRelease = this.getLatestRelease();
        return "New version available! " + this.getPluginName() + latestRelease.getVersion() + "[" + latestRelease.getLink() + "]";
    }

    @Override
    public String getUpdatePlayerLogString() {
        GithubRelease latestRelease = this.getLatestRelease();
        return "New version available! " + this.getPluginName() + latestRelease.getVersion() + "[" + latestRelease.getLink() + "]";
    }

    @Override
    public String getUpToDateLogString() {
        return this.getPluginName() + " is up-to-date.";
    }

    @Override
    public String getUpToDatePlayerLogString() {
        return this.getPluginName() + " is up-to-date.";
    }
}
