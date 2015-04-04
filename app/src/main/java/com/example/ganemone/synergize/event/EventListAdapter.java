package com.example.ganemone.synergize.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ganemone.synergize.api.APIManager;
import com.example.ganemone.synergize.R;

/**
 * Created by ganemone on 3/30/15.
 */
public class EventListAdapter extends ArrayAdapter<Event> {

    public EventListAdapter(Context context) {
        super(context, R.layout.event_list_item, APIManager.getInstance().events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.event_list_item, parent, false);
        }

        Event event = getItem(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date_range);
        TextView locationTextView = (TextView) convertView.findViewById(R.id.location);

        titleTextView.setText(event.title);
        dateTextView.setText(event.getFormattedDateRange());
        locationTextView.setText(event.getDisplayLocation());

        return convertView;
    }
}