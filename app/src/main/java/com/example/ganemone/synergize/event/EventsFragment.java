package com.example.ganemone.synergize.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ganemone.synergize.RefreshingFragment;
import com.example.ganemone.synergize.api.APIManager;
import com.example.ganemone.synergize.Closure;
import com.example.ganemone.synergize.R;

import java.util.Date;

/**
 * Created by ganemone on 3/29/15.
 */
public class EventsFragment extends Fragment implements ListView.OnItemClickListener, RefreshingFragment {

    public final static String EVENT_EXTRA = "com.synergize.synergize.EVENT_EXTRA";
    EventListAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new EventListAdapter(getActivity().getApplicationContext());
        APIManager manager = APIManager.getInstance();
        if (manager.events.size() == 0) {
            manager.loadEvents(new Closure() {
                @Override
                public void onSuccess() {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to load events, " + message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.events_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.event_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        Event event = adapter.getItem(position);
        Date now = new Date();
        intent.putExtra(EVENT_EXTRA, 1);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading Events");
        progress.show();
        APIManager.getInstance().reloadEvents(new Closure() {
            @Override
            public void onSuccess() {
                progress.hide();
                adapter.notifyDataSetChanged();
                listView.setSelectionAfterHeaderView();
            }

            @Override
            public void onFailure(String message) {
                progress.hide();
                Toast.makeText(getActivity().getApplicationContext(), "Failed to load events, " + message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
