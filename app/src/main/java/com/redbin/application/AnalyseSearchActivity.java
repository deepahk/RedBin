package com.redbin.application;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by NavyaAnand on 02-10-2015.
 */
public class AnalyseSearchActivity extends ActionBarActivity implements View.OnClickListener {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    Context ctx = this;
    private Button btnDisplay;
    List<Location> locationList = new ArrayList<>();
    ArrayList<String> selectedLocationList = new ArrayList<String>();
    MyCustomAdapter dataAdapter = null;
    private RadioGroup selectedRadioGrp;
    private RadioButton selectedQuery;
    EditText dateFrom ;
    EditText dateTo;
    Calendar myCalendar = Calendar.getInstance();
    private void displayListView()
    {

        //Array list of countries
        List<Location> locationList = new ArrayList<Location>();
        DataFetch dataFetch = new DataFetch(ctx);
        loadLocationDataFromDb();
        locationList=dataFetch.fetchLocationData();
        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.bininfo, (ArrayList<Location>) locationList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Location location = (Location) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + location.getBinNumber(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<Location> loadLocationDataFromDb(){
        locationList = new ArrayList<>();
        DataFetch dataFetch = new DataFetch(ctx);
        dataFetch.deleteAllLocation(dataFetch);

        dataFetch.insertLocationData(dataFetch,"Kor1","bin1","95","20","29","2015-08-23");
        dataFetch.insertLocationData(dataFetch,"Kor1","bin2","90","20","29","2015-08-24");
        dataFetch.insertLocationData(dataFetch,"Kor1","bin3","65","20","29","2015-08-25");
        dataFetch.insertLocationData(dataFetch, "Kor1", "bin4", "55", "20", "29", "2015-08-26");
        dataFetch.insertLocationData(dataFetch, "Kor2", "bin1", "25", "20", "29", "2015-08-27");
        return locationList;
    }

    private class MyCustomAdapter extends ArrayAdapter<Location> {

        private ArrayList<Location> locationList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Location> locationList) {
            super(context, textViewResourceId, locationList);
            this.locationList = new ArrayList<Location>();
            this.locationList.addAll(locationList);
        }

        private class ViewHolder {
            CheckBox name;
            TextView location;
            TextView bin;
            TextView fillLevel;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.bininfo, null);

                holder = new ViewHolder();
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.location = (TextView) convertView.findViewById(R.id.location);
                holder.bin = (TextView) convertView.findViewById(R.id.bin);
                holder.fillLevel = (TextView) convertView.findViewById(R.id.fillLevel);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Location location = (Location) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        location.setSelected(cb.isChecked());
                        if(cb.isChecked()){
                            selectedLocationList.add(location.getGeoLocation()+","+location.getBinNumber());
                        }
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Location location  = locationList.get(position);
            if(location.getFillLevel().intValue() > 71 && location.getFillLevel().intValue() < 100) {
                holder.location.setText(location.getGeoLocation());
                convertView.setBackgroundColor(Color.RED);
                holder.bin.setText("," + location.getBinNumber());
                convertView.setBackgroundColor(Color.RED);
                holder.fillLevel.setText(location.getFillLevel() + "%");
                convertView.setBackgroundColor(Color.RED);
                holder.name.setText("");
                holder.name.setChecked(location.isSelected());
                holder.name.setTag(location);
            }else
            if(location.getFillLevel().intValue() > 51 && location.getFillLevel().intValue() < 70)
            {
                holder.location.setText(location.getGeoLocation());
                convertView.setBackgroundColor(Color.YELLOW);
                holder.bin.setText("," + location.getBinNumber());
                convertView.setBackgroundColor(Color.YELLOW);
                holder.fillLevel.setText(location.getFillLevel() + "%");
                convertView.setBackgroundColor(Color.YELLOW);
                holder.name.setText("");
                holder.name.setChecked(location.isSelected());
                holder.name.setTag(location);
            } else {
                holder.location.setText(location.getGeoLocation());
                convertView.setBackgroundColor(Color.GREEN);
                holder.bin.setText("," + location.getBinNumber());
                convertView.setBackgroundColor(Color.GREEN);
                holder.fillLevel.setText(location.getFillLevel() + "%");
                convertView.setBackgroundColor(Color.GREEN);
                holder.name.setText("");
                holder.name.setChecked(location.isSelected());
                holder.name.setTag(location);
            }

            return convertView;

        }

    }
    @Override
    public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
        super.setSupportProgressBarIndeterminateVisibility(visible);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyse_search_activity_main);
        displayListView();
        addListenerOnButton();
    }


    public void addListenerOnButton() {
        selectedRadioGrp = (RadioGroup) findViewById(R.id.selectQuery);
        int selectedId = selectedRadioGrp.getCheckedRadioButtonId();
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        dateFrom = (EditText) findViewById(R.id.dateFrom);
        dateTo = (EditText) findViewById(R.id.dateTo);

        dateFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AnalyseSearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        updateLabel(dateFrom);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AnalyseSearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        updateLabel(dateTo);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = selectedRadioGrp.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                selectedQuery = (RadioButton) findViewById(selectedId);

                Toast.makeText(AnalyseSearchActivity.this,
                        selectedQuery.getText() + "\n" + dateFrom.getText() + "---" + dateTo.getText(), Toast.LENGTH_SHORT).show();
                Bundle dataBundle = new Bundle();
                System.out.println(selectedId);
                dataBundle.putString("query", "" + selectedQuery.getText());
                dataBundle.putString("fromDate", "" + dateFrom.getText());
                dataBundle.putString("toDate", "" + dateTo.getText());
                dataBundle.putString("dateRange", "" + dateFrom.getText() + "-" + dateTo.getText());
                dataBundle.putStringArrayList("selectedLocationList", selectedLocationList);
                Intent intent = new Intent(getApplicationContext(), AnalyseGraph.class);
                intent.putExtras(dataBundle);

                startActivity(intent);

            }

        });

    }

    private void updateLabel(EditText editText) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.analyse:Bundle dataBundle = new Bundle();
                selectedRadioGrp = (RadioGroup) findViewById(R.id.selectQuery);
                int selectedId = selectedRadioGrp.getCheckedRadioButtonId();
                System.out.println(selectedId);
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),AnalyseGraph.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
    @Override
    public void onClick(View view) {

    }



}

