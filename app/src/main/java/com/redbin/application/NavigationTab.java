package com.redbin.application;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by apreethi on 10/31/15.
 */
public class NavigationTab extends TabActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tab);

        TabHost tabHost = getTabHost();

        // Tab for Photos

        TabHost.TabSpec fillspec = tabHost.newTabSpec("Fill Level");
        // setting Title and Icon for the Tab
        fillspec.setIndicator("Fill Level");
        Intent fillIntent = new Intent(this,SmartBinMainActivity.class);
        fillspec.setContent(fillIntent);

        // Tab for Songs
        TabHost.TabSpec geospec = tabHost.newTabSpec("Geo Location");
        geospec.setIndicator("Geo Loc");
        Intent geoIntent = new Intent(this, MapsActivity.class);
        geospec.setContent(geoIntent);

        // Tab for Songs
        TabHost.TabSpec analyticspec = tabHost.newTabSpec("Analysis");
        analyticspec.setIndicator("Analysis");
        Intent analyticIntent = new Intent(this, AnalyseSearchActivity.class);
        analyticspec.setContent(analyticIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(fillspec); // Adding photos tab
        tabHost.addTab(geospec); // Adding songs tab
        tabHost.addTab(analyticspec);

    }
}