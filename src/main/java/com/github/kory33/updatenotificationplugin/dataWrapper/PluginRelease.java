package com.github.kory33.updatenotificationplugin.dataWrapper;

import com.github.kory33.updatenotificationplugin.versioning.PluginVersion;

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

    public PluginVersion getVersion(){
        return this.version;
    };
}
