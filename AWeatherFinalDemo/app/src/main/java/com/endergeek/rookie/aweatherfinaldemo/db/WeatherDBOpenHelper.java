package com.endergeek.rookie.aweatherfinaldemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangsenhui on 11/14/16.
 */
public class WeatherDBOpenHelper extends SQLiteOpenHelper{

    /**
     * Province表建表
     */
    public static final String CREATE_PROVINCE = "CREATE TABLE Province ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "province_name TEXT, "
            + "province_code TEXT)";

    /**
     * City表建表
     */
    public static final String CREATE_CITY = "CREATE table City ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "city_name TEXT, "
            + "city_code TEXT, "
            + "province_id INTEGER)";

    /**
     * County表建表
     */
    public static final String CREATE_COUNTY = "CREATE table County ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "county_name TEXT, "
            + "county_code TEXT, "
            + "city_id INTEGER)";

    public WeatherDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 创建三个表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
