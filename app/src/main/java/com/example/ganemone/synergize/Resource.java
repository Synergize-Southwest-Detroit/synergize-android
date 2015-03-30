package com.example.ganemone.synergize;

import java.net.URL;

/**
 * Created by ganemone on 3/30/15.
 */
public class Resource {
    public int id;
    public String title;
    public URL link;

    public Resource(int id, String title, URL link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }
}
