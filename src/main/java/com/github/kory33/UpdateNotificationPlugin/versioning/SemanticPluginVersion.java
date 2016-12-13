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
        
        String[] split_versionString = versionString.split("[\\.-]",4);
        
        major = Integer.parseInt(split_versionString[0]);
        minor =  Integer.parseInt(split_versionString[1]);
        patch =  Integer.parseInt(split_versionString[2]);
        identifier = null;
        buildMetadata = null;
        
        if(split_versionString[3].isEmpty())return;
        int identifier_pos = versionString.indexOf("-");
        
        if(identifier_pos == -1){
        	buildMetadata = split_versionString[3].substring(1, split_versionString[3].length());
            return;
        }
        String sub_identifier = versionString.substring( identifier_pos +1, versionString.length());
        String[] split_identifier = sub_identifier.split("+",2);
        if(!split_identifier[0].isEmpty())identifier = split_identifier[0];
        if(!split_identifier[1].isEmpty())buildMetadata = split_identifier[1];
        return;
        
        
        
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
    	SemanticPluginVersion version = new SemanticPluginVersion(versionString);
    	if(version.major != major)return version.major > major ? true:false;
    	if(version.minor != minor)return version.minor > minor ? true:false;
    	if(version.patch != patch)return version.patch > patch ? true:false;
    	String[] version_1 = identifier.split("\\.") ;
        String[] version_2 = version.identifier.split("\\.");
    	int lim =version_1.length > version_2.length ?version_2.length : version_1.length;
    	if(lim ==0)return version_1.length == 0 ?true : false;
    	for(int i = 0; i < lim; i++){
    	    int r  = 0;
    	    int compare = version_1[i].compareTo(version_2[i]);
			if(compare != 0) r = compare < 0 ? 1 : -1;
    		boolean check = false;
				try {
    			      int compare1 =Integer.parseInt(version_1[i]);
    				  check =true;
    				  int compare2 =Integer.parseInt(version_2[i]);
    				  if(compare1 != compare2) r = compare1 > compare2 ? 1:-1; 
    					   
    				 } catch (NumberFormatException e) {
    				  r = check == false ? 1 : -1;
    				 }
    		   if(r!=0)return r>0?true:false;
    	  }
    				
    	return version_1.length > version_2.length ? false:true;
    		
    	
    }
}
