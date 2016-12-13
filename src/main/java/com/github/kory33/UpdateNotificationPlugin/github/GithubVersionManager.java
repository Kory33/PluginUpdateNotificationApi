package com.github.kory33.UpdateNotificationPlugin.github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;

import com.github.kory33.UpdateNotificationPlugin.PluginRelease;
import com.github.kory33.UpdateNotificationPlugin.versioning.PluginVersion;
import com.github.kory33.UpdateNotificationPlugin.versioning.SemanticPluginVersion;
import com.google.common.base.Throwables;

public class GithubVersionManager {
    private final GithubUpdateNotifyPlugin plugin;
    public GithubVersionManager(GithubUpdateNotifyPlugin plugin){
        this.plugin = plugin;
    }
    
    /**
     * Get the latest version release.
     * <p>
     * This method is <strong>synchronous</strong>.
     * @return reference to the latest version release
     */
    public PluginRelease getLatestVersionRelease() {
        
        return null;
    }
    
    /**
     * get the Github release api's url from the plugin
     * @return
     */
    private String getGHReleaseAPIUrl() {
        return "https://api.github.com/repos/" + this.plugin.getGithubRepository() + "/releases";
    }
    
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
    
    private List<PluginVersion> getVersionsList() throws JSONException, ClientProtocolException, IOException{
        JSONArray jsonArray = new JSONArray(this.getReleaseJSONString());
        
        List<PluginVersion> versionList= new ArrayList<>();
        
        // TODO implementation
        
        return versionList;
    }
}
