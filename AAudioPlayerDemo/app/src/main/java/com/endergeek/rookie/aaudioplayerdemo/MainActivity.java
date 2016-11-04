package com.endergeek.rookie.aaudioplayerdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

/**
 * 参考代码，一直报错，且只能在杀死activity重新进入的时候音频可以播放
 * E/MediaPlayer: start called in state 1
 * E/MediaPlayer: start called in state 0
 * 原以为prepare处理不对，后来重写异常抛出，发现"报错细节"是java.lang.IllegalStateException MediaPlayer.prepare
 * 经过打Log.d发现不可读取到 filePath，在写 requestPermission 的情况下依然有 Permission Denied 警告
 * 因此诊断出 prepare的时候，授权操作还没进行，当授权完毕时，onPrepareListener由于prepare得不到文件因此不能完成
 * 故不会进入onPrepared，已修复
 *
 * 调用onRequestPermissionsResult 获取请求结果码，每一次不允许访问均抛出异常，一旦允许访问即可播放
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISSION_READ = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    MediaPlayer mediaPlayer = new MediaPlayer();

    private Button btnStop;
    private Button btnPause;
    private Button btnPlay;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // java.io.FileNotFoundException: /storage/emulated/0/music.mp3: open failed: EACCES (Permission denied)
        checkPermission(this);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPause.setEnabled(false);
        btnPlay.setEnabled(false);
        btnStop.setEnabled(false);
        initMediaPlayer(); // 初始化MediaPlayer
    }

    private void initMediaPlayer() {
        // 需要预先在根目录下存在 music.mp3文件
        file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
        Log.d(TAG, String.valueOf(file));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(file.getPath()); // 指定音频文件路径
            mediaPlayer.prepare(); // 让MediaPlayer进入准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(this);
        Log.d(TAG, "filePath:" + file.getPath());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMediaPlayer();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        btnPlay.setEnabled(true);
        btnPause.setEnabled(true);
        btnStop.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (!mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.start();
                }
                break;
            case R.id.btnPause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.btnStop:
                // 由于isPlaying只能判断play状态，因此使用!= null判断只要MediaPlayer对象存在，即重置
                if (mediaPlayer != null) {
                    mediaPlayer.reset();
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

    private void checkPermission(Activity activity) {
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadContactsPermission == PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation ?
            Toast.makeText(this, "Permission Has Been Granted", Toast.LENGTH_SHORT).show();
        } else {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            Toast.makeText(this, "Permission Hasn't Been Granted", Toast.LENGTH_SHORT).show();
            requestPermission(activity);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request.
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                // Return false user click Never Ask Again.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    requestPermission(this);
                    return;
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
}
