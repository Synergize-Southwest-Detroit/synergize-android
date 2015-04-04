package com.example.ganemone.synergize.howto;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by ganemone on 4/4/15.
 */
public class Step {
    public String title;
    public String body;
    public String link;
    public Bitmap image;

    public Step(String title, String body, String link) {
        this.title = title;
        this.body = body;
        this.link = link;
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
}
