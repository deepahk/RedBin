package com.redbin.application;

import android.provider.BaseColumns;

/**
 * Created by Deepa on 9/16/2015.
 */
public class MasterDataMapping implements BaseColumns{

        public static final String GEO_LOCATION = "geo_location";
        public static final String BIN_NUMBER = "bin_number";
        public static final String FILL_LEVEL="fill_level";
        public static final String HUMIDITY="humidity";
        public static final String TEMPERATURE="temperature";
        public static final String FILL_DATE="fill_date";
        public static final String DATABASE_NAME ="smart_bin";
        public static final String TABLE_NAME ="bin_location";
}
