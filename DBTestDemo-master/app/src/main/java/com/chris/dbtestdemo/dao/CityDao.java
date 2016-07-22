package com.chris.dbtestdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.chris.dbtestdemo.bean.CityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2016/5/17 09:43.
 */
public class CityDao {
    private static final String TAG = "CityDao";

    private Context mContext;
    private CityDBHelper mCityDBHelper;
    private SQLiteDatabase db;

    private final String[] TABLE_COLUMNS = new String[]{"id", "province", "city", "district"};

    public CityDao(Context context) {
        mContext = context;
        mCityDBHelper = CityDBHelper.getInstance(context);
    }

    /**
     * 初始化表格
     */
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
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", String.valueOf(i + 1));
                contentValues.put("province", provinces[i]);
                contentValues.put("city", citys[i]);
                contentValues.put("district", districts[i]);
                db.insert(CityDBHelper.TABLE_NAME, null, contentValues);
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

    /**
     * 判断表是否为空
     *
     * @return 空为 true
     */
    public boolean isEmpty() {
        Cursor cursor = null;

        try {
            db = mCityDBHelper.getReadableDatabase();
            cursor = db.query(CityDBHelper.TABLE_NAME,
                    new String[]{"count(id)"},
                    null, null, null, null, null);

            if (cursor.moveToFirst()) {
                if (cursor.getInt(0) > 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "isEmpty: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return true;
    }

    /**
     * 清空数据库表
     *
     * @return boolean
     */
    public boolean clearTable() {
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.delete(CityDBHelper.TABLE_NAME, null, null);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "clearTable: ", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return false;
    }

    /**
     * 查询所有数据
     *
     * @return list
     */
    public List<CityBean> selectAll() {
        Cursor cursor = null;

        try {
            db = mCityDBHelper.getReadableDatabase();
            cursor = db.query(CityDBHelper.TABLE_NAME, TABLE_COLUMNS, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<CityBean> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    list.add(parserCityBean(cursor));
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

    /**
     * 向表中添加一条数据
     *
     * @param cityBean 城市信息实体类
     */
    public boolean insertData(CityBean cityBean) {
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.beginTransaction();

            // 向表中插入一条数据
            // insert into TABLE_NAME (id, province, city, district)
            // values (cityBean.getId(), cityBean.getProvince(),
            // cityBean.getCity(), cityBean.getDistrict())
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Integer.parseInt(cityBean.getId()));
            contentValues.put("province", cityBean.getProvince());
            contentValues.put("city", cityBean.getCity());
            contentValues.put("district", cityBean.getDistrict());
            db.insertOrThrow(CityDBHelper.TABLE_NAME, null, contentValues);

            db.setTransactionSuccessful();

            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(mContext, "主键重复", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }

    /**
     * 从表中删除一条数据
     *
     * @param id 数据 id
     */
    public boolean delete(String id) {
        try {
            db = mCityDBHelper.getWritableDatabase();
            db.beginTransaction();

            // 删除一条 id = id 数据
            // delete TABLE_NAME where id = id
            db.delete(CityDBHelper.TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});

            db.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            Log.e(TAG, "delete: ", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }

    /**
     * 修改表中的数据
     *
     * @param cityBean 城市信息实体类
     *
     * @return 修改成功返回 true
     */
    public boolean update(CityBean cityBean) {
        if (cityBean == null) {
            return false;
        }

        try {
            db = mCityDBHelper.getWritableDatabase();
            db.beginTransaction();

            // 修改一条数据
            // update TABLE_NAME set key = value where id = cityBean.getId()
            ContentValues contentValues = new ContentValues();

            if (!TextUtils.isEmpty(cityBean.getProvince())) {
                contentValues.put("province", cityBean.getProvince());
            }

            if (!TextUtils.isEmpty(cityBean.getCity())) {
                contentValues.put("city", cityBean.getCity());
            }

            if (!TextUtils.isEmpty(cityBean.getDistrict())) {
                contentValues.put("district", cityBean.getDistrict());
            }

            db.update(CityDBHelper.TABLE_NAME, contentValues, "id = ?",
                    new String[]{String.valueOf(cityBean.getId())});

            db.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            Log.e(TAG, "update: ", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }

    /**
     * 根据条件查询数据
     *
     * @param cityBean 天气实体类
     *
     * @return 满足条件的实体类 List
     */
    public List<CityBean> select(CityBean cityBean) {
        Cursor cursor = null;

        try {
            Log.i(TAG, "select: ");

            db = mCityDBHelper.getReadableDatabase();

            if (!TextUtils.isEmpty(cityBean.getId())) {
                Log.i(TAG, "select: id" + cityBean.getId());

                // select * from TABLE_NAME where id = cityBean.getId()
                cursor = db.query(CityDBHelper.TABLE_NAME, TABLE_COLUMNS,
                        "id = ?", new String[]{cityBean.getId()}, null, null, null);

                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parserCityBean(cursor));
                    }

                    return list;
                }
            }

            if (!TextUtils.isEmpty(cityBean.getProvince())) {
                Log.i(TAG, "select: province" + cityBean.getProvince());

                // select * from TABLE_NAME where province = cityBean.getProvince()
                cursor = db.query(CityDBHelper.TABLE_NAME, TABLE_COLUMNS,
                        "province = ?", new String[]{cityBean.getProvince()}, null, null, null);

                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parserCityBean(cursor));
                    }

                    return list;
                }
            }

            if (!TextUtils.isEmpty(cityBean.getCity())) {
                Log.i(TAG, "select: city" + cityBean.getCity());

                // select * from TABLE_NAME where city = cityBean.getCity()
                cursor = db.query(CityDBHelper.TABLE_NAME, TABLE_COLUMNS,
                        "city = ?", new String[]{cityBean.getCity()}, null, null, null);

                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parserCityBean(cursor));
                    }

                    return list;
                }
            }

            if (!TextUtils.isEmpty(cityBean.getDistrict())) {
                Log.i(TAG, "select: district" + cityBean.getDistrict());

                // select * from TABLE_NAME where district = cityBean.getDistrict()
                cursor = db.query(CityDBHelper.TABLE_NAME, TABLE_COLUMNS,
                        "district = ?", new String[]{cityBean.getDistrict()}, null, null, null);

                if (cursor.getCount() > 0) {
                    List<CityBean> list = new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()) {
                        list.add(parserCityBean(cursor));
                    }

                    return list;
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "select: ", e);
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

    /**
     * 将查询到的 Cursor 转成城市信息实体类
     *
     * @param cursor 查询到的 Cursor
     *
     * @return CityBean 城市信息实体类
     */
    public CityBean parserCityBean(Cursor cursor) {
        CityBean cityBean = new CityBean();
        cityBean.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
        cityBean.setProvince(cursor.getString(cursor.getColumnIndex("province")));
        cityBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
        cityBean.setDistrict(cursor.getString(cursor.getColumnIndex("district")));
        return cityBean;
    }
}
