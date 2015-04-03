package com.example.ganemone.synergize;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ganemone on 3/30/15.
 */
public class Resource extends APIObject {
    public int id;
    public String title;
    public String link;

    public Resource(int id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public Resource(JSONObject obj) throws JSONException {
        this(obj.getInt("id"), obj.getString("title"), obj.getString("resource"));
    }
}
