package com.endergeek.rookie.asensortest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int STAND_ACCELEROMETER = 12;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private SensorManager sensorManager;

    private LocationManager locationManager;

    private Vibrator vibrator;

    private TextView tvLightLevel;

    private TextView tvOrientation;

    private ImageView imgCompass;

    private TextView tvGpsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStoragePermissions(this);
        tvLightLevel = (TextView) findViewById(R.id.tv_light_level);
        tvOrientation = (TextView) findViewById(R.id.tv_orientation);
        imgCompass = (ImageView) findViewById(R.id.img_compass);
        tvGpsInfo = (TextView) findViewById(R.id.tv_gps_info);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, (LocationListener) this);

        Sensor sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // 亮度传感器
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 加速度传感器
        Sensor sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); // 地磁传感器

        sensorManager.registerListener(listener, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, sensorMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 传感器退出或传感器使用完毕，释放使用资源
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {

        float[] accelerometerValues = new float[3];

        float[] magneticValues = new float[3];

        private float lastRotateDegree;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) { // 加速器处理
                accelerometerValues = sensorEvent.values.clone();
                float xValue = Math.abs(sensorEvent.values[0]);
                float yValue = Math.abs(sensorEvent.values[1]);
                float zValue = Math.abs(sensorEvent.values[2]);
//                Log.d(TAG, "xValue:" + xValue + "yValue" + yValue + "zValue" + zValue);
                if (xValue > STAND_ACCELEROMETER || yValue > STAND_ACCELEROMETER || zValue > STAND_ACCELEROMETER) {
                    /**
                     * 方法需要VIBRATE权限
                     * vibrate(long MILLS): 毫秒数
                     * vibrate(long[] PATTERN, int REPEAT): PATTERN开关模式(开启等待毫秒，开启持续毫秒)，REPEAT设置开始重复位置，-1则不重复
                     */
                    vibrator.vibrate(new long[]{800, 300, 800, 300}, -1);
                    Toast.makeText(MainActivity.this, "Shake~ Shake~", Toast.LENGTH_SHORT).show();
                }
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { // 地磁处理
                magneticValues = sensorEvent.values.clone();
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) { // 光亮处理
                // values数组的第一个值为当前的光照强度
                float value = sensorEvent.values[0];
                tvLightLevel.setText("Current light level is: " + value + " lx");
            }

            float[] R = new float[9];
            float[] values = new float[3];
            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            SensorManager.getOrientation(R, values);
            Log.d(TAG, String.valueOf(Math.toDegrees(values[0])));
            tvOrientation.setText("Current orientation is: " + Math.toDegrees(values[0]));
            float rotateDegree = -(float) Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
                RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                imgCompass.startAnimation(animation);
                lastRotateDegree = rotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onLocationChanged(Location location) {
        String myLocation ="Location changed...\n\nYou are located at: " + "\nLatitude: " + location.getLatitude()
                + "\nLongitude: " + location.getLongitude();
        tvGpsInfo.setText(myLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(), "Gps is turned on... ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Gps is turned off... ",
                Toast.LENGTH_SHORT).show();
    }

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
