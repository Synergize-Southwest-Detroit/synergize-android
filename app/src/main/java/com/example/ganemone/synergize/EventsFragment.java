package com.example.ganemone.synergize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ganemone on 3/29/15.
 */
public class EventsFragment extends Fragment {

    EventListAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Event> events = new ArrayList<Event>();
        Date now = new Date();
        events.add(new Event(1, "Title", "Description", now, now, "3114 Bronson Blvd", "", true, false, new Category(1, "Music")));
        events.add(new Event(2, "Title", "Description", now, now, "3114 Bronson Blvd", "", true, false, new Category(1, "Music")));
        events.add(new Event(3, "Title", "Description", now, now, "3114 Bronson Blvd", "", true, false, new Category(1, "Music")));
        events.add(new Event(4, "Title", "Description", now, now, "3114 Bronson Blvd", "", true, false, new Category(1, "Music")));
        adapter = new EventListAdapter(getActivity().getApplicationContext(), events);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.events_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.event_list_view);
        listView.setAdapter(adapter);
        return rootView;
    }
}
