package com.github.kory33.updatenotificationplugin.bukkit.github;

import com.github.kory33.updatenotificationplugin.bukkit.UpdateNotificationPlugin;
import com.github.kory33.updatenotificationplugin.github.GithubVersionManager;
import com.github.kory33.updatenotificationplugin.github.IGithubRepositoryHolder;

public abstract class GithubUpdateNotifyPlugin extends UpdateNotificationPlugin implements IGithubRepositoryHolder{
    private final GithubVersionManager gVersionManager = new GithubVersionManager(this.getGithubRepository(), this.getLogger());
    
    @Override
    public void updateReleaseCache(){
        this.updateReleaseCache(this.gVersionManager.getLatestVersionRelease());
    }
}
