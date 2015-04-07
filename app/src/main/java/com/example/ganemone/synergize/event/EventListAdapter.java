package com.example.ganemone.synergize.event;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganemone.synergize.Closure;
import com.example.ganemone.synergize.api.APIManager;
import com.example.ganemone.synergize.R;

/**
 * Created by ganemone on 3/30/15.
 */
public class EventListAdapter extends ArrayAdapter<Event> {

    private Closure closure = new Closure() {
        @Override
        public void onSuccess() {
            EventListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onFailure(String message) {
            Toast.makeText(getContext(), "Failed to load more events, " + message, Toast.LENGTH_LONG).show();
        }
    };

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

        event.setUpWithViews(titleTextView, dateTextView, null, locationTextView);

        if (position == this.getCount() - 1 && APIManager.getInstance().hasNextEventPage()) {
            APIManager.getInstance().loadEvents(closure);
        }

        return convertView;
    }


}
