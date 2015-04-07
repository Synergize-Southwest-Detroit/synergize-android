package com.example.ganemone.synergize.resource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganemone.synergize.Closure;
import com.example.ganemone.synergize.R;
import com.example.ganemone.synergize.api.APIManager;

/**
 * Created by ganemone on 4/3/15.
 */
public class ResourceListAdapter extends ArrayAdapter<Resource> {

    private Closure closure = new Closure() {
        @Override
        public void onSuccess() {
            ResourceListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onFailure(String message) {
            Toast.makeText(getContext(), "Failed to load more resources, " + message, Toast.LENGTH_LONG).show();
        }
    };

    public ResourceListAdapter(Context context) {
        super(context, R.layout.resource_list_item, APIManager.getInstance().resources);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.resource_list_item, parent, false);
        }

        Resource resource = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(resource.title);

        if (position == getCount() - 1 && APIManager.getInstance().hasNextResourcePage()) {
            APIManager.getInstance().loadResources(closure);
        }

        return convertView;
    }
}
