package com.endergeek.rookie.adatapersistence4filedemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FILE_NAME = "DATA_FILE";
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText etInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        checkStoragePermissions(this);
        etInputText = (EditText) findViewById(R.id.etInput);

        String restoredText = load();
        /**
         * TextUtils.isEmpty() 可以一次性进行null或""值的判断
         * 将输入光标移动到文末以便继续输入
         */
        if (!TextUtils.isEmpty(restoredText)) {
            etInputText.setText(restoredText);
            etInputText.setSelection(restoredText.length());
            Toast.makeText(this, "Restoring succeed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = etInputText.getText().toString();
        save(inputText);
        Log.d(TAG, inputText);
    }

    /**
     * OpenFileOutput方法构建FileOutputStream对象->用以构建OutputStreamWriter对象->用以构建BufferedWriter对象
     * 即可写入内容
     * @param inputText
     */
    private void save(String inputText) {
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 以下为测试部分，确认可使用
         */
//        String FILENAME = "hello_file";
//        try {
//            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//            fos.write(inputText.getBytes());
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * openFileInput方法构建FileInputStream对象->用以构建InputStreamReader对象->用以构建BufferedReader对象
     * 即可读出内容
     * @return
     */
    public String load() {
        FileInputStream fileInputStream;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            fileInputStream = openFileInput(FILE_NAME);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 此写法来自StackOverflow，比较直接，不像我之前写的废话多，demo能用就行
     *
     * 然而此demo并不需要 读写EXTERNAL STORAGE
     *
     * @param activity
     */
    public static void checkStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permissionWrite = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionRead = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionWrite != PackageManager.PERMISSION_GRANTED || permissionRead != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
