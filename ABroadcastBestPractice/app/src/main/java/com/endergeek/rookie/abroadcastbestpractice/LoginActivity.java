package com.endergeek.rookie.abroadcastbestpractice;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wangsenhui on 10/27/16.
 */
public class LoginActivity extends BaseActivity{

    private static final int SYSTEM_ALERT_WINDOW = 1;

    private EditText etAccount;

    private EditText etPassword;

    private Button btnLogin;

    private Button btnJumpToSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnJumpToSetting = (Button) findViewById(R.id.btnJumpToSetting);
        btnJumpToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 下部分代码将会在点击按钮后将你带入允许 Draw over other apps的坑中，不然你就选择手动入坑吧
                 */
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    // Show alert dialog to the user saying a separate permission is needed
                    // Launch the settings activity if the user prefers
                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(LoginActivity.this, "Nothing to show:)", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogin = (Button) findViewById(R.id.btLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if (account.equals("a") && password.equals("1")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Account or Password invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 因为 Android 6开始新的安全机制，此处涉及到 Draw over other apps 高级权限(SYSTEM_ALERT_WINDOWS)
     * 需要在设置中允许(也许存在更高级实现，因不为本部分重点，目前只做引导设置enable部分)
     * 复制粘贴不能太羞耻
     */
//    private void checkPermission() {
//        int hasReadContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
//        if (hasReadContactsPermission == PackageManager.PERMISSION_GRANTED) {
//            // Should we show an explanation ?
//            Toast.makeText(this, "Permission Has Been Granted", Toast.LENGTH_SHORT).show();
//        } else {
//            // Show an explanation to the user *asynchronously* -- don't block
//            // this thread waiting for the user's response! After the user
//            // sees the explanation, try again to request the permission.
//            Toast.makeText(this, "Permission Hasn't Been Granted", Toast.LENGTH_SHORT).show();
//            requestPermission();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        // Make sure it's our original READ_CONTACTS request.
//        if (requestCode == SYSTEM_ALERT_WINDOW) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                // Return false user click Never Ask Again.
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SYSTEM_ALERT_WINDOW)) {
//                    requestPermission();
//                    return;
//                } else {
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, SYSTEM_ALERT_WINDOW);
//    }
}
