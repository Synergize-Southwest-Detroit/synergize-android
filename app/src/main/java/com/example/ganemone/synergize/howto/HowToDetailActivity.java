package com.example.ganemone.synergize.howto;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganemone.synergize.R;
import com.example.ganemone.synergize.api.APIManager;
import com.example.ganemone.synergize.resource.ResourceListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class HowToDetailActivity extends ActionBarActivity {

    public HowTo howto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_detail);

        Intent intent = getIntent();
        int hid = intent.getIntExtra(HowtosFragment.HOWTO_EXTRA, -1);
        if (hid == -1) {
            Toast.makeText(getApplicationContext(), "Error getting howto extra", Toast.LENGTH_LONG).show();
            return;
        }
        howto = APIManager.getInstance().getHowToByID(hid);
        if (howto == null) {
            Toast.makeText(getApplicationContext(), "Error: Could not find selected howto", Toast.LENGTH_LONG).show();
            return;
        }
        if (howto.title != null) {
            this.setTitle(howto.title);
        }

        TextView body = (TextView) findViewById(R.id.howto_body);
        if (howto.description != null) {
            body.setText(howto.description);
        }

        LayoutInflater inflater = getLayoutInflater();
        ArrayList<View> stepViews = new ArrayList<View>();
        ViewGroup root = (ViewGroup) findViewById(R.id.how_to_detail_root);
        for (int i = 0; i < howto.steps.size(); i++) {

            Step step = howto.steps.get(i);
            View stepViewItem = inflater.inflate(R.layout.step_list_item, root, false);
            step.setUpWithViews(
                    (TextView) stepViewItem.findViewById(R.id.step_title),
                    (TextView) stepViewItem.findViewById(R.id.step_body),
                    (ImageView) stepViewItem.findViewById(R.id.step_image)
            );
            ((TextView) stepViewItem.findViewById(R.id.step_heading)).setText("Step " + (i + 1) + ":");
            stepViews.add(stepViewItem);
            root.addView(stepViewItem, i+1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_how_to_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
