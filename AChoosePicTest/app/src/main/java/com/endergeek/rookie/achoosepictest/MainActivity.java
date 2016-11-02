package com.endergeek.rookie.achoosepictest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 由于照片裁剪可能导致内存泄露，因此建议根据需求先对照片压缩再加载，更复杂例子参见： A content sharing demo.
 * 别忘增加权限
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int TAKE_PHOTO = 1;

    public static final int CROP_PHOTO = 2;

    public static final int CHOOSE_PHOTO = 3;

    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView imgPhoto;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(this);
        Button btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        Button btnChoosePhoto = (Button) findViewById(R.id.btnChoosePhoto);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnTakePhoto.setOnClickListener(this);
        btnChoosePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTakePhoto:
                // 创建File对象并命名，放在根目录下
                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage); // Uri.fromFile将File对象转换为Uri对象，标记唯一地址
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 指定图片的输出地址"output"
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.btnChoosePhoto:
                Intent intentChoosePhoto = new Intent("android.intent.action.GET_CONTENT");
                intentChoosePhoto.setType("image/*");
                startActivityForResult(intentChoosePhoto, CHOOSE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果拍照成功，将再次启动裁剪Intent
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            // 裁剪回调逻辑，返回结果并设置bitmap对象
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imgPhoto.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本，4.4
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
            default:
                break;
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imgPhoto.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection获取真实图片路径
        Cursor cursor = getContentResolver().query(
                uri, null, selection, null, null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }
        // 正确关闭Cursor方式:
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // path:/storage/emulated/0/Pictures/Screenshots/Screenshot_20161102-202044.png
        Log.d(TAG, "path:" + path);
        return path;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri， 则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            // docId:image:368064
            Log.d(TAG, "docId:" + docId);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析处数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果不是document类型的Uri，则使用普通方式处理
                imagePath = getImagePath(uri, null);
            }
            // imagePath:/storage/emulated/0/Pictures/Screenshots/Screenshot_20161102-202044.png
            Log.d(TAG, "imagePath:" + imagePath);
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private void requestPermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_CODE
            );
        }
    }
}
