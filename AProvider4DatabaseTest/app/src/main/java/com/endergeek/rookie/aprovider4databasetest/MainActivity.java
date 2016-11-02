package com.endergeek.rookie.aprovider4databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 此应用为 A SQLite Database Demo 关于ContentProvider 的搭配测试应用，测试为数据提供外部访问接口
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String AUTHORITY = "com.endergeek.rookie.asqlitedatabasedemo.provider";

    private Button btnAddData;

    private Button btnQueryData;

    private Button btnUpdateData;

    private Button btnDeleteData;

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnQueryData = (Button) findViewById(R.id.btnQueryData);
        btnDeleteData = (Button) findViewById(R.id.btnDeleteData);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);

        btnAddData.setOnClickListener(this);
        btnUpdateData.setOnClickListener(this);
        btnQueryData.setOnClickListener(this);
        btnDeleteData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddData:
                addData();
                break;
            case R.id.btnQueryData:
                queryData();
                break;
            case R.id.btnUpdateData:
                updateData();
                break;
            case R.id.btnDeleteData:
                deleteData();
                break;
            default:
                break;
        }
    }

    // 通过内容提供器获取另一个应用的数据
    // 删除添加的那条数据
    private void deleteData() {
        Uri uri = Uri.parse("content://" + AUTHORITY + "/book/" + newId);
        getContentResolver().delete(uri, null, null);
    }

    // 更新添加的那条数据
    private void updateData() {
        Uri uri = Uri.parse("content://" + AUTHORITY + "/book/" + newId);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "A Storm of Swords");
        contentValues.put("pages", 1216);
        contentValues.put("price", 24.05);
        getContentResolver().update(uri, contentValues, null, null);
    }

    private void queryData() {
        Uri uri = Uri.parse("content://" + AUTHORITY + "/book");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Toast.makeText(this, name + author + pages + price,Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();
    }

    // insert 会返回一个Uri对象，包含了新增数据的id，通过 getPathSegments()取出此id
    private void addData() {
        Uri uri = Uri.parse("content://" + AUTHORITY + "/book");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "A Clash of Kings");
        contentValues.put("author", "George Martin");
        contentValues.put("pages", 1040);
        contentValues.put("price", 22.85);
        Uri newUri = getContentResolver().insert(uri, contentValues);
        newId = newUri.getPathSegments().get(1);
    }
}
