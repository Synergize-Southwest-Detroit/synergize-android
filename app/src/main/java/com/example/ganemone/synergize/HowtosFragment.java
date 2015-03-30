package com.example.ganemone.synergize;

import android.app.ListFragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ganemone on 3/29/15.
 */
public class HowtosFragment extends Fragment {

    HowToListAdapter adapter;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HowTo first = new HowTo(1, "Some Title", "Some Description");
        HowTo second = new HowTo(2, "Some Title", "Some Description");
        ArrayList<HowTo> items = new ArrayList<HowTo>();
        items.add(first);
        items.add(second);
        adapter = new HowToListAdapter(getActivity().getApplicationContext(), items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.howtos_fragment, container, false);
        list = (ListView) rootView.findViewById(R.id.howto_list_view);
        list.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
