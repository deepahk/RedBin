package com.redbin.application;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class binDetails extends Activity {
    List<Location> locationList = new ArrayList<Location>();
    String dummy_location="Kor1";
    String dummy_binid="bin1";
    Context ctx = this;

    TableLayout tl;
    TabHost th;
    TableRow tr;
    TextView locationDetails,binDetails,fillLevels;

    Button fillLevelButton;
    Button optimalRouteButton;
    Button analyticsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bindetails);

        Bundle bundle = getIntent().getExtras();
         dummy_location= bundle.getString("location");
         dummy_binid= bundle.getString("bin_number");

        tl = (TableLayout) findViewById(R.id.tl1);
        loadLocationDataFromDb();
       // fetchLocationData();
        addData();

    }

    public void loadLocationDataFromDb(){
        locationList = new ArrayList<Location>();
        DataFetch dataFetch = new DataFetch(ctx);
        //dataFetch.deleteAllLocation(dataFetch);
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "95", "20", "29", "2015-08-23");

        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "95", "30", "33", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "80", "05", "25", "2015-08-24");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "89", "15", "27", "2015-08-25");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "93", "20", "23", "2015-08-26");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "75", "27", "29", "2015-08-27");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "50", "17", "34", "2015-08-28");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "60", "34", "29", "2015-08-29");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "55", "10", "12", "2015-08-30");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "45", "2015-09-01");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "45", "2015-09-02");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "45", "2015-09-03");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "45", "2015-09-04");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "45", "2015-09-05");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "45", "2015-09-06");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "34", "27", "2015-10-27");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "34", "2015-10-26");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "22", "13", "2015-10-25");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "35", "32", "2015-10-24");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "25", "27", "2015-10-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "35", "22", "2015-10-22");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "20", "27", "2015-10-21");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "35", "32", "2015-10-20");

        dataFetch.insertLocationData(dataFetch, "Kor1", "bin4", "55", "20", "29", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor2", "bin1", "25", "20", "29", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor2", "bin2", "15", "20", "29", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin2", "90", "30", "29", "2015-08-23");
        fetchLocationData();
    }

    public void fetchLocationData(){
        DataFetch dataFetch = new DataFetch(ctx);
        SQLiteDatabase db = dataFetch.getReadableDatabase();


        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());

        // final Cursor cursor = db.rawQuery("SELECT * from " + MasterDataMapping.TABLE_NAME + ";",null);
        final Cursor cursor = db.rawQuery("SELECT * from "+ MasterDataMapping.TABLE_NAME+" where " + MasterDataMapping.BIN_NUMBER + " = '"+dummy_binid+ "' and " + MasterDataMapping.GEO_LOCATION+" = '" +dummy_location+ "' and " + MasterDataMapping.FILL_DATE + ">'"+ formattedDate + "' order by " + MasterDataMapping.FILL_DATE+ ";",null);
        // cursor.moveToFirst();
        Log.d("ERROR","SELECT * from "+ MasterDataMapping.TABLE_NAME+" where " + MasterDataMapping.BIN_NUMBER + " = '"+dummy_binid+ "' and " + MasterDataMapping.GEO_LOCATION+" = '" +dummy_location+ "' and " + MasterDataMapping.FILL_DATE + ">'"+ formattedDate + "';");

        if (cursor != null) {
            try {

                for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    Location loc = new Location();
                    loc.setUniqueId(1);
                    loc.setGeoLocation(cursor.getString(0));
                    loc.setBinNumber(cursor.getString(1));
                    loc.setFillLevel(Integer.parseInt(cursor.getString(2)));
                    loc.setHumidity(Integer.parseInt(cursor.getString(3)));
                    loc.setTemperature(Integer.parseInt(cursor.getString(4)));

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date fillDate = null;
                    try {
                        fillDate = formatter.parse(cursor.getString(5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("fillDate"+fillDate);
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());
                    loc.setFillDate(fillDate);
                    Log.d("fetch check", loc.getGeoLocation());
                    locationList.add(loc);
                }
            } finally {
                cursor.close();
            }
        }
    }


    public void addData(){

        for (Location i:locationList) {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView try_t = (TextView) findViewById(R.id.textView);
            try_t.setText(i.getGeoLocation() + " , ");
            try_t.append(i.getBinNumber());
            try_t.append("                  " + i.getFillLevel() + "%");
            try_t.setTextColor(Color.WHITE);


            try_t = (TextView) findViewById(R.id.textView1);
            try_t.setText(i.getTemperature().toString() + "C");
            try_t.setTextColor(Color.BLACK);
            // try_t.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.temperature,0,0,0);
            try_t = (TextView) findViewById(R.id.textView2);
            try_t.setText(i.getHumidity().toString() + "%");
            try_t.setTextColor(Color.BLACK);
        }



            GraphView graph = (GraphView) findViewById(R.id.graph);
            DataPoint[] data_bar = new DataPoint[locationList.size()];
            DataPoint[] data_line = new DataPoint[locationList.size()];

        int counter=0;
        String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","NOV","DEC"};
        String [] date_label= new String[locationList.size()];
        Calendar cal = Calendar.getInstance();
        for (Location i:locationList) {
            data_bar[counter]=new DataPoint(counter,i.getHumidity());
            data_line[counter]=new DataPoint(counter,i.getTemperature());
            cal.setTime(i.getFillDate());
            int dummy_date=cal.get(Calendar.DATE);
            int dummy_month=cal.get(Calendar.MONTH);
            if (counter%2 ==0)
            {
            date_label[counter++]=month[dummy_month]+ "  " +Integer.toString(dummy_date);
            }
            else
            {
                date_label[counter++]="  ";
            }


        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(data_bar);

        double xInterval=1.0;
        graph.getViewport().setXAxisBoundsManual(true);
        if (series instanceof BarGraphSeries ) {
            // Shunt the viewport, per v3.1.3 to show the full width of the first and last bars. 
           graph.getViewport().setMinX(series.getLowestValueX() - (xInterval/2.0));
           graph.getViewport().setMaxX(series.getHighestValueX() + (xInterval/2.0));
        } else {
           graph.getViewport().setMinX(series.getLowestValueX() );
           graph.getViewport().setMaxX(series.getHighestValueX());
        }


        // use static labels for horizontal and vertical labels
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(date_label);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setTextSize(5);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLUE);


        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ctx, " Humidity: " + dataPoint.getY(), Toast.LENGTH_SHORT).show();

            }
        });



        series.setSpacing(50);
        series.setColor(Color.rgb(165, 42, 42));
        graph.addSeries(series);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(data_line);
        series2.setDataPointsRadius(25);

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series2, DataPointInterface dataPoint) {
                Toast.makeText(ctx, " Temp: " + dataPoint.getY(), Toast.LENGTH_SHORT).show();

            }
        });

        series2.setDrawDataPoints(true);
        series2.setThickness(15);
        series2.setTitle("Temp humidity Vs date");


        graph.addSeries(series2);

            // Add the TableRow to the TableLayout
       //     tl.addView(tr, new TableLayout.LayoutParams(
         //           TableRow.LayoutParams.FILL_PARENT,
           //         TableRow.LayoutParams.WRAP_CONTENT));
        }
    }



