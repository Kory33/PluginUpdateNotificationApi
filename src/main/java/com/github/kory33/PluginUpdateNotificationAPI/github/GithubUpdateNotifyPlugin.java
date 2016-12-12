package com.github.kory33.PluginUpdateNotificationAPI.github;

import com.github.kory33.PluginUpdateNotificationAPI.UpdateNotifyPlugin;

public abstract class GithubUpdateNotifyPlugin extends UpdateNotifyPlugin {
    private final GithubVersionManager gVersionManager = new GithubVersionManager(this);
    
    @Override
    public void updateReleaseCacheSync(){
        this.updateReleaseCache(this.gVersionManager.getLatestVersionRelease());
    }
    
    @Override
    public void updateReleaseCacheAsync() {
        // TODO implementation
    };
    
    /**
     * Return the repository location in a format of [owner]/[repository]
     * @return
     */
    public abstract String getGithubRepository();
}
