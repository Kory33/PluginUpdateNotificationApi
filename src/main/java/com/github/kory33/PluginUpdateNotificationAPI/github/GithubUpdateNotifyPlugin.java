package com.github.kory33.PluginUpdateNotificationAPI.github;

import java.util.List;

import com.github.kory33.PluginUpdateNotificationAPI.UpdateNotifyPlugin;

public abstract class GithubUpdateNotifyPlugin extends UpdateNotifyPlugin {
    private final GithubVersionManager gVersionManager = new GithubVersionManager();
    
    /**
     * <p>
     * This function is expected to return true if the plugin should follow <strong>only version releases.</strong>
     * Version releases are the releases which are tagged with version, for instance, 1.0.2 or 0.5.3.2.
     * <p>
     * For the release to be considered as "new version", there should be increase in the version number,
     * and the release must have a tag that matches the following regular expression: ($v?%d(\.%d)*)
     */
    public abstract boolean followVersions();

    @Override
    public boolean checkForUpdate() {
        List<GithubRelease> releases;
        if(this.followVersions()){
            releases = this.gVersionManager.getVersionReleases();
        }else{
            releases = this.gVersionManager.getReleases();
        }
        
        if(releases.iterator().hasNext()){
            return true;
        }
        
        return false;
    }
    
    
    public GithubRelease getLatestRelease(){
        if(!this.checkForUpdate()){
            return null;
        }
        
        if(this.followVersions()){
            return this.gVersionManager.getLatestVersionRelease();
        }else{
            return this.gVersionManager.getLatestRelease();
        }
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
