package com.github.kory33.UpdateNotificationPlugin;

public class PluginRelease {
    private final PluginVersion version;
    private final String linkURL;
    
    public PluginRelease(PluginVersion version, String linkURL){
        this.version = version;
        this.linkURL = linkURL;
    }
    
    public String getVersionString() {
        return version.toString();
    }
    
    public String getLink() {
        return linkURL;
    }

    public boolean isNewerThanCurrent(PluginVersion currentVersion){
        return this.version.newerThan(currentVersion);
    };
}