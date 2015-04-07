package com.example.ganemone.synergize.howto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ganemone.synergize.Closure;
import com.example.ganemone.synergize.R;
import com.example.ganemone.synergize.RefreshingFragment;
import com.example.ganemone.synergize.api.APIManager;

/**
 * Created by ganemone on 3/29/15.
 */
public class HowtosFragment extends Fragment implements ListView.OnItemClickListener, RefreshingFragment {

    public final static String HOWTO_EXTRA = "com.synergize.synergize.howto_extra";

    HowToListAdapter adapter;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HowToListAdapter(getActivity().getApplicationContext());
        APIManager manager = APIManager.getInstance();
        if (manager.howtos.size() == 0) {
            manager.loadHowtos(new Closure() {
                @Override
                public void onSuccess() {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to load howtos, " + message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.howtos_fragment, container, false);
        list = (ListView) rootView.findViewById(R.id.howto_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), HowToDetailActivity.class);
        HowTo item = adapter.getItem(position);
        intent.putExtra(HOWTO_EXTRA, item.id);
        startActivity(intent);
     }

    @Override
    public void refresh() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading How Tos");
        progress.show();
        APIManager.getInstance().reloadHowtos(new Closure() {
            @Override
            public void onSuccess() {
                progress.hide();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                progress.hide();
                Toast.makeText(getActivity().getApplicationContext(), "Failed to load events, " + message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
