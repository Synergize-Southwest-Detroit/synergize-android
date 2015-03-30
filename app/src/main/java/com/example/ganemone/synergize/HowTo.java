package com.example.ganemone.synergize;

import java.util.ArrayList;

/**
 * Created by ganemone on 3/30/15.
 */
public class HowTo {
    public int id;
    public String title;
    public String description;
    public ArrayList<Resource> resources;

    public HowTo(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public HowTo(int id, String title, String description, ArrayList<Resource> resources) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.resources = resources;
    }
}
