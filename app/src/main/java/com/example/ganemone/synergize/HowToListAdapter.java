package com.example.ganemone.synergize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganemone on 3/30/15.
 */
public class HowToListAdapter extends ArrayAdapter<HowTo> {

    public HowToListAdapter(Context applicationContext) {
        super(applicationContext, R.layout.howto_list_item, APIManager.getInstance().howtos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.howto_list_item, parent, false);
        }

        HowTo item = getItem(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.description);

        titleTextView.setText(item.title);
        descriptionTextView.setText(item.description);

        return convertView;
    }

    public void syncWithAPIManager() {
        ArrayList<HowTo> howtos = APIManager.getInstance().howtos;
        this.clear();
        this.addAll(howtos);
        this.notifyDataSetChanged();
    }
}
