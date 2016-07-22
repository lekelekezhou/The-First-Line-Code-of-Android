package com.chris.dbtestdemo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chris on 2016/5/17 09:40.
 */
public class CityDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "city.db";
    public static final String TABLE_NAME = "city";
    public static final int DB_VERSION = 1;

    private static CityDBHelper mInstance = null;

    public CityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    static synchronized CityDBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CityDBHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + "(" +
                "id integer primary key," +
                "province text," +
                "city text," +
                "district text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
