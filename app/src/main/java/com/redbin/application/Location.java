package com.redbin.application;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Deepa on 9/21/2015.
 */
public class Location implements Parcelable {

    private int uniqueId;
    private String geoLocation;
    private String location;
    private String binNumber;
    private Integer humidity;
    private Integer temperature;
    private Integer fillLevel;
    private Date fillDate;
    boolean selected = false;

    public Location() {

    }


    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(Integer fillLevel) {
        this.fillLevel = fillLevel;
    }

    public Date getFillDate() {
        return fillDate;
    }

    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        // TODO Auto-generated method stub
        dest.writeInt(uniqueId);
        dest.writeString(geoLocation);
        dest.writeString(location);
        dest.writeString(binNumber);
        dest.writeInt(humidity);
        dest.writeInt(temperature);
        dest.writeInt(fillLevel);
        dest.writeString(fillDate.toString());



    }

    public Location(Parcel in) {
        uniqueId = in.readInt();
        geoLocation = in.readString();
        location = in.readString();
        binNumber = in.readString();
        humidity = in.readInt();
        temperature = in.readInt();
        fillLevel = in.readInt();
        try {
            Date thedate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(in.readString());
            fillDate = thedate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}

