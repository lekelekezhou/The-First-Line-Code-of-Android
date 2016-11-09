package com.endergeek.rookie.asensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int STAND_ACCELEROMETER = 12;

    private static final String TAG = MainActivity.class.getSimpleName();

    private SensorManager sensorManager;

    private Vibrator vibrator;

    private TextView tvLightLevel;

    private TextView tvOrientation;

    private ImageView imgCompass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLightLevel = (TextView) findViewById(R.id.tv_light_level);
        tvOrientation = (TextView) findViewById(R.id.tv_orientation);
        imgCompass = (ImageView) findViewById(R.id.img_compass);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

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
                tvLightLevel.setText("Current light level is " + value + " lx");
            }

            float[] R = new float[9];
            float[] values = new float[3];
            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            SensorManager.getOrientation(R, values);
            Log.d(TAG, String.valueOf(Math.toDegrees(values[0])));
            tvOrientation.setText("Current orientation is " + Math.toDegrees(values[0]));
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
}
