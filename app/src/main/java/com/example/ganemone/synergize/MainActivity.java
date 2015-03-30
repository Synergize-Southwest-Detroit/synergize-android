package com.example.ganemone.synergize;

import android.os.Bundle;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class MainActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {
        addSection(newSection("Events", new EventsFragment()));
        addSection(newSection("Resources", new ResourcesFragment()));
        addSection(newSection("How-Tos", new HowtosFragment()));
    }
}
