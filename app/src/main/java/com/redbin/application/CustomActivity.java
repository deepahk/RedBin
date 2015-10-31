package com.redbin.application;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class CustomActivity extends Activity implements OnClickListener {

    //UI References
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    Context ctx = this;
    TableLayout tl;
    TabHost th;
    TableRow tr;
    int iteration=7;
    Button button;
    TextView locationDetails,binDetails,fillLevels;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    List<Location> locationList = new ArrayList<Location>();
    String dummy_location="Kor1";
    String dummy_binid="bin1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout);
        findViewsById();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();
        addListenerOnButton();
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);


    }
    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                loadLocationDataFromDb();
             // fetchLocationData();
                tl = (TableLayout) findViewById(R.id.tl1);
                addData();

            }

        });

    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));



            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            Log.d("ERROR","In Loop onclick");
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                d = sdf.parse("23/08/2015");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DatePicker validate = fromDatePickerDialog.getDatePicker();
            validate.setMinDate(d.getTime());
            Calendar newDate = Calendar.getInstance();
            validate.setMaxDate(newDate.getTimeInMillis());
            fromDatePickerDialog.show();

        } else if(view == toDateEtxt) {

            DatePicker validate = toDatePickerDialog.getDatePicker();
            Calendar today = Calendar.getInstance();

            Calendar newDate = Calendar.getInstance();
            newDate.set(fromDatePickerDialog.getDatePicker().getYear(), fromDatePickerDialog.getDatePicker().getMonth(), fromDatePickerDialog.getDatePicker().getDayOfMonth());

            long diff_date=TimeUnit.MILLISECONDS.toDays(Math.abs(newDate.getTimeInMillis() - today.getTimeInMillis()));
            if (diff_date > 0 ) {
                validate.setMinDate(newDate.getTimeInMillis());
                validate.setMaxDate(today.getTimeInMillis());
                toDatePickerDialog.show();
            }
            else {
            }

        }
    }

    public void loadLocationDataFromDb(){
        locationList = new ArrayList<Location>();
        DataFetch dataFetch = new DataFetch(ctx);
        dataFetch.deleteAllLocation(dataFetch);
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "95", "20", "29", "2015-08-22");

        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "95", "30", "33", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "80", "25", "25", "2015-08-24");
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
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin1", "85", "15", "45", "2015-09-07");

        dataFetch.insertLocationData(dataFetch, "Kor1", "bin4", "55", "20", "29", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor2", "bin1", "25", "20", "29", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor2", "bin2", "15", "20", "29", "2015-08-23");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin2", "90", "30", "29", "2015-08-23");
        fetchLocationData();
    }

    public void fetchLocationData(){
        DataFetch dataFetch = new DataFetch(ctx);
        SQLiteDatabase db = dataFetch.getReadableDatabase();
        // final Cursor cursor = db.rawQuery("SELECT * from " + MasterDataMapping.TABLE_NAME + ";",null);


        // format date

        int year = fromDatePickerDialog.getDatePicker().getYear();
        int month = fromDatePickerDialog.getDatePicker().getMonth();
        int day = fromDatePickerDialog.getDatePicker().getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String from_date = format.format(calendar.getTime());

         year = toDatePickerDialog.getDatePicker().getYear();
         month = toDatePickerDialog.getDatePicker().getMonth();
        day = toDatePickerDialog.getDatePicker().getDayOfMonth();

         calendar = Calendar.getInstance();
        calendar.set(year, month, day);

         format = new SimpleDateFormat("yyyy-MM-dd");
        String to_date = format.format(calendar.getTime());


       // String from_date=fromDatePickerDialog.getDatePicker().getYear() +"-" + (fromDatePickerDialog.getDatePicker().getMonth() +1) + "-" + fromDatePickerDialog.getDatePicker().getDayOfMonth();
        //String to_date =toDatePickerDialog.getDatePicker().getYear() +"-" + ( toDatePickerDialog.getDatePicker().getMonth() + 1) + "-" + toDatePickerDialog.getDatePicker().getDayOfMonth();


        final Cursor cursor = db.rawQuery("SELECT * from "+ MasterDataMapping.TABLE_NAME+" where " + MasterDataMapping.BIN_NUMBER + " = '"+dummy_binid+ "' and " + MasterDataMapping.GEO_LOCATION+" = '" +dummy_location+ "' and " + MasterDataMapping.FILL_DATE + ">'"+ from_date +"' and " + MasterDataMapping.FILL_DATE + "<'"+ to_date + "' order by " + MasterDataMapping.FILL_DATE+ ";",null);
        //final Cursor cursor = db.rawQuery("SELECT * from "+ MasterDataMapping.TABLE_NAME+" where " + MasterDataMapping.BIN_NUMBER + " = '"+dummy_binid+ "' and " + MasterDataMapping.GEO_LOCATION+" = '" +dummy_location+ "';",null);
        // cursor.moveToFirst();
        Log.d("ERROR","SELECT * from "+ MasterDataMapping.TABLE_NAME+" where " + MasterDataMapping.BIN_NUMBER + " = '"+dummy_binid+ "' and " + MasterDataMapping.GEO_LOCATION+" = '" +dummy_location+ "' and " + MasterDataMapping.FILL_DATE + ">'"+ from_date +"' and " + MasterDataMapping.FILL_DATE + "<'"+ to_date + "' order by " + MasterDataMapping.FILL_DATE+ ";");

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



    public void addData() {

        for (Location i : locationList) {
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


        GraphView graph = (GraphView) findViewById(R.id.graph2);
       // DataPoint[] data_bar = new DataPoint[locationList.size()];
       // DataPoint[] data_line = new DataPoint[locationList.size()];

        int counter = 0;
        String[] month = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "NOV", "DEC"};
        String[] date_label = new String[iteration];
        Calendar cal = Calendar.getInstance();

        double bound = Math.ceil(locationList.size() / iteration);
        DataPoint[] data_bar = new DataPoint[iteration];
        DataPoint[] data_line = new DataPoint[iteration];
        Log.d("bound", Double.toString(bound));
        double delta = 0;
        double sum_humidity = 0, average_bar = 0, sum_temp = 0, average_line = 0;

        for (int j = 0; j <iteration ; j++) {
            for (double cnt = delta; cnt < (bound + delta); cnt++) {
                sum_humidity += locationList.get((int) cnt).getHumidity();
                sum_temp += locationList.get((int) cnt).getTemperature();
            }
            average_bar = Math.ceil(sum_humidity / bound);
            average_line = Math.ceil(sum_temp / bound);

            Log.d("delta", Double.toString(delta));
            data_bar[counter] = new DataPoint(counter, average_bar);
            Log.d("bar", Double.toString(average_bar));
            data_line[counter] = new DataPoint(counter, average_line);
            Log.d("line", Double.toString(average_line));

            Log.d("uppper", Double.toString(bound + delta));
            sum_humidity = 0;
            sum_temp = 0;
            cal.setTime(locationList.get((int) (delta)).getFillDate());
            int dummy_date = cal.get(Calendar.DATE);
            int dummy_month = cal.get(Calendar.MONTH);
            if (counter % 2 == 0) {
                date_label[counter++] = month[dummy_month] + "  " + Integer.toString(dummy_date);

            } else {
                date_label[counter++] = "  ";
                Log.d("ELSE",date_label[counter-1].toString());
            }

            delta = delta + bound;
        }


        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(data_bar);

        double xInterval=1.0;
        graph.getViewport().setXAxisBoundsManual(true);
      //  if (series instanceof BarGraphSeries ) {
            // Shunt the viewport, per v3.1.3 to show the full width of the first and last bars. 
            graph.getViewport().setMinX(series.getLowestValueX() - (xInterval/2.0));
            graph.getViewport().setMaxX(series.getHighestValueX() + (xInterval/2.0));
       // } else {
            graph.getViewport().setMinX(series.getLowestValueX() );
            graph.getViewport().setMaxX(series.getHighestValueX());
       // }

        // use static labels for horizontal and vertical labels
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(date_label);


        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setTextSize(5);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLUE);


        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ctx, " Humidity: "+dataPoint.getY(), Toast.LENGTH_SHORT).show();

            }
        });

        series.setSpacing(50);
        series.setColor(Color.rgb(165, 42, 42));
        graph.addSeries(series);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(data_line);
        series2.setDataPointsRadius(20);
        series2.setDrawDataPoints(true);
        series2.setTitle("Temp/humidity Vs date");

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series2, DataPointInterface dataPoint) {
                Toast.makeText(ctx, " Temp: " + dataPoint.getY(), Toast.LENGTH_SHORT).show();

            }
        });


        series2.setThickness(15);
        graph.addSeries(series2);

        // Add the TableRow to the TableLayout
        //     tl.addView(tr, new TableLayout.LayoutParams(
        //           TableRow.LayoutParams.FILL_PARENT,
        //         TableRow.LayoutParams.WRAP_CONTENT));
    }



}
