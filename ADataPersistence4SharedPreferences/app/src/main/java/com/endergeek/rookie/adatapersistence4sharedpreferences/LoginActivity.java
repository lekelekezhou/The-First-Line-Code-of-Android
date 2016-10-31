package com.endergeek.rookie.adatapersistence4sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wangsenhui on 10/27/16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String REMEMBER_PASSWORD = "remember_password";
    private static final String USER_NAME = "account";
    private static final String USER_PASSWORD = "password";


    private SharedPreferences sharedPref;

    private SharedPreferences.Editor sharedEditor;

    private EditText etAccount;

    private EditText etPassword;

    private Button btnLogin;

    private Button btnJumpToSetting;

    private CheckBox chkRememberPassword;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnJumpToSetting = (Button) findViewById(R.id.btnJumpToSetting);
        chkRememberPassword = (CheckBox) findViewById(R.id.chkRememberPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnJumpToSetting.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        /**
         * 读取数据部分：获取SharedPreferences对象，获取值
         */
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = sharedPref.getBoolean(REMEMBER_PASSWORD, false);
        if (isRemember) {
            String account = sharedPref.getString(USER_NAME, "");
            String password = sharedPref.getString(USER_PASSWORD, "");
            etAccount.setText(account);
            etPassword.setText(password);
            chkRememberPassword.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnJumpToSetting:
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
                break;
            case R.id.btnLogin:
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if (account.equals("admin") && password.equals("admin")) {
                    /**
                     * 检查复选框是否被选中，如果是，则保存账号密码(三步)，否则清空，最终提交，
                     */
                    sharedEditor = sharedPref.edit();
                    if (chkRememberPassword.isChecked()) {
                        sharedEditor.putBoolean(REMEMBER_PASSWORD, true);
                        sharedEditor.putString(USER_NAME, account);
                        sharedEditor.putString(USER_PASSWORD, password);
                    } else {
                        sharedEditor.clear();
                    }
                    sharedEditor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Account or Password invalid", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
