package com.example.ganemone.synergize;

import com.example.ganemone.synergize.api.APIObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ganemone on 3/30/15.
 */
public class Category extends APIObject {
    public int id;
    public String category;

    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public Category(JSONObject obj) throws JSONException {
        this(obj.getInt("id"), obj.getString("category"));
    }
}
