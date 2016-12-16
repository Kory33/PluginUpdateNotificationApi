package com.github.kory33.updatenotificationplugin.github;

public interface IGithubRepositoryHolder {
    /**
     * Return the repository location in a format of [owner]/[repository]
     * @return
     */
    public abstract String getGithubRepository();
}
