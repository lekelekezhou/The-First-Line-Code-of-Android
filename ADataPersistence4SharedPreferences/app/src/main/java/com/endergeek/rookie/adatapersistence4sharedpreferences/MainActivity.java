package com.endergeek.rookie.adatapersistence4sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String FILE_NAME = "file_name";
    private Button btnSaveData;
    private Button btnRestoreData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSaveData = (Button) findViewById(R.id.btnSaveData);
        btnRestoreData = (Button) findViewById(R.id.btnRestoreData);
        Button btnForceOffline = (Button) findViewById(R.id.btnForceOffline);
        btnSaveData.setOnClickListener(this);
        btnRestoreData.setOnClickListener(this);
        btnForceOffline.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveData:
                SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 24);
                editor.putBoolean("married", false);
                editor.commit();
                break;
            case R.id.btnRestoreData:
                SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
                String name = pref.getString("name", "");
                int age = pref.getInt("age", 0);
                boolean married = pref.getBoolean("married", false);
                Toast.makeText(MainActivity.this, "name:" + name + " age:" + age + " married:" + married, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnForceOffline:
                Intent intent = new Intent("com.endergeek.rookie.abroadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;
            default:
                break;
        }
    }
}
