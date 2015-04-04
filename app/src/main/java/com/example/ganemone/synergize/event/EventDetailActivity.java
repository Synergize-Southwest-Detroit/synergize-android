package com.example.ganemone.synergize.event;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganemone.synergize.APIManager;
import com.example.ganemone.synergize.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;


public class EventDetailActivity extends ActionBarActivity {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent intent = getIntent();
        int eid = intent.getIntExtra(EventsFragment.EVENT_EXTRA, -1);
        if (eid == -1) {
            Toast.makeText(getApplicationContext(), "Error loading event data", Toast.LENGTH_LONG).show();
        }
        event = APIManager.getInstance().getEventByID(eid);
        this.setTitle(event.title);

        TextView title = (TextView) findViewById(R.id.event_title);
        TextView dateRange = (TextView) findViewById(R.id.event_date_range);
        TextView description = (TextView) findViewById(R.id.event_description);
        TextView location = (TextView) findViewById(R.id.event_location);

        event.setUpWithViews(title, dateRange, description, location);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_to_calendar) {
            this.addEventToCalendar();
            return true;
        }
        if (id == R.id.view_on_map) {
            this.viewEventOnMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void viewEventOnMap() {
        try {
            String map = "http://maps.google.co.in/maps?q=" + URLEncoder.encode(event.address, "utf-8");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void addEventToCalendar() {
        int apiVersion = android.os.Build.VERSION.SDK_INT;
        if (apiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Calendar beginTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.start.getTime())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.end.getTime())
                    .putExtra(CalendarContract.Events.TITLE, event.title)
                    .putExtra(CalendarContract.Events.DESCRIPTION, event.description)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, event.getDisplayLocation());
            startActivity(intent);
        }
    }

    public void viewEventInMaps(View view) {
        this.viewEventOnMap();
    }

    public void addEventToCalendar(View view) {
        this.addEventToCalendar();
    }
}
