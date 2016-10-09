package com.endergeek.rookie.acontentsharingdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.endergeek.rookie.acontentsharingdemo.MESSAGE";
    private static final String TAG = "MainActivity";
    private static final int INTENT_ID = 2000;

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

        Button buttonGotoSecond = (Button) findViewById(R.id.btn_goto_secondary);
        buttonGotoSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 启动活动2
                 * 隐式Intent，与在AndroidManifest.xml中定义的<action>一致，以启动intent
                 * 自定义Category(一般不使用)前缀必须加上以保证唯一性
                 */
                Intent intent = new Intent("com.endergeek.rookie.acontentsharingdemo.SecondActivity.ACTION_START");
                intent.addCategory("com.endergeek.rookie.acontentsharingdemo.SecondActivity.CUSTOM_CATEGORY");
                startActivityForResult(intent, INTENT_ID);
                // 显式Intent
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
            }
        });

        Button buttonGotoThird = (Button) findViewById(R.id.btn_goto_third);
        buttonGotoThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 启动活动3
                 * 在对应 intent-filter 中配置响应的 action = Intent.ACTION_VIEW,category = DEFAULT, scheme = http
                 * ThirdActivity如果能够加载并显示网页，则能够响应一个打开网页的Intent，否则，默认行为是选择打开程序：浏览器 或者 ThirdActivity
                 */
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://cn.bing.com"));
                startActivity(intent);
            }
        });

        Button buttonBack = (Button) findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击以销毁活动，退出应用
                finish();
            }
        });
    }

    /**
     * @param requestCode 请求码
     * @param resultCode 结果代码, setResult中设定
     * @param data 携带返回数据的intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
                break;
            case 2000:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    Log.i(TAG, "Return Data: " + returnedData);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 增加一些常见类和方法的笔记总结
     * 从ImagePicker中获取选择的图片Uri,待重写
     */
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

    public void queryIpWeb(View view){

        // 调用系统浏览器查询ip归属地
        Intent queryIpIntent = new Intent(Intent.ACTION_VIEW);
        EditText editText = (EditText) findViewById(R.id.et_ip_area);
        String message = editText.getText().toString();
        queryIpIntent.setData(Uri.parse("http://www.ip138.com/ips138.asp?action=2&ip=" + message));
        startActivity(queryIpIntent);
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
