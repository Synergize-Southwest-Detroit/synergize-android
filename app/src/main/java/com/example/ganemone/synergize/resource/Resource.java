package com.example.ganemone.synergize.resource;

import android.content.Intent;
import android.net.Uri;

import com.example.ganemone.synergize.api.APIObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ganemone on 3/30/15.
 */
public class Resource extends APIObject {
    public int id;
    public String title;
    public String link;

    public static ArrayList<Resource> createResourceArray(JSONArray rawResources) throws JSONException {
        ArrayList<Resource> resources = new ArrayList<>();
        for (int i = 0; i < rawResources.length(); i++) {
           resources.add(new Resource(rawResources.getJSONObject(i)));
        }
        return resources;
    }

    public Resource(int id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public Resource(JSONObject obj) throws JSONException {
        this(obj.getInt("id"), obj.getString("title"), obj.getString("resource"));
    }

    public Intent getIntentForLink() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(this.link));
    }
}
