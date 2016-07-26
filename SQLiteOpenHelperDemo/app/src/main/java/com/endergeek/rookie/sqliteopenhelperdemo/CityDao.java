package com.endergeek.rookie.sqliteopenhelperdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsenhui on 2016/7/22.
 */
public class CityDao {
    private static final String TAG = "CityDao";

    private Context mContext;
    private CityDBHelper mCityDBHelper;
    private SQLiteDatabase db;

    private final String[] TABLE_COLUMNS = new String[]{"id", "province", "city", "district"};
    public CityDao(Context context) {
        mContext = context;
        mCityDBHelper = CityDBHelper.getContext(context);
    }

    public void initTable() {
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.beginTransaction();
            String[] provinces = new String[]{
                    "北京", "北京", "辽宁", "辽宁", "陕西",
                    "陕西", "黑龙江", "黑龙江", "山东", "山东"
            };
            String[] citys = new String[]{
                    "北京", "北京", "沈阳", "丹东", "西安",
                    "咸阳", "哈尔滨", "齐齐哈尔", "青岛", "菏泽"
            };
            String[] districts = new String[]{
                    "朝阳", "海淀", "法库", "凤城", "临潼",
                    "兴平", "双城", "讷河", "崂山", "曹县"
            };
            for (int i = 0; i < 10; i++) {
                ContentValues values = new ContentValues();
                values.put(CityDBHelper.TableCity.COLUMN_NAME_ID, String.valueOf(i+1));
                values.put(CityDBHelper.TableCity.COLUMN_NAME_PROVINCE, provinces[i]);
                values.put(CityDBHelper.TableCity.COLUMN_NAME_CITY, citys[i]);
                values.put(CityDBHelper.TableCity.COLUMN_NAME_DISTRICT, districts[i]);
                db.insert(CityDBHelper.TableCity.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            Log.e(TAG, "initTable: ", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public boolean isEmpty() {
        Cursor cursor = null;
        try {
            db = mCityDBHelper.getReadableDatabase();
            cursor = db.query(CityDBHelper.TableCity.TABLE_NAME, new String[]{"count(id)"},
                    null, null, null,null, null);
            if (cursor.moveToFirst()) {
                if (cursor.getInt(0) > 0 ) {
                    return false;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "isEmpty: ", e);
        } finally {
            if ( cursor != null ) {
                cursor.close();
            }
            if ( db != null ) {
                db.close();
            }
        }

        return true;
    }

    public void clearTable() {
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.delete(CityDBHelper.TableCity.TABLE_NAME, null, null);
        } catch (Exception e) {
            Log.e(TAG, "clearTable: ", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<CityBean> queryAll() {
        Cursor cursor = null;

        try {
            db = mCityDBHelper.getReadableDatabase();
            cursor = db.query(CityDBHelper.TableCity.TABLE_NAME, TABLE_COLUMNS,
                    null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<CityBean> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    list.add(parseCityInfo(cursor));
                }
                return list;
            }
        } catch (Exception e) {
            Log.e(TAG, "selectAll: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public void insertData(CityBean mCityBean) {
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(CityDBHelper.TableCity.COLUMN_NAME_ID, Integer.parseInt(mCityBean.getId()));
            values.put(CityDBHelper.TableCity.COLUMN_NAME_PROVINCE, mCityBean.getProvince());
            values.put(CityDBHelper.TableCity.COLUMN_NAME_CITY, mCityBean.getCity());
            values.put(CityDBHelper.TableCity.COLUMN_NAME_DISTRICT, mCityBean.getDistrict());
            db.insertOrThrow(CityDBHelper.TableCity.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(mContext, "Duplicated Primary key", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public void deleteData(String  id) {
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(CityDBHelper.TableCity.TABLE_NAME, CityDBHelper.TableCity.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("deleteData", String.valueOf(e));
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public void updateData(CityBean mCityBean) {
        if (mCityBean == null) {
            return;
        }
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues values = new ContentValues();
            if (!TextUtils.isEmpty(mCityBean.getProvince())) {
                values.put(CityDBHelper.TableCity.COLUMN_NAME_PROVINCE, mCityBean.getProvince());
            }
            if (!TextUtils.isEmpty(mCityBean.getCity())) {
                values.put(CityDBHelper.TableCity.COLUMN_NAME_CITY, mCityBean.getCity());
            }
            if (!TextUtils.isEmpty(mCityBean.getDistrict())) {
                values.put(CityDBHelper.TableCity.COLUMN_NAME_DISTRICT, mCityBean.getDistrict());
            }

            db.update(CityDBHelper.TableCity.TABLE_NAME, values, CityDBHelper.TableCity.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(mCityBean.getId())});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "update: ", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public List<CityBean> queryData(CityBean mCityBean) {
        Cursor cursor = null;

        try {
            db = mCityDBHelper.getReadableDatabase();
            if (!TextUtils.isEmpty(mCityBean.getId())) {
                cursor = db.query(CityDBHelper.TableCity.TABLE_NAME, TABLE_COLUMNS, CityDBHelper.TableCity.COLUMN_NAME_ID + " = ?",
                        new String[]{mCityBean.getId()}, null, null, null);
                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parseCityInfo(cursor));
                    }
                    return list;
                }
            }
            if (!TextUtils.isEmpty(mCityBean.getProvince())) {
                cursor = db.query(CityDBHelper.TableCity.TABLE_NAME, TABLE_COLUMNS, CityDBHelper.TableCity.COLUMN_NAME_PROVINCE + " = ?",
                        new String[]{(mCityBean.getProvince())}, null, null, null);
                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parseCityInfo(cursor));
                    }
                    return list;
                }
            }
            if (!TextUtils.isEmpty(mCityBean.getCity())) {
                cursor = db.query(CityDBHelper.TableCity.TABLE_NAME, TABLE_COLUMNS, CityDBHelper.TableCity.COLUMN_NAME_CITY + " = ?",
                        new String[]{mCityBean.getCity()}, null, null, null);
                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parseCityInfo(cursor));
                    }
                    return list;
                }
            }
            if (!TextUtils.isEmpty(mCityBean.getDistrict())) {
                cursor = db.query(CityDBHelper.TableCity.TABLE_NAME, TABLE_COLUMNS, CityDBHelper.TableCity.COLUMN_NAME_DISTRICT + " = ?",
                        new String[]{mCityBean.getDistrict()}, null, null, null);
                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parseCityInfo(cursor));
                    }
                    return list;
                }
            }
        } catch (Exception e) {
            Log.e("query", String.valueOf(e));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public CityBean parseCityInfo(Cursor cursor) {
        CityBean mCityBean = new CityBean();
        mCityBean.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(CityDBHelper.TableCity.COLUMN_NAME_ID))));
        mCityBean.setProvince(cursor.getString(cursor.getColumnIndex(CityDBHelper.TableCity.COLUMN_NAME_PROVINCE)));
        mCityBean.setCity(cursor.getString(cursor.getColumnIndex(CityDBHelper.TableCity.COLUMN_NAME_CITY)));
        mCityBean.setDistrict(cursor.getString(cursor.getColumnIndex(CityDBHelper.TableCity.COLUMN_NAME_DISTRICT)));
        return mCityBean;
    }
}
