package com.github.kory33.UpdateNotificationPlugin.github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.kory33.UpdateNotificationPlugin.PluginRelease;
import com.github.kory33.UpdateNotificationPlugin.versioning.PluginVersion;
import com.github.kory33.UpdateNotificationPlugin.versioning.SemanticPluginVersion;

/**
 * 
 * @author Kory33
 *
 */
public class GithubVersionManager {
    private final GithubUpdateNotifyPlugin plugin;
    public GithubVersionManager(GithubUpdateNotifyPlugin plugin){
        this.plugin = plugin;
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
            this.plugin.getLogger().log(Level.WARNING, "Caught exceptions while fetching and parsing data from Github:", e);
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
    private String getGHReleaseAPIUrl() {
        return "https://api.github.com/repos/" + this.plugin.getGithubRepository() + "/releases";
    }
    
    /**
     * Access to the Github API and get the release data.
     * @return string data received from github api
     * @throws ClientProtocolException
     * @throws IOException
     */
    private String getReleaseJSONString() throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGetMethod = new HttpGet(this.getGHReleaseAPIUrl());
        
        // Execute the http get method and gain the response from the server
        HttpResponse response = httpClient.execute(httpGetMethod);
        
        int responseStatusCode = response.getStatusLine().getStatusCode();
        
        if (responseStatusCode != 200) {
            throw new HttpResponseException(
                    responseStatusCode, "Got unexpected status response. Is the url " + this.getGHReleaseAPIUrl() + " proper?"
                    );
        }
        
        // get and return the response body
        HttpEntity responseEntity = response.getEntity();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
        
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        
        return stringBuilder.toString();
    }
    
    /**
     * Generate PluginRelease instance from JSONObject.
     * <p>
     * If the JSONObject does not have version string in "tag_name" and url in "html_url",
     * or the version is invalid, this method returns null
     * @param releaseJson
     * @return instance of PluginRelease
     */
    private PluginRelease getPluginReleaseFromJSON(JSONObject releaseJson) {
        String versionString = null, releaseHTMLUrl = null;
        try{
            versionString = releaseJson.getString("tag_name");
            //remove non-number characters at the beginning of the version string
            versionString = versionString.replace("[^0-9]", "");
            
            releaseHTMLUrl = releaseJson.getString("html_url");
        }catch(JSONException e){
            this.plugin.getLogger().log(
                    Level.WARNING, "Caught exception while parsing JSON. Data might be corrupted while being transmitted.", e
            );
            return null;
        }
        
        try{
            PluginVersion releaseVersion = new SemanticPluginVersion(versionString);
            return new PluginRelease(releaseVersion, releaseHTMLUrl);
        }catch (IllegalArgumentException e) {
            this.plugin.getLogger().log(
                    Level.WARNING, "Version " + versionString + " was found, but was ignored."
            );
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
    public List<PluginRelease> getReleasesList() throws JSONException, ClientProtocolException, IOException{
        JSONArray releaseJsonArray = new JSONArray(this.getReleaseJSONString());
        
        List<PluginRelease> releaseList = new ArrayList<>();
        
        for(int i = 0; i < releaseJsonArray.length(); i++) {
            JSONObject releaseJson = releaseJsonArray.getJSONObject(i);
            
            PluginRelease release = this.getPluginReleaseFromJSON(releaseJson);
            
            if(release == null){
                continue;
            }
            
            releaseList.add(release);
        }
        
        return releaseList;
    }
}
