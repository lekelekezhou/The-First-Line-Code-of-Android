package com.endergeek.rookie.sqliteopenhelperdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Button btn_main_query;

    Button btn_main_reset;
    private TextView tv_main_msg;
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

        mCityDao = new CityDao(this);

        if (mCityDao.isEmpty()) {
            mCityDao.initTable();
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
        switch (view.getId()) {
            case R.id.btn_main_insert:
                btn_main_insert.setEnabled(false);
                insert();
                btn_main_insert.setEnabled(true);
                break;

            case R.id.btn_main_delete:
                btn_main_delete.setEnabled(false);
                delete();
                btn_main_delete.setEnabled(true);
                break;

            case R.id.btn_main_update:
                btn_main_update.setEnabled(false);
                update();
                btn_main_update.setEnabled(true);
                break;

            case R.id.btn_main_query:
                btn_main_query.setEnabled(false);
                query();
                btn_main_query.setEnabled(true);
                break;

            case R.id.btn_main_reset:
                btn_main_reset.setEnabled(false);
                reset();
                btn_main_reset.setEnabled(true);
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

        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(province) && !TextUtils.isEmpty(city) &&
                !TextUtils.isEmpty(district)) {
            CityBean mCityBean = new CityBean();
            mCityBean.setId(id);
            mCityBean.setProvince(province);
            mCityBean.setCity(city);
            mCityBean.setDistrict(district);

            Message message = new Message();
            message.what = 0x123;
            message.obj = btn_main_insert;

            try {
                mCityDao.insertData(mCityBean);
                String msg = "Insert into Table:\n" +
                        "INSERT INTO " + CityDBHelper.TableCity.TABLE_NAME +
                        "(id, province, city, district) VALUES (" +
                        "\"" + mCityBean.getId() + "\", " +
                        "\"" + mCityBean.getProvince() + "\", " +
                        "\"" + mCityBean.getCity() + "\", " +
                        "\"" + mCityBean.getDistrict() + "\")";
                tv_main_msg.setText(msg);
                refreshList();

                mHandler.sendMessageDelayed(message, 3000);
                Toast.makeText(this, "Insert Done", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Insert Error!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Verify Your Data Format Before Insert", Toast.LENGTH_SHORT).show();
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

        if (!TextUtils.isEmpty(id) && TextUtils.isEmpty(province) && TextUtils.isEmpty(city) &&
                TextUtils.isEmpty(district)) {
            try {
                mCityDao.deleteData((id));
                String msg = "Delete A Record\n" +
                        "DELETE FROM " + CityDBHelper.TableCity.TABLE_NAME + "WHERE ID =  " + id;
                tv_main_msg.setText(msg);

                refreshList();

                mHandler.sendMessageDelayed(message, 3000);
                Toast.makeText(this, "Delete Done!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                mHandler.sendMessage(message);
                Toast.makeText(this, "Delete Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Verify Your Data Format Before Delete", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Please Specify Id To Be Updated", Toast.LENGTH_SHORT).show();
        } else {
            if (!TextUtils.isEmpty(province) |  !TextUtils.isEmpty(city) |
                    !TextUtils.isEmpty(district)) {
                CityBean mCityBean = new CityBean();
                mCityBean.setId(id);
                mCityBean.setProvince(province);
                mCityBean.setCity(city);
                mCityBean.setDistrict(district);

                try {
                    mCityDao.updateData(mCityBean);
                    String msg = "Revise Data:\n" +
                            "UPDATE " + CityDBHelper.TableCity.TABLE_NAME + " SET" +
                            " province = " + mCityBean.getProvince() +
                            ", city = " + mCityBean.getCity() +
                            ", district = " + mCityBean.getDistrict() +
                            ", where id = " + mCityBean.getId();
                    tv_main_msg.setText(msg);

                    refreshList();

                    mHandler.sendMessageDelayed(message, 3000);
                    Toast.makeText(this, "Delete Done!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    mHandler.sendMessage(message);
                    Toast.makeText(this, "Update  Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Verify Your Data Format Before Delete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void query() {
        String id = et_main_id.getText().toString();
        String province = et_main_province.getText().toString();
        String city = et_main_city.getText().toString();
        String district = et_main_district.getText().toString();

        CityBean mCityBean = null;
        String message_qry = "";

        if (!TextUtils.isEmpty(id) && TextUtils.isEmpty(province) && TextUtils.isEmpty(city) &&
                TextUtils.isEmpty(district)) {
            mCityBean = new CityBean();
            mCityBean.setId(id);
            message_qry = "Query Data\n" +
                    "SELECT * FROM " + CityDBHelper.TableCity.TABLE_NAME +
                    " WHERE ID = " + id;
        } else  if (TextUtils.isEmpty(id) && !TextUtils.isEmpty(province) && TextUtils.isEmpty(city) &&
                TextUtils.isEmpty(district)) {
            mCityBean = new CityBean();
            mCityBean.setProvince(province);
            message_qry = "Query Data\n" +
                    "SELECT * FROM " + CityDBHelper.TableCity.TABLE_NAME +
                    "WHERE province = " + province;
        } else  if (TextUtils.isEmpty(id) && TextUtils.isEmpty(province) && !TextUtils.isEmpty(city) &&
                TextUtils.isEmpty(district)) {
            mCityBean = new CityBean();
            mCityBean.setCity(city);
            message_qry = "Query Data\n" +
                    "SELECT * FROM " + CityDBHelper.TableCity.TABLE_NAME +
                    "WHERE city = " + city;
        } else  if (TextUtils.isEmpty(id) && TextUtils.isEmpty(province) && TextUtils.isEmpty(city) &&
                !TextUtils.isEmpty(district)) {
            mCityBean = new CityBean();
            mCityBean.setDistrict(district);
            message_qry = "Query Data\n" +
                    "SELECT * FROM " + CityDBHelper.TableCity.TABLE_NAME +
                    "WHERE district = " + district;
        } else  if (TextUtils.isEmpty(id) && TextUtils.isEmpty(province) && TextUtils.isEmpty(city) &&
                TextUtils.isEmpty(district)) {
            /* *****                   **/
            refreshList();

            Toast.makeText(this, "Please Specify At Least 1 Condition", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please Specify Only 1 Condition", Toast.LENGTH_SHORT).show();
        }

        Message message = new Message();
        message.what = 0x123;
        message.obj = btn_main_query;

        if ( mCityBean != null) {
            List<CityBean> queryList = mCityDao.queryData(mCityBean);

            if (queryList != null) {
                list.clear();
                list.addAll(queryList);
                tv_main_msg.setText(message_qry);

                mCityAdapter.notifyDataSetChanged();

                mHandler.sendMessageDelayed(message, 3000);
                Toast.makeText(this, "Query Done", Toast.LENGTH_SHORT).show();
            } else {
                mHandler.sendMessage(message);
                Toast.makeText(this, "Please Specify Condition", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void reset() {
        if (!mCityDao.isEmpty()) {
            mCityDao.clearTable();
            mCityDao.initTable();
            tv_main_msg.setText("Reset Table");

            Message message = new Message();
            message.what = 0x123;
            message.obj = btn_main_reset;
            mHandler.sendMessageDelayed(message, 3000);

            refreshList();
            Toast.makeText(this, "Reset Done", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshList() {
        list.clear();
        list.addAll(mCityDao.queryAll());
        mCityAdapter.notifyDataSetChanged();
    }

    class CityAdapter extends BaseAdapter{
        @Override
        public int getCount() { return list.size(); }

        @Override
        public Object getItem(int position) { return list.get(position); }

        @Override
        public long getItemId(int position) {return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_lv_main, null);

                holder = new ViewHolder();
                holder.tv_item_lv_main_id =
                        (TextView)convertView.findViewById(R.id.tv_item_lv_main_id);
                holder.tv_item_lv_main_province =
                        (TextView)convertView.findViewById(R.id.tv_item_lv_main_province);
                holder.tv_item_lv_main_city =
                        (TextView)convertView.findViewById(R.id.tv_item_lv_main_city);
                holder.tv_item_lv_main_district =
                        (TextView)convertView.findViewById(R.id.tv_item_lv_main_district);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            CityBean mCityBean = list.get(position);
            if (mCityBean == null) {
                return null;
            }
            holder.tv_item_lv_main_id.setText(mCityBean.getId());
            holder.tv_item_lv_main_province.setText(mCityBean.getProvince());
            holder.tv_item_lv_main_city.setText(mCityBean.getCity());
            holder.tv_item_lv_main_district.setText(mCityBean.getDistrict());
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv_item_lv_main_id;
        TextView tv_item_lv_main_province;
        TextView tv_item_lv_main_city;
        TextView tv_item_lv_main_district;
    }
}
