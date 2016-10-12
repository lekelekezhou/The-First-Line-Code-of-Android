package com.endergeek.rookie.anuiwidgetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wangsenhui on 10/12/16.
 */
public class TableLayoutActivity extends AppCompatActivity{
    private Button btnGotoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_basic_table);

        btnGotoMain = (Button) findViewById(R.id.btnTableBackMain);
        btnGotoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
