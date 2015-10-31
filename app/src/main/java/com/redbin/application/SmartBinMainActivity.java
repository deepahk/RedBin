package com.redbin.application;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmartBinMainActivity extends ActionBarActivity implements View.OnKeyListener{
    List<Location> locationList = new ArrayList<>();
    Context ctx = this;
    Location selectedLocation = null;

    TableLayout tl;
    TableRow tr;
    TextView locationDetails,binDetails,fillLevels;
    EditText searchText = null;
    String searchString = "";


     private RadioGroup selectedRadioGrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_bin_activity_main);
        tl = (TableLayout) findViewById(R.id.table1);
        searchText = (EditText)findViewById(R.id.search_bar);
        searchText.setOnKeyListener(this);
        //searchText.addTextChangedListener(onSearchFieldTextChanged);
        loadLocationDataFromDb();
        addHeaders();
        addData();

    }

    @Override
    public boolean onKey(View view,int keyCode,KeyEvent event){
        EditText serchtxt = (EditText)view;
        if(keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            if(!event.isShiftPressed()){
                if(view.getId() == R.id.search_bar){
                    System.out.println("here ... ");
                    locationList = new ArrayList<>();
                    //loadLocationDataFromDb();
                    addData();
                    return true;
                }
            }
        }
        return false;
    }



    /** This function add the headers to the table **/
    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView locationDetails = new TextView(this);
        locationDetails.setText("Location");
        locationDetails.setTextColor(Color.BLACK);
        locationDetails.setBackgroundColor(Color.GRAY);
        locationDetails.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        locationDetails.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        locationDetails.setPadding(5, 5, 5, 5);
        locationDetails.setHeight(100);
        tr.addView(locationDetails);  // Adding textView to tablerow.

        /** Creating a TextView to add to the row **/
        TextView binDetails = new TextView(this);
        binDetails.setText("Bin");
        binDetails.setTextColor(Color.BLACK);
        binDetails.setBackgroundColor(Color.GRAY);
        binDetails.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        binDetails.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        binDetails.setPadding(5, 5, 5, 5);
        binDetails.setHeight(100);
        tr.addView(binDetails);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView fillLevels = new TextView(this);
        fillLevels.setText("Fill Level");
        fillLevels.setTextColor(Color.BLACK);
        fillLevels.setBackgroundColor(Color.GRAY);
        fillLevels.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        fillLevels.setPadding(5, 5, 5, 5);
        fillLevels.setHeight(100);
        fillLevels.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(fillLevels); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    /** This function add the data to the table **/
    public void addData(){

        for (Location i:locationList)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            locationDetails = new TextView(this);
            locationDetails.setText(i.getGeoLocation());
            locationDetails.setTextColor(Color.BLACK);
            locationDetails.setHeight(100);
            if(i.getFillLevel().intValue() > 71 && i.getFillLevel().intValue() < 100) {
                locationDetails.setBackgroundColor(Color.RED);

            }
            else
            if(i.getFillLevel().intValue() > 51 && i.getFillLevel().intValue() < 70)
            {
                locationDetails.setBackgroundColor(Color.YELLOW);
            }
            else
            {
                locationDetails.setBackgroundColor(Color.GREEN);
            }
            locationDetails.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            locationDetails.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            locationDetails.setPadding(5, 5, 5, 5);
            tr.setClickable(true);
            //final Location loc = i;
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tr.setBackgroundColor(Color.BLUE);
                    tr.setDrawingCacheBackgroundColor(Color.BLUE);
                    //  selectedLocation = loc;
                    //System.out.println("view " + loc.getLocation());
                    TableRow tablerow = (TableRow) view;
                    TextView bin = (TextView) tablerow.getChildAt(1);
                    TextView loc = (TextView) tablerow.getChildAt(0);
                    TextView fillLevel = (TextView) tablerow.getChildAt(2);
                    String BinDetail = bin.getText().toString();
                    String LocDetail = loc.getText().toString();
                    String FillLevel = fillLevel.getText().toString();
                    selectedLocation = new Location();
                    //selectedLocation.setFillLevel(FillLevel);
                    selectedLocation.setLocation(LocDetail);
                    selectedLocation.setBinNumber(BinDetail);
                    Toast.makeText(SmartBinMainActivity.this,
                            "Bin Details : " +selectedLocation.getBinNumber().toString()+ " ,  " + selectedLocation.getLocation().toString(), Toast.LENGTH_SHORT).show();
                    Bundle dataBundle = new Bundle();
                    System.out.println(selectedLocation.getBinNumber().toString());
                    dataBundle.putString("location", "" + selectedLocation.getLocation().toString());
                    dataBundle.putString("bin_number", "" + selectedLocation.getBinNumber().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);

                   // System.out.println("Bin Number " +selectedLocation.getBinNumber().toString());
                   // System.out.println("Location  " +selectedLocation.getLocation().toString());
                    //System.out.println("Unique location " +selectedLocation.getUniqueId());
                }
            });

            tr.addView(locationDetails);  // Adding textView to tablerow.

            /** Creating a TextView to add to the row **/
            binDetails = new TextView(this);
            binDetails.setText(i.getBinNumber());
            binDetails.setTextColor(Color.BLACK);
            binDetails.setHeight(100);
            if(i.getFillLevel().intValue() > 71 && i.getFillLevel().intValue() < 100){
                binDetails.setBackgroundColor(Color.RED);
            }
            else
            if(i.getFillLevel().intValue() > 51 && i.getFillLevel().intValue() < 70)
            {
                binDetails.setBackgroundColor(Color.YELLOW);
            }
            else
            {
                binDetails.setBackgroundColor(Color.GREEN);
            }
            binDetails.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            binDetails.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            binDetails.setPadding(5, 5, 5, 5);
            tr.addView(binDetails);  // Adding textView to tablerow.

            /** Creating another textview **/
            fillLevels = new TextView(this);
            fillLevels.setText(i.getFillLevel().toString() + "%");
            fillLevels.setTextColor(Color.BLACK);
            fillLevels.setHeight(100);
            if(i.getFillLevel().intValue() > 71 && i.getFillLevel().intValue() < 100){
                fillLevels.setBackgroundColor(Color.RED);
            }
            else
            if(i.getFillLevel().intValue() > 51 && i.getFillLevel().intValue() < 70)
            {
                fillLevels.setBackgroundColor(Color.YELLOW);
            }
            else
            {
                fillLevels.setBackgroundColor(Color.GREEN);
            }
            fillLevels.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            fillLevels.setPadding(5, 5, 5, 5);
            fillLevels.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(fillLevels); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    public void loadLocationDataFromDb(){
        locationList = new ArrayList<>();
        DataFetch dataFetch = new DataFetch(ctx);
        dataFetch.deleteAllLocation(dataFetch);
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "95", "20", "29", "2015-10-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "95", "30", "33", "2015-10-24");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "63", "20", "23", "2015-10-26");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "75", "27", "29", "2015-10-27");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin4", "55", "20", "29", "2015-10-23");
        dataFetch.insertLocationData(dataFetch, "Kor2", "bin1", "25", "20", "29", "2015-10-23");
        dataFetch.insertLocationData(dataFetch, "Kor2", "bin2", "15", "20", "29", "2015-10-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin2", "20", "30", "29", "2015-10-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "20", "27", "2015-10-21");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "35", "32", "2015-10-20");

        fetchLocationData();
    }


    public void fetchLocationData() {
        DataFetch dataFetch = new DataFetch(ctx);
        SQLiteDatabase db = dataFetch.getReadableDatabase();
        final Cursor cursor =  db.rawQuery("SELECT * from " + MasterDataMapping.TABLE_NAME +" where "+MasterDataMapping.GEO_LOCATION+" like '%"+searchString+"%' order by "+ MasterDataMapping.FILL_DATE +";", null);
        if (cursor != null) {
            try {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    Location loc = new Location();
                    loc.setUniqueId(1);
                    loc.setGeoLocation(cursor.getString(0));
                    loc.setBinNumber(cursor.getString(1));
                    loc.setFillLevel(Integer.parseInt(cursor.getString(2)));
                    loc.setHumidity(Integer.parseInt(cursor.getString(3)));
                    loc.setTemperature(Integer.parseInt(cursor.getString(4)));
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
                    Date fillDate = null;
                    try {
                        fillDate = formatter.parse(cursor.getString(5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("fillDate" + fillDate);
                    loc.setFillDate(fillDate);
                    locationList.add(loc);
                }
            } finally {
                cursor.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
        super.setSupportProgressBarIndeterminateVisibility(visible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),AnalyseSearchActivity.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            case R.id.analyse:dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                 intent = new Intent(getApplicationContext(),AnalyseGraph.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Location getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Location selectedLocation) {
        this.selectedLocation = selectedLocation;
    }
}
