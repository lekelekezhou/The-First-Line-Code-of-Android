package com.chris.dbtestdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.dbtestdemo.bean.CityBean;
import com.chris.dbtestdemo.dao.CityDBHelper;
import com.chris.dbtestdemo.dao.CityDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private CityDao mCityDao;
    private List<CityBean> list = new ArrayList<>();
    CityAdapter mCityAdapter;

    private EditText et_main_id;
    private EditText et_main_province;
    private EditText et_main_city;
    private EditText et_main_district;
    Button btn_main_insert;
    Button btn_main_delete;
    Button btn_main_update;
    Button btn_main_select;
    Button btn_main_reset;
    private TextView tv_main_msg;
    ListView lv_main;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    Button btn = (Button) msg.obj;
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

        mCityDao = new CityDao(this);

        if (mCityDao.isEmpty()) {
            mCityDao.initTable();
        }

        initView();
        refreshList();
    }

    private void initView() {
        et_main_id = (EditText) findViewById(R.id.et_main_id);
        et_main_province = (EditText) findViewById(R.id.et_main_province);
        et_main_city = (EditText) findViewById(R.id.et_main_city);
        et_main_district = (EditText) findViewById(R.id.et_main_district);
        btn_main_insert = (Button) findViewById(R.id.btn_main_insert);
        btn_main_delete = (Button) findViewById(R.id.btn_main_delete);
        btn_main_update = (Button) findViewById(R.id.btn_main_update);
        btn_main_select = (Button) findViewById(R.id.btn_main_select);
        btn_main_reset = (Button) findViewById(R.id.btn_main_reset);
        tv_main_msg = (TextView) findViewById(R.id.tv_main_msg);
        lv_main = (ListView) findViewById(R.id.lv_main);

        mCityAdapter = new CityAdapter();
        lv_main.setAdapter(mCityAdapter);

        btn_main_insert.setOnClickListener(this);
        btn_main_delete.setOnClickListener(this);
        btn_main_update.setOnClickListener(this);
        btn_main_select.setOnClickListener(this);
        btn_main_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_insert:
                btn_main_insert.setEnabled(false);

                insert();
                break;

            case R.id.btn_main_delete:
                btn_main_delete.setEnabled(false);

                delete();
                break;

            case R.id.btn_main_update:
                btn_main_update.setEnabled(false);

                update();
                break;

            case R.id.btn_main_select:
                btn_main_select.setEnabled(false);

                select();
                break;

            case R.id.btn_main_reset:
                btn_main_reset.setEnabled(false);

                reset();
                break;

            default:

                break;
        }
    }

    private void insert() {
        String id = et_main_id.getText().toString();
        String province = et_main_province.getText().toString();
        String city = et_main_city.getText().toString();
        String district = et_main_district.getText().toString();

        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(province)
                && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(district)) {
            CityBean cityBean = new CityBean();
            cityBean.setId(id);
            cityBean.setProvince(province);
            cityBean.setCity(city);
            cityBean.setDistrict(district);

            Message message = new Message();
            message.what = 0x123;
            message.obj = btn_main_insert;

            if (mCityDao.insertData(cityBean)) {
                String msg = "向表中插入一条数据\n" +
                        "insert into " + CityDBHelper.TABLE_NAME +
                        "(id, province, city, district) values (" +
                        "\"" + cityBean.getId() + "\", " +
                        "\"" + cityBean.getProvince() + "\", " +
                        "\"" + cityBean.getCity() + "\", " +
                        "\"" + cityBean.getDistrict() + "\")";
                tv_main_msg.setText(msg);

                refreshList();

                mHandler.sendMessageDelayed(message, 3000);
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            } else {
                mHandler.sendMessage(message);
                Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请填写需要添加数据", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete() {
        String id = et_main_id.getText().toString();
        String province = et_main_province.getText().toString();
        String city = et_main_city.getText().toString();
        String district = et_main_district.getText().toString();

        Message message = new Message();
        message.what = 0x123;
        message.obj = btn_main_delete;

        if (!TextUtils.isEmpty(id) && TextUtils.isEmpty(province)
                && TextUtils.isEmpty(city) && TextUtils.isEmpty(district)) {
            if (mCityDao.delete(id)) {
                String msg = "删除一条数据\n" +
                        "delete form" + CityDBHelper.TABLE_NAME + "where id = " + id;
                tv_main_msg.setText(msg);

                refreshList();

                mHandler.sendMessageDelayed(message, 3000);
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            } else {
                mHandler.sendMessage(message);
                Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请正确填写删除条件", Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        String id = et_main_id.getText().toString();
        String province = et_main_province.getText().toString();
        String city = et_main_city.getText().toString();
        String district = et_main_district.getText().toString();

        Message message = new Message();
        message.what = 0x123;
        message.obj = btn_main_update;

        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, "请填写需要修改的数据id", Toast.LENGTH_SHORT).show();
        } else {
            if (!TextUtils.isEmpty(province) | !TextUtils.isEmpty(city)
                    | !TextUtils.isEmpty(district)) {
                CityBean cityBean = new CityBean();
                cityBean.setId(id);
                cityBean.setProvince(province);
                cityBean.setCity(city);
                cityBean.setDistrict(district);

                if (mCityDao.update(cityBean)) {
                    String msg = "修改一条数据\n" +
                            "update " + CityDBHelper.TABLE_NAME + " set" +
                            " province = " + cityBean.getProvince() +
                            ", city = " + cityBean.getCity() +
                            ", district = " + cityBean.getDistrict() +
                            ", where id = " + cityBean.getId();
                    tv_main_msg.setText(msg);

                    refreshList();

                    mHandler.sendMessageDelayed(message, 3000);
                    Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                } else {
                    mHandler.sendMessage(message);
                    Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请正确填写需要修改的数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void select() {
        String id = et_main_id.getText().toString();
        String province = et_main_province.getText().toString();
        String city = et_main_city.getText().toString();
        String district = et_main_district.getText().toString();

        CityBean cityBean = null;
        String msg = "";

        if (!TextUtils.isEmpty(id) && TextUtils.isEmpty(province)
                && TextUtils.isEmpty(city) && TextUtils.isEmpty(district)) {
            cityBean = new CityBean();
            cityBean.setId(id);

            msg = "查询数据\n" +
                    "select * from " + CityDBHelper.TABLE_NAME + " where id = " + id;
        } else if (TextUtils.isEmpty(id) && !TextUtils.isEmpty(province)
                && TextUtils.isEmpty(city) && TextUtils.isEmpty(district)) {
            cityBean = new CityBean();
            cityBean.setProvince(province);

            msg = "查询数据\n" +
                    "select * from " + CityDBHelper.TABLE_NAME + " where province = " + province;
        } else if (TextUtils.isEmpty(id) && TextUtils.isEmpty(province)
                && !TextUtils.isEmpty(city) && TextUtils.isEmpty(district)) {
            cityBean = new CityBean();
            cityBean.setCity(city);

            msg = "查询数据\n" +
                    "select * from " + CityDBHelper.TABLE_NAME + " where city = " + city;
        } else if (TextUtils.isEmpty(id) && TextUtils.isEmpty(province)
                && TextUtils.isEmpty(city) && !TextUtils.isEmpty(district)) {
            cityBean = new CityBean();
            cityBean.setDistrict(district);

            msg = "查询数据\n" +
                    "select * from " + CityDBHelper.TABLE_NAME + " where district = " + district;
        } else if (TextUtils.isEmpty(id) && TextUtils.isEmpty(province)
                && TextUtils.isEmpty(city) && TextUtils.isEmpty(district)) {
            Toast.makeText(this, "请填写一个查询条件", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "只能填写一个查询条件", Toast.LENGTH_SHORT).show();
        }

        Message message = new Message();
        message.what = 0x123;
        message.obj = btn_main_select;

        if (cityBean != null) {
            List<CityBean> selectList = mCityDao.select(cityBean);

            if (selectList != null) {
                list.clear();
                list.addAll(selectList);
                tv_main_msg.setText(msg);

                mCityAdapter.notifyDataSetChanged();

                mHandler.sendMessageDelayed(message, 3000);
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            } else {
                mHandler.sendMessage(message);
                Toast.makeText(this, "请正确填写查询条件", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void reset() {
        if (!mCityDao.isEmpty()) {
            mCityDao.clearTable();
            mCityDao.initTable();
            tv_main_msg.setText("重置数据库表");

            Message message = new Message();
            message.what = 0x123;
            message.obj = btn_main_reset;
            mHandler.sendMessageDelayed(message, 3000);

            refreshList();
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshList() {
        list.clear();
        list.addAll(mCityDao.selectAll());
        mCityAdapter.notifyDataSetChanged();
    }

    class CityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_lv_main, null);

                holder = new ViewHolder();
                holder.tv_item_lv_main_id =
                        (TextView) convertView.findViewById(R.id.tv_item_lv_main_id);
                holder.tv_item_lv_main_province =
                        (TextView) convertView.findViewById(R.id.tv_item_lv_main_province);
                holder.tv_item_lv_main_city =
                        (TextView) convertView.findViewById(R.id.tv_item_lv_main_city);
                holder.tv_item_lv_main_district =
                        (TextView) convertView.findViewById(R.id.tv_item_lv_main_district);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityBean cityBean = list.get(position);
            if (cityBean == null) {
                return null;
            }

            holder.tv_item_lv_main_id.setText(cityBean.getId());
            holder.tv_item_lv_main_province.setText(cityBean.getProvince());
            holder.tv_item_lv_main_city.setText(cityBean.getCity());
            holder.tv_item_lv_main_district.setText(cityBean.getDistrict());

            return convertView;
        }

        class ViewHolder {
            TextView tv_item_lv_main_id;
            TextView tv_item_lv_main_province;
            TextView tv_item_lv_main_city;
            TextView tv_item_lv_main_district;
        }

    }

}
