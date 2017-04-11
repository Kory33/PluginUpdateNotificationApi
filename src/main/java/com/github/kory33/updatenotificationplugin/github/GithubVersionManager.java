package com.github.kory33.updatenotificationplugin.github;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.kory33.updatenotificationplugin.dataWrapper.PluginRelease;
import com.github.kory33.updatenotificationplugin.versioning.PluginVersion;
import com.github.kory33.updatenotificationplugin.versioning.SemanticPluginVersion;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * @author Kory33
 *
 */
public class GithubVersionManager {
    private final Logger serverLogger;
    private final String githubRepository;
    
    public GithubVersionManager(String githubRepository, Logger serverConsoleLogger){
        this.githubRepository = githubRepository;
        this.serverLogger = serverConsoleLogger;
    }
    
    /**
     * Get the latest version release.
     * This method does not have any modification of the member variable,
     * therefore it may be called from multiple threads.
     * @return reference to the latest version release
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws JSONException 
     */
    public PluginRelease getLatestVersionRelease(){
        List<PluginRelease> releaseList = null;

        try{
            releaseList = this.getReleasesList();
        } catch (Exception e) {
            this.serverLogger.log(Level.WARNING, "Caught exceptions while fetching and parsing data from Github:", e);
            return null;
        }
        
        if (releaseList.isEmpty()) {
            return null;
        }
        
        return releaseList.get(0);
    }
    
    /**
     * get the Github release api's url from the plugin
     * @return
     */
    private URL getGHReleaseAPIUrl() {
        try {
            return new URL("https://api.github.com/repos/" + this.githubRepository + "/releases");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    
    /**
     * Access to the Github API and get the release data.
     * @return string data received from github api
     * @throws ClientProtocolException
     * @throws IOException
     */
    private JsonArray getReleaseJSONArray() throws IOException {
        URL url = this.getGHReleaseAPIUrl();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Update-Notification-Plugin--Github-Version-Manager");

        int responseStatusCode = connection.getResponseCode();
        if (responseStatusCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Got unexpected status response(" + responseStatusCode + "). Is the url "
                    + this.getGHReleaseAPIUrl() + " proper?");
        }
        
        return (new JsonParser()).parse(new InputStreamReader(connection.getInputStream())).getAsJsonArray();
    }
    
    /**
     * Generate PluginRelease instance from JSONObject.
     * <p>
     * If the JSONObject does not have version string in "tag_name" and url in "html_url",
     * or the version is invalid, this method returns null
     * @param releaseJson
     * @return instance of PluginRelease
     */
    private PluginRelease getPluginReleaseFromJSON(JsonObject releaseJson) {
        String versionString = releaseJson.get("tag_name").getAsString();
        //remove non-number characters at the beginning of the version string
        versionString = versionString.replace("[^0-9]", "");
            
        String releaseHTMLUrl = releaseJson.get("html_url").getAsString();
        
        try{
            PluginVersion releaseVersion = new SemanticPluginVersion(versionString);
            return new PluginRelease(releaseVersion, releaseHTMLUrl);
        }catch (IllegalArgumentException e) {
            this.serverLogger.log(Level.WARNING, "Version " + versionString + " was found, but was ignored.");
            return null;
        }
    }
    
    /**
     * Get the plugin-release's list.
     * <p>
     * This method involves downloading data from Github.
     * @return list of releases
     * @throws JSONException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public List<PluginRelease> getReleasesList() throws IOException{
        JsonArray releaseJsonArray = this.getReleaseJSONArray();
        
        List<PluginRelease> releaseList = new ArrayList<>();
        releaseJsonArray.forEach(element -> releaseList.add(this.getPluginReleaseFromJSON(element.getAsJsonObject())));
        
        return releaseList;
    }
}
