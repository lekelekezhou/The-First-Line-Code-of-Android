package com.endergeek.rookie.aweatherfinaldemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.endergeek.rookie.aweatherfinaldemo.R;
import com.endergeek.rookie.aweatherfinaldemo.db.WeatherDBProcess;
import com.endergeek.rookie.aweatherfinaldemo.model.City;
import com.endergeek.rookie.aweatherfinaldemo.model.County;
import com.endergeek.rookie.aweatherfinaldemo.model.Province;
import com.endergeek.rookie.aweatherfinaldemo.util.HttpCallbackListener;
import com.endergeek.rookie.aweatherfinaldemo.util.HttpUtil;
import com.endergeek.rookie.aweatherfinaldemo.util.LogUtil;
import com.endergeek.rookie.aweatherfinaldemo.util.NetworkUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsenhui on 11/14/16.
 */
public class ChooseAreaActivity extends AppCompatActivity{

    private static final String TAG = ChooseAreaActivity.class.getSimpleName();

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;

    private TextView tvTitle;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private WeatherDBProcess weatherDBProcess;

    private List<String> dataList = new ArrayList<String>();

    private List<Province> provinceList;                            // 省列表

    private List<City> cityList;                                    // 市列表

    private List<County> countyList;                                // 郡列表

    private Province selectedProvince;                              // 选中的省

    private City selectedCity;                                      // 选中的城市

    private int currentLevel;                                       // 当前选中级别

    private boolean isFromWeatherActivity;                          // 是否从天气页跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);
        LogUtil.d(TAG, "isFromWeatherActivity:" + isFromWeatherActivity);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /**
         * 再次启动应用时，若已经选择过城市，city_selected=true，且不是从天气页跳转，则直接跳转到天气页
         */
        if (prefs.getBoolean("city_selected", false) && !isFromWeatherActivity) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        weatherDBProcess = WeatherDBProcess.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String countyCode = countyList.get(position).getCountyCode();
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    intent.putExtra("county_code", countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询选中的郡，优先从本地取，再从服务器取
     */
    private void queryCounties() {
        countyList = weatherDBProcess.loadCounty(selectedCity.getId());
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county: countyList) {
                dataList.add(county.getCountyName());
            }
            LogUtil.d(TAG, "dataList:" + dataList);
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            tvTitle.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        } else {
            queryFromSvr(selectedCity.getCityCode(), "county");
        }
    }

    /**
     * 查询选中的市，优先从本地取，再从服务器取
     */
    private void queryCities() {
        cityList = weatherDBProcess.loadCity(selectedProvince.getId()); // id错误会导致npe
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city: cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            tvTitle.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        } else {
            queryFromSvr(selectedProvince.getProvinceCode(), "city");
        }

    }

    /**
     * 查询省数据，优先从本地取，再从服务器取
     */
    private void queryProvinces() {
        provinceList = weatherDBProcess.loadProvince();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            tvTitle.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryFromSvr(null, "province");
        }
    }

    /**
     * 根据代号和类型从服务器查询数据
     */
    private void queryFromSvr(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = NetworkUtility.handleProvinceResponse(weatherDBProcess, response);
                    LogUtil.d(TAG, "province response" + response);
                } else if ("city".equals(type)) {
                    result = NetworkUtility.handleCityResponse(weatherDBProcess, response, selectedProvince.getId());
                    LogUtil.d(TAG, "city response" + response);
                } else if ("county".equals(type)) {
                    result = NetworkUtility.handleCountiesResponse(weatherDBProcess, response, selectedCity.getId());
                    LogUtil.d(TAG, "county response" + response);
                }
                if (result) {
                    runOnUiThread(new Runnable() {  // 通过runOnUiThread在主线程中处理逻辑
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();  // 重新加载
                            } else if ("city".equals(type)) {
                                queryCities();     // 重新加载
                            } else if ("county".equals(type)) {
                                queryCounties();   // 重新加载
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "Fail to load data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 打开进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 重写Back行为
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();              // 此处需要删掉，否则返回行为未被override
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            if (isFromWeatherActivity) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
