package com.endergeek.rookie.aweatherfinaldemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.endergeek.rookie.aweatherfinaldemo.model.City;
import com.endergeek.rookie.aweatherfinaldemo.model.County;
import com.endergeek.rookie.aweatherfinaldemo.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsenhui on 11/14/16.
 */
public class WeatherDBProcess {

    public static final String DB_NAME = "weather";

    public static final int DB_VERSION = 1;

    private static WeatherDBProcess weatherDBProcess;

    private SQLiteDatabase db;

    private String TABLE_COL_ID = "id";

    private String PROVINCE_TABLE_NAME = "Province";
    private String PROVINCE_COL_NAME = "province_name";
    private String PROVINCE_COL_CODE = "province_code";

    private String CITY_TABLE_NAME = "City";
    private String CITY_COL_NAME = "city_name";
    private String CITY_COL_CODE = "city_code";
    private String CITY_COL_FRO_ID = "province_id";     // City表外键指向Province表id

    private String COUNTY_TABLE_NAME = "County";
    private String COUNTY_COL_NAME = "county_name";
    private String COUNTY_COL_CODE = "county_code";
    private String COUNTY_COL_FRO_ID = "city_id";       // County表外键指向City表id

    /**
     * 构造方法初始化
     * @param context
     */
    private WeatherDBProcess(Context context) {
        WeatherDBOpenHelper weatherDBOpenHelper = new WeatherDBOpenHelper(context, DB_NAME, null, DB_VERSION);
        db = weatherDBOpenHelper.getWritableDatabase();
    }

    /**
     * 获取weatherDBProcess实例
     * @param context
     * @return
     */
    public synchronized static WeatherDBProcess getInstance(Context context) {
        if (weatherDBProcess == null) {
            weatherDBProcess = new WeatherDBProcess(context);
        }
        return weatherDBProcess;
    }

    /**
     * 将获取的Province实例存入数据库
     * @param province
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROVINCE_COL_NAME, province.getProvinceName());
            contentValues.put(PROVINCE_COL_CODE, province.getProvinceCode());
            db.insert(PROVINCE_TABLE_NAME, null, contentValues);
        }
    }

    /**
     * 从数据库中得到Province实例并解释成List<Province>以通过ListView展示
     * @return
     */
    public List<Province> loadProvince() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query(
                PROVINCE_TABLE_NAME, null, null, null, null, null ,null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex(TABLE_COL_ID)));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex(PROVINCE_COL_NAME)));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex(PROVINCE_COL_CODE)));
                list.add(province);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    /**
     * 将获取的City实例存入数据库
     * @param city
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CITY_COL_NAME, city.getCityName());
            contentValues.put(CITY_COL_CODE, city.getCityCode());
            contentValues.put(CITY_COL_FRO_ID, city.getProvinceId());
            db.insert(CITY_TABLE_NAME, null, contentValues);
        }
    }

    /**
     * 从数据库中得到City实例并解释成List<City>以通过ListView展示
     * @return
     */
    public List<City> loadCity(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query(
                CITY_TABLE_NAME, null, CITY_COL_FRO_ID + " = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex(TABLE_COL_ID)));
                city.setCityName(cursor.getString(cursor.getColumnIndex(CITY_COL_NAME)));
                city.setCityCode(cursor.getString(cursor.getColumnIndex(CITY_COL_CODE)));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    /**
     * 将获取的County实例存入数据库
     * @param county
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COUNTY_COL_NAME, county.getCountyName());
            contentValues.put(COUNTY_COL_CODE, county.getCountyCode());
            contentValues.put(COUNTY_COL_FRO_ID, county.getCityId());
            db.insert(COUNTY_TABLE_NAME, null, contentValues);
        }
    }

    /**
     * 从数据库中得到County实例并解释成List<County>以通过ListView展示
     * @return
     */
    public List<County> loadCounty(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query(
                COUNTY_TABLE_NAME, null, COUNTY_COL_FRO_ID + " = ?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex(TABLE_COL_ID)));
                county.setCountyName(cursor.getString(cursor.getColumnIndex(COUNTY_COL_NAME)));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex(COUNTY_COL_CODE)));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
