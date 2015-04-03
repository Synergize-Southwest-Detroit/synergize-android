package com.example.ganemone.synergize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by ganemone on 3/29/15.
 */
public class ResourcesFragment extends Fragment implements AdapterView.OnItemClickListener {

    public final static String RESOURCE_EXTRA = "com.synergize.synergize.resource_extra";

    ListView listView;
    ResourceListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ResourceListAdapter(getActivity().getApplicationContext());
        APIManager manager = APIManager.getInstance();
        if (manager.resources.size() == 0) {
            manager.loadResources(new Closure() {
                @Override
                public void onSuccess() {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to load resources, " + message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.resources_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.resource_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Resource item = adapter.getItem(position);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.link));
        startActivity(browserIntent);
    }
}
