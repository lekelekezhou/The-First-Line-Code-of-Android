package com.endergeek.rookie.asqlitedatabasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 本例子并不是较好实现，建议参考 SQLiteOpenHelper Demo
 * adb shell 打开并进入数据库目录: /data/data/PACKAGE_NAME/databases/
 * >sqlite3 BookStore.db 查看数据库
 * > .table 查看表
 * > .schema 查看建表语句
 * > .exit 退出
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyDatabaseHelper myDatabaseHelper;

    private Button btnCreateDB;

    private Button btnAddData;

    private Button btnUpdateData;

    private Button btnDeleteData;

    private Button btnQueryData;

    private Button btnReplaceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2); // 版本号只要比上一次大，就会执行onUpgrade

        btnCreateDB = (Button) findViewById(R.id.btnCreateDB);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);
        btnDeleteData = (Button) findViewById(R.id.btnDeleteData);
        btnQueryData = (Button) findViewById(R.id.btnQueryData);
        btnReplaceData = (Button) findViewById(R.id.btnReplaceData);

        btnCreateDB.setOnClickListener(this);
        btnAddData.setOnClickListener(this);
        btnUpdateData.setOnClickListener(this);
        btnDeleteData.setOnClickListener(this);
        btnQueryData.setOnClickListener(this);
        btnReplaceData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreateDB:
                // 当第一次点击时会检测到没有此数据库，会创建并调用onCreate方法，之后因存在则不会创建
                myDatabaseHelper.getWritableDatabase();
                break;
            case R.id.btnAddData:
//                // 直接使用SQL方法
//                SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
//                db.execSQL("insert into Book (name, author, pages, price) values( ?, ?, ?, ?)",
//                        new String[] {"The Da Vinci Code", "Dan Brown", "454", "16.96"});
                addData();
                break;
            case R.id.btnUpdateData:
//                // 直接使用SQL方法
//                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
//                db.execSQL("update Book set price = ? where name = ?",
//                        new String[] {"10.99", "The Da Vinci Code"});
                updateData();
                break;
            case R.id.btnDeleteData:
//                // 直接使用SQL方法
//                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
//                db.execSQL("delete from Book where pages > ?",
//                        new String[] {"500"});
                deleteData();
                break;
            case R.id.btnQueryData:
//                // 直接使用SQL方法
//                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
//                db.rawQuery("select * from Book", null);
                queryData();
                break;
            case R.id.btnReplaceData:
                replaceData();
            default:
                break;
        }
    }



    private void addData() {
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // 添加第一条数据
        contentValues.put("name", "The Da Vinci Code");
        contentValues.put("author", "Dan Brown");
        contentValues.put("pages", 454);
        contentValues.put("price", 16.96);
        sqLiteDatabase.insert(
                "Book",             // 待插入项的表
                null,               // 第二个参数指定在 ContentValues 为空的情况下框架可在其中插入 NULL 的列的名称（如果您将其设置为 "null"， 那么框架将不会在没有值时插入行。）
                contentValues);     // 包含行上列值的映射键值对
        contentValues.clear();
        // 添加第二条数据
        contentValues.put("name", "The Lost Symbol");
        contentValues.put("author", "Dan Brown");
        contentValues.put("pages", 510);
        contentValues.put("price", 19.95);
        sqLiteDatabase.insert("Book", null, contentValues);
    }

    private void updateData() {
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("price", 10.99);
        sqLiteDatabase.update(
                "Book",                                 // 待更新项的表
                contentValues,                          // 参考插入
                "name = ?",                             // 选择条件
                new String[] {"The Da Vinci Code"});    // 选择条件参数
    }

    private void deleteData() {
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(
                "Book",                 // 待删除项的表
                "pages > ?",            // 选择条件
                new String[] {"500"});  // 选择条件参数
    }

    private void queryData() {
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                "Book", // 待查询的表
                null,   // 返回的列
                null,   // 使用 WHERE 查询的列
                null,   // 使用 WHERE 查询的值
                null,   // 不对行分组
                null,   // 不对行过滤
                null);  // 排序方式
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Toast.makeText(this, name + author + pages + price, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void replaceData() {
        SQLiteDatabase sqliteDatabase = myDatabaseHelper.getWritableDatabase();
        sqliteDatabase.beginTransaction(); // 开启事务
        try {
            sqliteDatabase.delete("Book", null, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "Game of Thrones");
            contentValues.put("author", "George Martin");
            contentValues.put("pages", 720);
            contentValues.put("price", 20.85);
            sqliteDatabase.insert("Book", null, contentValues);
            sqliteDatabase.setTransactionSuccessful(); // 事务成功执行
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteDatabase.endTransaction(); // 结束事务
        }
    }
}
