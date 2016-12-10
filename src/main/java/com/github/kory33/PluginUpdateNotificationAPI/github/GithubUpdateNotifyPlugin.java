package com.github.kory33.PluginUpdateNotificationAPI.github;

import com.github.kory33.PluginUpdateNotificationAPI.PluginRelease;
import com.github.kory33.PluginUpdateNotificationAPI.UpdateNotifyPlugin;

public abstract class GithubUpdateNotifyPlugin extends UpdateNotifyPlugin {
    private final GithubVersionManager gVersionManager = new GithubVersionManager();
    
    /**
     * Get the reference to the latest version that is released on Github
     * @return
     */
    @Override
    public PluginRelease getLatestRelease(){
        return this.gVersionManager.getLatestVersionRelease();
    }

    
    @Override
    public boolean checkForUpdate() {
        PluginRelease release = this.getLatestRelease();
        if(release == null){
            return false;
        }
        
        return release.isNewerThanCurrent();
    }
}
