package com.endergeek.rookie.sqliteopenhelperdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CityInfo mCityInfo;
    private List<CityGetInfo> list = new ArrayList<>();
    CityAdapter mCityAdapter;

    private EditText et_main_id;
    private EditText et_main_province;
    private EditText et_main_city;
    private EditText et_main_district;

    private TextView tv_main_msg;

    Button btn_main_insert;
    Button btn_main_delete;
    Button btn_main_update;
    Button btn_main_query;

    Button btn_main_reset;

    ListView lv_main;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    Button btn = (Button)msg.obj;
                    btn.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCityInfo = new CityInfo(this);

        if (mCityInfo.isEmpty()) {
            mCityInfo.initTable();
        }
        initView();
        refreshList();
    }

    private void initView() {
        et_main_id = (EditText)findViewById(R.id.et_main_id);
        et_main_province = (EditText)findViewById(R.id.et_main_province);
        et_main_city = (EditText)findViewById(R.id.et_main_city);
        et_main_district = (EditText)findViewById(R.id.et_main_district);
        btn_main_insert = (Button)findViewById(R.id.btn_main_insert);
        btn_main_delete = (Button)findViewById(R.id.btn_main_delete);
        btn_main_update = (Button)findViewById(R.id.btn_main_update);
        btn_main_query = (Button)findViewById(R.id.btn_main_query);
        btn_main_reset = (Button)findViewById(R.id.btn_main_reset);
        tv_main_msg = (TextView)findViewById(R.id.tv_main_msg);
        lv_main = (ListView)findViewById(R.id.lv_main);

        mCityAdapter = new CityAdapter();
        lv_main.setAdapter(mCityAdapter);

        btn_main_insert.setOnClickListener(this);
        btn_main_delete.setOnClickListener(this);
        btn_main_update.setOnClickListener(this);
        btn_main_query.setOnClickListener(this);
        btn_main_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    private void refreshList() {

    }



    private class CityAdapter {
    }
}
