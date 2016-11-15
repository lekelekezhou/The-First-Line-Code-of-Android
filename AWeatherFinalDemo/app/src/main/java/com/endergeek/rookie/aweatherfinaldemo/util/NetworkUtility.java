package com.endergeek.rookie.aweatherfinaldemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.endergeek.rookie.aweatherfinaldemo.db.WeatherDBProcess;
import com.endergeek.rookie.aweatherfinaldemo.model.City;
import com.endergeek.rookie.aweatherfinaldemo.model.County;
import com.endergeek.rookie.aweatherfinaldemo.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wangsenhui on 11/14/16.
 */
public class NetworkUtility {

    private static final String TAG = NetworkUtility.class.getSimpleName();

    /**
     * 解析处理Province数据并写入数据库
     * @param weatherDBProcess
     * @param response
     * @return
     */
    public synchronized static boolean handleProvinceResponse(WeatherDBProcess weatherDBProcess, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] provinces = response.split(",");
            if (provinces != null && provinces.length > 0) {
                for (String p: provinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    weatherDBProcess.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析处理City数据并写入数据库
     * @param weatherDBProcess
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(WeatherDBProcess weatherDBProcess, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] cities = response.split(",");
            if (cities != null && cities.length > 0) {
                for (String c: cities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    weatherDBProcess.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析处理County数据并写入数据库
     * @param weatherDBProcess
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountiesResponse(WeatherDBProcess weatherDBProcess, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] counties = response.split(",");
            if (counties != null && counties.length > 0) {
                for (String c: counties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    /**
                     * 原：county.setCountyCode(array[1]);
                     * 此处造成了难追溯的
                     * java.lang.NullPointerException:
                     * Attempt to invoke virtual method 'java.lang.String java.lang.Object.toString()' on a null object reference
                     * 即countyName=null，在List<String> dataList.add时存在null->String
                     */
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    weatherDBProcess.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * {"weatherinfo":
     *      {"city":"..", "cityid":"..", "temp1":"..", "temp2":"..", "weather":"..", "img":"..", "img2":"..", "ptime":".."}
     * }
     * @param context
     * @param response
     */
    public static void handleWeatherResponse(Context context, String response) {
        LogUtil.d(TAG, "response in handler" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String tempLow = weatherInfo.getString("temp1");
            String tempHigh = weatherInfo.getString("temp2");
            String weatherDesc = weatherInfo.getString("weather");
            String pTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, tempLow, tempHigh, weatherDesc, pTime);
            LogUtil.d(TAG, "weatherinfo" + weatherInfo + "cityName:" + cityName
                    + " weatherCode:" + weatherCode + " temprLow:" + tempLow
                    + " temprHigh:" +tempHigh + " weatherDesc:" + weatherDesc
                    + " pTime:" + pTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void saveWeatherInfo(Context context, String cityName, String weatherCode, String tempLow, String tempHigh, String weatherDesc, String pTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("tempr_low", tempLow);
        editor.putString("tempr_high", tempHigh);
        editor.putString("weather_desc", weatherDesc);
        editor.putString("publish_time", pTime);
        editor.putString("current_date", sdf.format(new Date()));
        LogUtil.d(TAG, "city_name:" + cityName
                + " weather_code:" + weatherCode + " tempr_low:" + tempLow
                + " tempr_high:" +tempHigh + " weather_desc:" + weatherDesc
                + " publish_time:" + pTime + " current_date:" + sdf.format(new Date()));
        editor.commit();
    }
}
