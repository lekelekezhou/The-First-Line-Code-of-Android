package com.endergeek.rookie.aweatherfinaldemo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endergeek.rookie.aweatherfinaldemo.R;
import com.endergeek.rookie.aweatherfinaldemo.util.HttpCallbackListener;
import com.endergeek.rookie.aweatherfinaldemo.util.HttpUtil;
import com.endergeek.rookie.aweatherfinaldemo.util.NetworkUtility;

/**
 * Created by wangsenhui on 11/14/16.
 */
public class WeatherActivity extends AppCompatActivity{

    private static final String TAG = WeatherActivity.class.getSimpleName();

    private LinearLayout weatherInfoLayout;

    private TextView tvCityName;        // 城市名

    private TextView tvPublishTime;     // 发布日期

    private TextView tvWeatherDesc;     // 天气信息

    private TextView tvTemprLow;        // 低温

    private TextView tvTemprHigh;       // 高温

    private TextView tvCurrentDate;     // 当前日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        weatherInfoLayout = (LinearLayout) findViewById(R.id.layout_weather_info);
        tvCityName = (TextView) findViewById(R.id.tv_city_name);
        tvPublishTime = (TextView) findViewById(R.id.tv_publish_info);
        tvWeatherDesc = (TextView) findViewById(R.id.tv_weather_desc);
        tvTemprLow = (TextView) findViewById(R.id.tv_tempr_low);
        tvTemprHigh = (TextView) findViewById(R.id.tv_tempr_high);
        tvCurrentDate = (TextView) findViewById(R.id.tv_weather_date);

        String countyCode = getIntent().getStringExtra("county_code");

        // 代码为县级则查询天气
        if (!TextUtils.isEmpty(countyCode)) {
            tvPublishTime.setText("Sync...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            tvCityName.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            // 没有县级代码则显示本地天气
            showWeather();
        }
    }

    /**
     * 根据地址和类型从服务器查询天气代码或天气信息
     * @param address
     * @param type
     */
    private void queryFromSvr(final String address,final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onError(Exception response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPublishTime.setText("Fail to sync");
                    }
                });
            }

            @Override
            public void onFinish(final String response) {
                // 解析天气代码
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                            Log.d(TAG, "weatherCode" + weatherCode);
                        }
                    }
                    // 解析天气信息
                } else if ("weatherCode".equals(type)) {
                    NetworkUtility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示
     */
    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tvCityName.setText(prefs.getString("city_name", ""));
        tvTemprLow.setText(prefs.getString("tempr_low", ""));
        tvTemprHigh.setText(prefs.getString("tempr_high", ""));
        tvWeatherDesc.setText(prefs.getString("weather_desc", ""));
        tvPublishTime.setText(prefs.getString("publish_time", ""));
        tvCurrentDate.setText(prefs.getString("current_date", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        tvCityName.setVisibility(View.VISIBLE);
    }

    /**
     * 查询代号所对应天气
     * @param weatherCode
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        Log.d(TAG, "queryWeatherInfo:" + address);
        queryFromSvr(address, "weatherCode");
    }

    /**
     * 查询县级代号所对应的天气代号
     * @param countyCode
     */
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        Log.d(TAG, "queryWeatherCode:" + countyCode);
        queryFromSvr(address, "countyCode");
    }
}
