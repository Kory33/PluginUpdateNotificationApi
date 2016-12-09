package com.gethub.kory33.githubbukkitpluginupdateapi;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class GithubUpdateNotifyPlugin extends JavaPlugin {
    public abstract String getGithubRepositoryUrl(); 
    
    @Override
    public void onEnable() {
        // TODO 自動生成されたメソッド・スタブ
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        // TODO 自動生成されたメソッド・スタブ
        super.onDisable();
    }
}
