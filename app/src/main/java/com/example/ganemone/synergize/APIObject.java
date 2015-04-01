package com.example.ganemone.synergize;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by ganemone on 4/1/15.
 */
public abstract class APIObject {

    public APIObject(JSONObject json) throws ParseException, JSONException {
    }

    protected APIObject() {
    }
}
