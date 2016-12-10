package com.github.kory33.PluginUpdateNotificationAPI.github;

import com.github.kory33.PluginUpdateNotificationAPI.UpdateNotifyPlugin;

public abstract class GithubUpdateNotifyPlugin extends UpdateNotifyPlugin {
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
        // TODO implementation
        return false;
    }

    @Override
    public String getUpdateLogString() {
        // TODO implementation
        return null;
    }

    @Override
    public String getUpdatePlayerLogString() {
        // TODO implementation
        return null;
    }

    @Override
    public String getUpToDateLogString() {
        // TODO implementation
        return null;
    }

    @Override
    public String getUpToDatePlayerLogString() {
        // TODO implementation
        return null;
    }
}
