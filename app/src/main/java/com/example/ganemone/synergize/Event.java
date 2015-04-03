package com.example.ganemone.synergize;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ganemone on 3/30/15.
 */
public class Event extends APIObject {
    public int id;
    public String title;
    public String description;
    public Date start;
    public Date end;
    public String address;
    public String displayAddress;
    public Category category;

    public Event(int id, String title, String description, Date start, Date end, String address, String displayAddress, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.address = address;
        this.displayAddress = displayAddress;
        this.category = category;
    }

    public Event(int id, String title, String description, String start, String end, String address, String displayAddress, Category category) throws ParseException {
        this.id = id;
        this.title = title;
        this.description = description;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        this.start = formatter.parse(start);
        this.end = formatter.parse(end);
        this.address = address;
        this.displayAddress = displayAddress;
        this.category = category;
    }

    public Event(JSONObject object) throws ParseException, JSONException {
        this(
                object.getInt("id"),
                object.getString("name"),
                object.getString("description"),
                object.getString("start"),
                object.getString("end"),
                object.getString("address"),
                object.getString("display_address"),
                new Category(object.getJSONArray("categories").getJSONObject(0))
        );
    }

    public String getFormattedDateRange() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);
        String start = formatter.format(this.start);
        if (this.isSingleDayEvent()) {
            SimpleDateFormat endFormatter = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            return start + " - " + endFormatter.format(this.end);
        }
        return start + " - " + formatter.format(this.end);
    }

    public Boolean isSingleDayEvent() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(this.start);
        cal2.setTime(this.end);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }


    public String getDisplayLocation() {
        if (this.displayAddress.length() > 0) {
            return this.displayAddress;
        }
        return this.address;
    }

}
