package com.endergeek.rookie.acontact4contentproviderdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS_READ_CONTACTS = {
            Manifest.permission.READ_CONTACTS
    };

    private static final int REQUEST_READ_CONTACTS = 1;

    ListView listViewContacts;

    ArrayAdapter<String> adapter;

    List<String> listContacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStoragePermissions(this);
        listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listContacts);
        listViewContacts.setAdapter(adapter);
        readContacts();
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            /**
             * Uri对象使用方式
             * ContentResolver resolver = context.getContentResolver();
             * Cursor cursor = resolver.query(
             * uri, // 待查询某应用下的表
             * projection, // 待查询的列名
             * selection, // 指定WHERE的约束条件
             * selectionArgs, // 为WHERE中的占位符提供具体的值
             * sortOrder); // 指定查询结果的排序方式
             * 查询联系人数据
             */
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                /**
                 * 获取联系人信息
                 * ContactsContract.CommonDataKinds.Phone类已封装，获取其中常量作为参数
                 * 等价于Uri.parse(URI)产生的Uri对象作为参数
                 */
                String nameContact = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String numberContact = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                listContacts.add(nameContact + "\n" + numberContact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void checkStoragePermissions(Activity activity) {
        // Check if we have permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_READ_CONTACTS,
                    REQUEST_READ_CONTACTS
            );
        }
    }
}
