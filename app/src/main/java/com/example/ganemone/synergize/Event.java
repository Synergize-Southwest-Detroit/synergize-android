package com.example.ganemone.synergize;

import java.util.Date;

/**
 * Created by ganemone on 3/30/15.
 */
public class Event {
    public int id;
    public String title;
    public String description;
    public Date start;
    public Date end;
    public String address;
    public String displayAddress;
    public Boolean isApproved;
    public Boolean isReported;
    public Category category;

    public Event(int id, String title, String description, Date start, Date end, String address, String displayAddress, Boolean isApproved, Boolean isReported, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.address = address;
        this.displayAddress = displayAddress;
        this.isApproved = isApproved;
        this.isReported = isReported;
        this.category = category;
    }

    public String getFormattedDateRange() {
        return "Temporary formatted date range";
    }

    public String getDisplayLocation() {
        if (this.displayAddress.length() > 0) {
            return this.displayAddress;
        }
        return this.address;
    }

}
