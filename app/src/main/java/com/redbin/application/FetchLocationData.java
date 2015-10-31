package com.redbin.application;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepa on 9/26/2015.
 */
public class FetchLocationData extends Activity{

    List<Location> locationList;
    Context ctx = this;

    public List<Location> loadLocationDataFromDb(){
        locationList = new ArrayList<>();
        DataFetch dataFetch = new DataFetch(ctx);
        dataFetch.deleteAllLocation(dataFetch);
        dataFetch.insertLocationData(dataFetch,"Kor1","bin1","95","20","29","23-08-2015");
        dataFetch.insertLocationData(dataFetch,"Kor1","bin2","90","20","29","24-08-2015");
        dataFetch.insertLocationData(dataFetch,"Kor1","bin3","65","20","29","25-08-2015");
        dataFetch.insertLocationData(dataFetch,"Kor1","bin4","55","20","29","26-08-2015");
        dataFetch.insertLocationData(dataFetch,"Kor2","bin1","25","20","29","27-08-2015");
        dataFetch.insertLocationData(dataFetch,"Kor2","bin2","15","20","29","28-08-2015");
        locationList = fetchLocationData();
        return locationList;
    }

    public List<Location> fetchLocationData(){
        locationList = new ArrayList<>();
        DataFetch dataFetch = new DataFetch(ctx);
        SQLiteDatabase db = dataFetch.getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * from "+ MasterDataMapping.TABLE_NAME+";",null);
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
                    Log.d("fetch check", loc.getGeoLocation());
                    locationList.add(loc);
                }
            } finally {
                cursor.close();
            }
        }
        return locationList;
    }
}
