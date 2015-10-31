package com.redbin.application;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by NavyaAnand on 02-10-2015.
 */
public class AnalyseGraph extends ActionBarActivity implements View.OnClickListener {

    private View mChart;
    TableLayout selectedBinTableLayout;
    TableRow tr;
    Context ctx = this;
    TextView locationDetails,binDetails,fillLevels;
    TextView dateRange ;
    TextView selectedQuery ;
    String fromDate,toDate;

    List<Location> locationList = new ArrayList<>();
    private String[] week = new String[] {
            "Mon", "Tue" , "Wed", "Thu", "Fri", "Sat",
            "Sun"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        List<Location> selectionLocationList = new ArrayList<Location>();
        List<Location> locationListDataFetch = new ArrayList<Location>();
        DataFetch dataFetch = new DataFetch(ctx);
        locationListDataFetch=dataFetch.fetchLocationData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyse_graph);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> selectedList = bundle.getStringArrayList("selectedLocationList");
        fromDate=bundle.getString("fromDate");
        toDate=bundle.getString("toDate");
        System.out.println(fromDate);
        System.out.println(toDate);
        for(String locationSting:selectedList){
            System.out.println(locationSting);
            String[] array=locationSting.split(",");
            System.out.println(array[0] +array[1]);
            for(Location location:locationListDataFetch){
                if(array[0].equals(location.getGeoLocation())&&array[1].equals(location.getBinNumber())){
                    selectionLocationList.add(location);
                }
            }
        }


