package com.endergeek.rookie.asqlitedatabasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by wangsenhui on 10/31/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{

    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text, "
            + "category_id integer)";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGORY);
//        Toast.makeText(mContext, "DB Created", Toast.LENGTH_SHORT).show();
    }

    /**
     * 注意：没有使用break，这是为了保证跨版本升级的时候，每一次的数据库修改都能被执行到
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                sqLiteDatabase.execSQL(CREATE_CATEGORY);
            case 2:
                // 针对Book表有需要增加列名的需求，升级表
                sqLiteDatabase.execSQL("alter table Book add column category_id integer");
            default:
        }
//        sqLiteDatabase.execSQL("drop table if exists Book");
//        sqLiteDatabase.execSQL("drop table if exists Category");
//        onCreate(sqLiteDatabase);
    }
}
