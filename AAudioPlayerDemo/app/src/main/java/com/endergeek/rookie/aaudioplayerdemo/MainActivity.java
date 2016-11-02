package com.endergeek.rookie.aaudioplayerdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISSION_READ = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private Button btnPlay;

    private Button btnPause;

    private Button btnStop;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // java.io.FileNotFoundException: /storage/emulated/0/music.mp3: open failed: EACCES (Permission denied)
        requestPermission(this);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        mediaPlayer
//        initMediaPlayer(); // 初始化MediaPlayer
    }

    private void initMediaPlayer() {
        File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
        Log.d(TAG, String.valueOf(file));
        try {
            // 需要预先在根目录下存在 music.mp3文件
            mediaPlayer.setDataSource(file.getPath()); // 指定音频文件路径
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepare(); // 让MediaPlayer进入准备状态
            Log.d(TAG, "filePath:" + file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case R.id.btnPause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.btnStop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset(); // 停止播放
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    /**
     * Android 6.0 就是这样，下面这段代码都写烂了...
     * @param activity
     */
    public void requestPermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSION_READ,
                    REQUEST_CODE
            );
        }
    }
}
