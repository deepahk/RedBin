package com.redbin.application;

import android.app.ActionBar;
import android.app.Notification;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

        /**
         * Called when the activity is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            TabHost tabHost = getTabHost();
            Bundle bundle = getIntent().getExtras();
            String location = bundle.getString("location");
            String bin_number = bundle.getString("bin_number");

            // Tab for Photos

            Bundle dataBundle = new Bundle();
            System.out.println(location);
            dataBundle.putString("location", "" + location);
            dataBundle.putString("bin_number", "" + bin_number);


            TabSpec weekspec = tabHost.newTabSpec("Week");
            // setting Title and Icon for the Tab
            weekspec.setIndicator("Week");
            Intent weekIntent = new Intent(this, binDetails.class);
            weekIntent.putExtras(dataBundle);
            weekspec.setContent(weekIntent);

            // Tab for Songs
            TabSpec customspec = tabHost.newTabSpec("Custom");
            customspec.setIndicator("Custom");
            Intent customIntent = new Intent(this, CustomActivity.class);
            customIntent.putExtras(dataBundle);
            customspec.setContent(customIntent);

            // Adding all TabSpec to TabHost
            tabHost.addTab(weekspec); // Adding photos tab
            tabHost.addTab(customspec); // Adding songs tab

        }
    }
