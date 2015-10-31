package com.redbin.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Deepa on 9/16/2015.
 */
public class DataFetch extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ MasterDataMapping.TABLE_NAME+"("+ MasterDataMapping.GEO_LOCATION+" TEXT,"+ MasterDataMapping.BIN_NUMBER+" TEXT,"+ MasterDataMapping.FILL_LEVEL+" TEXT,"+ MasterDataMapping.HUMIDITY+" TEXT,"+ MasterDataMapping.TEMPERATURE+" TEXT,"+ MasterDataMapping.FILL_DATE+" TEXT );";
    SQLiteDatabase sLite;
    public DataFetch(Context context) {
        super(context, MasterDataMapping.DATABASE_NAME, null, database_version);
        Log.d("Database data", "Database Created");
        sLite = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sdb){
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database data", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS "+ MasterDataMapping.TABLE_NAME);
        onCreate(db);
    }

    public void insertLocationData(DataFetch dop,String geoLoc,String binNum,String fillLevel,String humudity, String temp,String fillDate)
    {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MasterDataMapping.GEO_LOCATION,geoLoc);
        cv.put(MasterDataMapping.BIN_NUMBER,binNum);
        cv.put(MasterDataMapping.FILL_LEVEL,fillLevel);
        cv.put(MasterDataMapping.HUMIDITY,humudity);
        cv.put(MasterDataMapping.TEMPERATURE,temp);
        cv.put(MasterDataMapping.FILL_DATE, fillDate);
        long res = SQ.insert(MasterDataMapping.TABLE_NAME,null,cv);
        Log.d("Database data", "Inserted Successfully");
    }

    public void selectAllLocation(SQLiteDatabase sdb){
        sdb.execSQL("select count(*) from " + MasterDataMapping.TABLE_NAME);
        Log.d("dataCheck","dataCheck");
    }

    public void deleteAllLocation(DataFetch dop){
        SQLiteDatabase SQ = dop.getWritableDatabase();
        SQ.execSQL("delete from "+ MasterDataMapping.TABLE_NAME);
        Log.d("deleted Successfully","deleted Successfully");
    }


    public  List<Location> fetchLocationData(){
        List<Location> locationList = new ArrayList<Location>();

        final Cursor cursor = sLite.rawQuery("SELECT * from "+ MasterDataMapping.TABLE_NAME+";",null);
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
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
                    Date fillDate = null;
                    try {
                        fillDate = formatter.parse(cursor.getString(5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("fillDate"+fillDate);
                    loc.setFillDate(fillDate);
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
