package com.endergeek.rookie.sqliteopenhelperdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by wangsenhui on 2016/7/25.
 */
public final class CityDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "city.db";
    public static final int DATABASE_VERSION = 1;

    /*
     * Must Make Comments HERE!!! Dooooooooooooo NoT forget BLANK_SPACE in SQL.
     */
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TableCity.TABLE_NAME + " ( " +
                    TableCity.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TableCity.COLUMN_NAME_PROVINCE + TEXT_TYPE + COMMA_SEP +
                    TableCity.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    TableCity.COLUMN_NAME_DISTRICT + TEXT_TYPE + ")";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableCity.TABLE_NAME;
    private static CityDBHelper mInstance = null;

    // One Way to organize contract class is to define some global variables in your root layer
    // and create inner class for each table.
    public static abstract class TableCity implements BaseColumns {
        public static final String TABLE_NAME = "city";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_PROVINCE = "province";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_DISTRICT = "district";

    }



    public CityDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized CityDBHelper getContext(Context context) {
        if (mInstance == null) {
            mInstance = new CityDBHelper(context);
        }
        return mInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
