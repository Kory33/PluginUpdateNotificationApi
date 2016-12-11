package com.github.kory33.PluginUpdateNotificationAPI;

public abstract class PluginRelease {
    private final String version;
    private final String linkURL;
    
    public PluginRelease(String version, String linkURL){
        this.version = version;
        this.linkURL = linkURL;
    }
    
    public String getVersion() {
        return version;
    }

    public String getLink() {
        return linkURL;
    }

    public abstract boolean isNewerThanCurrent();
}
