package com.example.ganemone.synergize.howto;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganemone.synergize.api.APIObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ganemone on 4/4/15.
 */
public class Step extends APIObject implements Comparable<Step> {
    public String title;
    public String body;
    public String link;
    public Bitmap image;
    public int order;

    public static ArrayList<Step> createStepArray(JSONArray rawSteps) throws JSONException {
        ArrayList<Step> steps = new ArrayList<>();
        for (int i = 0; i < rawSteps.length(); i++) {
            steps.add(new Step(rawSteps.getJSONObject(i)));
        }
        return steps;
    }

    public Step(String title, String body, String link, int order) {
        this.title = title;
        this.body = body;
        this.link = link;
        this.order = order;
    }

    public Step(JSONObject jsonObject) throws JSONException {
        this(
                jsonObject.getString("title"),
                jsonObject.getString("description"),
                jsonObject.getString("image"),
                jsonObject.getInt("number")
        );
    }

    public void setUpWithViews(TextView title, TextView body) {
        title.setText(this.title);
        body.setText(this.body);
    }

    public boolean shouldLoadImage() {
        return (this.image == null && this.link.length() > 0);
    }

    public void setUpWithViews(TextView titleTextView, TextView bodyTextView, ImageView imageView) {
        this.setUpWithViews(titleTextView, bodyTextView);
        if (this.image != null) {
            imageView.setImageBitmap(this.image);
        }
        else if (this.link.length() > 0) {
            ImageLoader.getInstance().displayImage(this.link, imageView);
        }
    }

    @Override
    public int compareTo(Step another) {
        return this.order - another.order;
    }
}
