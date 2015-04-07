package com.example.ganemone.synergize;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ganemone.synergize.event.EventsFragment;
import com.example.ganemone.synergize.howto.HowtosFragment;
import com.example.ganemone.synergize.resource.ResourcesFragment;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    SlidingTabLayout slidingTabLayout;
    private MPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageManager.configure(getApplicationContext());
        setContentView(R.layout.activity_main);
        adapter = new MPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            for (int i = 0; i < 3; i++) {
                Fragment fragment = adapter.getItem(i);
                if (fragment.isVisible() && false) {
                    ((RefreshingFragment)fragment).refresh();
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    class MPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        public MPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new EventsFragment());
            fragments.add(new HowtosFragment());
            fragments.add(new ResourcesFragment());
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Events";
                case 1: return "How-Tos";
                default: return "Resources";
            }
        }
    }
}