package com.github.kory33.UpdateNotificationPlugin.versioning;

/**
 * Class that represents the semantic versioning of the plugin.
 * <p>
 * The concept of semantic versioning comforms to the document in
 * <a href="http://semver.org/">http://semver.org/</a>
 * @author Kory33, Bnana877
 *
 */
public class SemanticPluginVersion extends PluginVersion{
    /** major version of the plugin */
    private int major;
    
    /** minor version of the plugin */
    private int minor;
    
    /** patch version of the plugin */
    private int patch;
    
    /** identifier of the plugin verison */
    private String identifier;

    /** build metadata of the plugin verison */
    private String buildMetadata;
    
    public SemanticPluginVersion(String versionString) {
        super(versionString);
        //TODO implementation
    }
    
    /**
     * Returns true if the version contains any identifier.
     * @return
     */
    public boolean hasIdentifier(){
        return !this.identifier.equals("");
    }
    
    /**
     * Returns true if the version contains any metadata
     * @return true if the version contains any metadata
     */
    public boolean hasBuildMetadata(){
        return !this.buildMetadata.equals("");
    }
    
    @Override
    public boolean isNewerThan(String versionString) {
        //TODO implementation
        return false;
    }
}
