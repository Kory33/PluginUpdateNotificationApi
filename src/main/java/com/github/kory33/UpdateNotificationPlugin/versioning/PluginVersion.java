package com.github.kory33.UpdateNotificationPlugin.versioning;

public abstract class PluginVersion {
    private final String versionString;
    
    protected PluginVersion(String versionString) {
        this.versionString = versionString;
    }
    
    /**
     * returns if this version is newer than a version that corresponds to the given string
     * @param versionString version string to compare with
     * @return if this version is newer than the given version
     */
    public abstract boolean isNewerThan(String versionString);
    
    @Override
    public String toString() {
        return this.versionString;
    }
}
