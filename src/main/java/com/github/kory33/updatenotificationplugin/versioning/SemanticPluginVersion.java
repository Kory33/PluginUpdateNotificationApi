package com.github.kory33.updatenotificationplugin.versioning;

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
        
        if(versionString.matches("^(0|([1-9][0-9]*))\\.(0|([1-9][0-9]*))\\.(0|([1-9][0-9]*))(\\-[0-9A-Za-z\\.]+)?(\\+[0-9A-Za-z\\.]+)?$") == false){
            throw new IllegalArgumentException();
        }
        String[] split_versionString = versionString.split("\\.",3);
        major = Integer.parseInt(split_versionString[0]);
        minor =  Integer.parseInt(split_versionString[1]);
        
        int identifier_pos = split_versionString[2].indexOf("-");
        if(split_versionString[2].matches("^[0-9]+$")) {
            patch = Integer.parseInt(split_versionString[2]);
        }else if(identifier_pos != -1){
            patch =  Integer.parseInt(split_versionString[2].substring(0,identifier_pos));
        }else if(split_versionString[2].indexOf("+") != -1){
            patch =  Integer.parseInt( split_versionString[2].substring(0, split_versionString[2].indexOf("+")));
        }
        
        identifier = null;
        buildMetadata = null;
          String buildMetadata_identifier = null;
          if(identifier_pos != -1 || versionString.indexOf("+") != -1){
              buildMetadata_identifier = versionString.substring((identifier_pos != -1?identifier_pos:versionString.indexOf("+"))+1,versionString.length());
          }
        if(buildMetadata_identifier==null){
            return;
        }
        if(identifier_pos == -1){
            buildMetadata =  split_versionString[2].substring(2,  split_versionString[2].length());
            return;
        }
        String sub_identifier = split_versionString[2].substring(identifier_pos +1, split_versionString[2].length());
        String[] split_identifier = sub_identifier.split("\\+",2);
        if(split_identifier.length >= 1 ) {
            identifier = split_identifier[0];
        }
        if(split_identifier.length >= 2 ) {
            buildMetadata = split_identifier[1];
        }
        return;
    }
     
    /**
     * Returns true if the version contains any identifier.
     * @return
     */
    public boolean hasIdentifier(){
        return !"".equals(this.identifier);
    }
    
    /**
     * Returns true if the version contains any metadata
     * @return true if the version contains any metadata
     */
    public boolean hasBuildMetadata(){
        return !"".equals(this.buildMetadata);
    }
    
    @Override
    public boolean isNewerThan(String versionString) {
        SemanticPluginVersion version = new SemanticPluginVersion(versionString);
        if(version.major != major) {
            return version.major < major;
        }
        if(version.minor != minor) {
            return version.minor < minor;
        }
        if(version.patch != patch) {
            return version.patch < patch;
        }
        if(version.identifier == null) {
            return false;
        }
        if(identifier == null) {
            return version.identifier != null;
        }
        String[] version_1 = identifier.split("\\.") ;
        String[] version_2 = version.identifier.split("\\.");
        int lim =version_1.length > version_2.length ?version_2.length : version_1.length;
        if(lim ==0) {
            return version_1.length == 0;
        }
        for(int i = 0; i < lim; i++){
            boolean r = false;
            int compare = version_1[i].compareTo(version_2[i]) * -1;
            if(compare != 0) {
                r = compare < 0;
            } else if(i == lim - 1) {
                return version_1.length > version_2.length;
            } else {
                continue;
            }
            if (version_1[i].matches("[0-9]+")!=version_2[i].matches("[0-9]+")) {
                return version_1[i].matches("[0-9]+") == false; 
            } else if(version_1[i].matches("[0-9]+") == true) {
                int compare1 =Integer.parseInt(version_1[i]);
                int compare2 =Integer.parseInt(version_2[i]);
                if(compare1 != compare2) return  compare1 > compare2;        
            } else {
                return r;
            }
        }
        return version.identifier.length() < identifier.length() ;
    }
   
}
