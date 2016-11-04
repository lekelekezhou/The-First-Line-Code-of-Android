package com.endergeek.rookie.avideoplayerdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by wangsenhui on 11/4/16.
 */
public class VideoPlay extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private static final String TAG = VideoPlay.class.getSimpleName();
    private VideoView videoView;
    private Button btnReplay;
    private Button btnPause;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.videoView);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnReplay = (Button) findViewById(R.id.btnReplay);

        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnReplay.setOnClickListener(this);

        btnPlay.setEnabled(false);
        btnPause.setEnabled(false);
        btnReplay.setEnabled(false);
        videoView.setMediaController(new MediaController(this));
        initVideo();
    }

    public  void initVideo() {
        File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        Uri fileUri = Uri.fromFile(file);
        Log.d(TAG, "fileUri" + fileUri);
        videoView.setVideoURI(fileUri);
        videoView.setOnPreparedListener(this);
        videoView.setOnCompletionListener(this);
        videoView.setOnErrorListener(this);
        videoView.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVideo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (!videoView.isPlaying() || videoView != null) {
                    videoView.start();
                }
                break;
            case R.id.btnPause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.btnReplay:
                if (videoView != null) {
                    videoView.resume();
                    initVideo();
                    videoView.start();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        btnPlay.setEnabled(true);
        btnPause.setEnabled(true);
        btnReplay.setEnabled(true);
        Log.d(TAG, "onPrepared");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Toast.makeText(this, "Complete Play", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Toast.makeText(this, "Error Play", Toast.LENGTH_SHORT).show();
        return false;
    }
}
