package com.endergeek.rookie.acontentsharingdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.endergeek.rookie.acontentsharingdemo.MESSAGE";
    private static final String TAG = "MainActivity";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private ImageView imageView;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        imageView = (ImageView) findViewById(R.id.select_image_view);
        ImagePicker.setMinQuality(100, 100);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    // 增加一些常见类和方法的笔记总结
    // onClickListener?
    // 从ImagePicker中获取选择的图片Uri,待重写
    public void shareImageTo(View view) {
        /*
         * 以下代码段将发送应用内默认压缩率的图片
         */

        Uri fileUri = ImagePicker.selectedImage;
        Log.w(TAG, "fileUri: " + fileUri);
        Intent shareImageIntent = new Intent(Intent.ACTION_SEND);
        shareImageIntent.setType("image/*");
        shareImageIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        startActivity(Intent.createChooser(shareImageIntent, getResources().getText(R.string.share_image_to)));

        /*
         * 以下代码段将发送指定压缩率率的图片
         */

//        File cachePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +  "/tmp.jpg");
//        Log.w(TAG, "cachePath: " + cachePath);
//        try {
//            cachePath.createNewFile();
//            FileOutputStream ostream = new FileOutputStream(cachePath);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
//            ostream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Intent shareImageIntent = new Intent(Intent.ACTION_SEND);
//        shareImageIntent.setType("image/*");
//        shareImageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
//        startActivity(Intent.createChooser(shareImageIntent, getResources().getText(R.string.share_image_to)));

    }

    public void onPickImage(View view) {
        ImagePicker.pickImage(this, String.valueOf(getResources().getText(R.string.pic_select)));
    }

    // Handle Text Sharing.
    public void shareTextMessageTo(View view) {
        // Called when user click the send button.
        // An Intent can carry data types as key-value pairs called extras.
        Intent sendTextIntent = new Intent();
        sendTextIntent.setAction(Intent.ACTION_SEND);
        // use findViewById() to get the EditText element.
        EditText editText = (EditText)findViewById(R.id.edit_message);
        // Assign the text to a local message variable, and use the putExtra() method to add its text value to the intent.
        String message = editText.getText().toString();

        sendTextIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendTextIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendTextIntent, getResources().getText(R.string.send_text_to)));
    }

    private void checkPermission() {
        int hasReadExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWriteExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasReadExternalStoragePermission == PackageManager.PERMISSION_GRANTED && hasWriteExternalStoragePermission == PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation ?
            Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
        } else {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // Make sure it's our original request.
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions Granted Now", Toast.LENGTH_SHORT).show();
            } else {
                // Return false user click Never Ask Again.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(
                            this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                    return;
                } else {
                    Toast.makeText(this, "Permissions Denied Now", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
