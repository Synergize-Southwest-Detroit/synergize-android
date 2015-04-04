package com.example.ganemone.synergize;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.ganemone.synergize.event.EventsFragment;
import com.example.ganemone.synergize.howto.HowtosFragment;
import com.example.ganemone.synergize.resource.ResourcesFragment;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    SlidingTabLayout slidingTabLayout;
    private MPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(pager);
    }

    class MPagerAdapter extends FragmentPagerAdapter {

        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new EventsFragment();
                case 1: return new HowtosFragment();
                default: return new ResourcesFragment();
            }
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