        locationList=selectionLocationList;
        selectedBinTableLayout = (TableLayout) findViewById(R.id.selectedBins);
        selectedBins();
        openChart();

    }

    private void openChart(){
        int[] x = { 0,1,2,3,4,5,6};


        int[] binFillTrend1 = { 15,25,35,35,35,25,15};
        int[] binFillTrend2 = {10, 20, 30, 30 ,30,20,10};
        String locationName1="";
        String locationName2="";

        for(int count=0;count<locationList.size();count++){
            Location l=locationList.get(count);
            Calendar cal = Calendar.getInstance();
            cal.setTime(l.getFillDate());
            int dateOfWeek=cal.get(Calendar.DAY_OF_WEEK);
            if(count==0){
                binFillTrend1[dateOfWeek-1]=l.getFillLevel();
                locationName1=l.getGeoLocation()+""+l.getBinNumber();
            }else if(count==1){
                binFillTrend2[dateOfWeek-1]=l.getFillLevel();
                locationName2=l.getGeoLocation()+""+l.getBinNumber();
            }

        }
        dateRange = (TextView) findViewById(R.id.dateRange);
        System.out.println(dateRange.getText());
        selectedQuery = (TextView) findViewById(R.id.querySelected);
        // Creating an XYSeries for Income
        XYSeries binFillTrendSeries1  = new XYSeries(locationName1);
        // Creating an XYSeries for Expense
        XYSeries binFillTrendSeries2  = new XYSeries(locationName2);
        // Adding data to Income and Expense Series
        for (int i = 0; i < x.length; i++) {
            binFillTrendSeries1.add(i, binFillTrend1[i]);
            binFillTrendSeries2.add(i, binFillTrend2[i]);
        }
        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(binFillTrendSeries1);
        // Adding Expense Series to dataset
        dataset.addSeries(binFillTrendSeries2);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer bin1Renderer = new XYSeriesRenderer();
        bin1Renderer.setFillBelowLineColor(Color.RED);
        bin1Renderer.setColor(Color.BLUE); // color of the graph set to cyan
        bin1Renderer.setFillPoints(true);
        bin1Renderer.setLineWidth(2f);
        bin1Renderer.setDisplayChartValues(true);
        // setting chart value distance
        bin1Renderer.setDisplayChartValuesDistance(10);
        // setting line graph point style to circle
        bin1Renderer.setPointStyle(PointStyle.POINT);
        // setting stroke of the line chart to solid
        bin1Renderer.setStroke(BasicStroke.SOLID);
        // for filling area
        bin1Renderer.setFillBelowLine(true);
        // incomeRenderer.setFillBelowLineColor(Color.YELLOW);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer bin2Renderer = new XYSeriesRenderer();
        bin2Renderer.setGradientStart(0,Color.RED);
        bin2Renderer.setColor(Color.BLUE);
        bin2Renderer.setFillPoints(true);
        bin2Renderer.setLineWidth(2f);
        bin2Renderer.setDisplayChartValues(true);
        // setting line graph point style to circle
        bin2Renderer.setPointStyle(PointStyle.POINT);
        // setting stroke of the line chart to solid
        bin2Renderer.setStroke(BasicStroke.SOLID);
        // for filling area
        bin2Renderer.setFillBelowLine(true);
        // expenseRenderer.setFillBelowLineColor(Color.RED);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        Bundle bundle = getIntent().getExtras();
        String query= bundle.getString("query");
        String range= bundle.getString("dateRange");
        multiRenderer.setChartTitle("");
        dateRange.setText("" + range);
        selectedQuery.setText(query);
        multiRenderer.setXTitle("");
        multiRenderer.setYTitle("");

        /***
         * Customizing graphs
         */
        // setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
        // setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
        // setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
        // setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
        // setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
        // setting click false on graph
        multiRenderer.setClickEnabled(false);
        // setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
        // setting lines to display on y axis
        multiRenderer.setShowGridY(true);
        // setting lines to display on x axis
        multiRenderer.setShowGridX(true);
        // setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
        // setting displaying line on grid
        multiRenderer.setShowGrid(true);
        // setting zoom to false
        multiRenderer.setZoomEnabled(false);
        // setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
        // setting displaying lines on graph to be formatted(like using
        // graphics)
        multiRenderer.setAntialiasing(true);
        // setting to in scroll to false
        multiRenderer.setInScroll(false);
        // setting to set legend height of the graph
        multiRenderer.setLegendHeight(10);
        // setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
        // setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.RIGHT);
        // setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        // setting no of values to display in y axis
        //multiRenderer.setYLabels(1);
        multiRenderer.setYAxisMin(0);
        // setting y axis max value, Since i'm using static values inside the
        // graph so i'm setting y max value to 4000.
        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(100);
        // setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(0);
        // setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(6);
        // setting bar size or space between two bars
        // multiRenderer.setBarSpacing(0.5);
        // Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        // Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(
                R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setScale(2f);
        // setting x axis point size
        multiRenderer.setPointSize(4f);
        // setting the margin size for the graph in the order top, left, bottom,
        // right
        multiRenderer.setMargins(new int[] { 50, 50, 50, 50 });

        for (int i = 0; i < x.length; i++) {
            multiRenderer.addXTextLabel(i, week[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to
        // multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(bin1Renderer);
        multiRenderer.addSeriesRenderer(bin2Renderer);

        // this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        // remove any views before u paint the chart
        chartContainer.removeAllViews();
        // drawing bar chart
        mChart = ChartFactory.getLineChartView(AnalyseGraph.this, dataset,
                multiRenderer);
        // adding the view to the linearlayout
        chartContainer.addView(mChart);

    }
    public void selectedBins(){

        for (Location i:locationList)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            locationDetails = new TextView(this);
            locationDetails.setText(i.getGeoLocation());

            locationDetails.setTextColor(Color.BLACK);
            if(i.getFillLevel().intValue() > 71 && i.getFillLevel().intValue() < 100){
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
            locationDetails.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            locationDetails.setPadding(5, 5, 5, 5);
            tr.addView(locationDetails);  // Adding textView to tablerow.

            /** Creating a TextView to add to the row **/
            binDetails = new TextView(this);
            binDetails.setText(i.getBinNumber());
            binDetails.setTextColor(Color.BLACK);
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
            binDetails.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            binDetails.setPadding(5, 5, 5, 5);
            tr.addView(binDetails);  // Adding textView to tablerow.

            /** Creating another textview **/
            fillLevels = new TextView(this);
            fillLevels.setText(i.getFillLevel().toString() + "%");
            fillLevels.setTextColor(Color.BLACK);
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
            fillLevels.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            fillLevels.setPadding(5, 5, 5, 5);
            fillLevels.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(fillLevels); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            selectedBinTableLayout.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.analyse:;
                Bundle dataBundle = new Bundle();
                Intent intent = new Intent(getApplicationContext(),AnalyseSearchActivity.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View view) {

    }
}


