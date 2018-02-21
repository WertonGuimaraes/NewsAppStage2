package com.udacity.wertonguimaraes.newsapp.util;

import com.udacity.wertonguimaraes.newsapp.model.Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Parse {
    private JSONObject mJsonResponse;
    private JSONArray mJsonResults;

    public Parse(String jsonString) throws JSONException {
        JSONObject jsonData = new JSONObject(jsonString);
        mJsonResponse = jsonData.getJSONObject("response");
        mJsonResults = mJsonResponse.getJSONArray("results");
    }

    public List<Info> convertJsonToInfoObjects() throws JSONException, MalformedURLException {
        List<Info> infoObjects = new ArrayList<>();
        for (int i=0; i<mJsonResults.length(); i++) {
            JSONObject newsJson = mJsonResults.getJSONObject(i);
            String title = newsJson.getString("webTitle");
            String section = newsJson.getString("sectionName");
            String webPublicationDate = newsJson.optString("webPublicationDate");
            String author = "";
            if (newsJson.has("fields")) {
                author = newsJson.getJSONObject("fields").optString("byline");
            }
            String webUrl = newsJson.optString("webUrl");

            infoObjects.add(new Info(title, section, webPublicationDate, author, webUrl));
        }
        return infoObjects;
    }
}
