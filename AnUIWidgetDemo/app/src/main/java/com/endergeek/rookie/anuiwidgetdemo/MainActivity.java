package com.endergeek.rookie.anuiwidgetdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMainTest;

    private EditText etMainTest;

    private ImageView imgViewMainTest;

    private ProgressBar pgBarMainTestBar;

    private Button btnMainTestAlert;

    private Button btnMainTestProgress;

    private Button btnGotoLinearLayout;
    private Button btnGotoRelativeLayout;
    private Button btnGotoFrameLayout;
    private Button btnGotoTableLayout;

    private boolean hasImgSet = false;

    private static final String TAG = ".MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMainTest = (Button) findViewById(R.id.btnMainTest);
        etMainTest = (EditText) findViewById(R.id.etMainTest);
        imgViewMainTest = (ImageView) findViewById(R.id.imgMainTest);
        pgBarMainTestBar = (ProgressBar) findViewById(R.id.pgBarMainTest);
        btnMainTestAlert = (Button) findViewById(R.id.btnMainTestAlert);
        btnMainTestProgress = (Button) findViewById(R.id.btnMainTestProgress);

        btnGotoLinearLayout = (Button) findViewById(R.id.btnGotoLinearLayout);
        btnGotoRelativeLayout = (Button) findViewById(R.id.btnGotoRelativeLayout);
        btnGotoFrameLayout = (Button) findViewById(R.id.btnGotoFrameLayout);
        btnGotoTableLayout = (Button) findViewById(R.id.btnGotoTableLayout);

        btnMainTest.setOnClickListener(this);
        btnMainTestAlert.setOnClickListener(this);
        btnMainTestProgress.setOnClickListener(this);
        btnGotoLinearLayout.setOnClickListener(this);
        btnGotoRelativeLayout.setOnClickListener(this);
        btnGotoFrameLayout.setOnClickListener(this);
        btnGotoTableLayout.setOnClickListener(this);

    }

    /**
     * implements View.OnClickListener 后增加 onClick 方法以集中处理点击事件
     * EditText: 编辑文本通过按钮Toast文本
     * ImageView: 通过按钮更改资源图片
     * ProgressBar: 通过按钮更改进度,更多文档https://developer.android.com/reference/android/widget/ProgressBar.html
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMainTest:
                // 设置按钮行为：点击一次进度条+10
                int pgBarPercent = pgBarMainTestBar.getProgress();
                pgBarPercent += 10;
                pgBarMainTestBar.setProgress(pgBarPercent);
//                if (pgBarMainTestBar.getVisibility() == View.GONE) {
//                    pgBarMainTestBar.setVisibility(View.VISIBLE);
//
//                } else {
//                    pgBarMainTestBar.setVisibility(View.GONE);
//                }
                // 设置按钮行为: 切换图片
                if (hasImgSet == false) {
                    imgViewMainTest.setImageResource(R.drawable.avatar_press);
                    hasImgSet = true;
                } else {
                    imgViewMainTest.setImageResource(R.drawable.avatar_normal);
                    hasImgSet = false;
                }

                // 设置按钮行为：显示输入文本
                String etMainTestText = etMainTest.getText().toString();
                if (etMainTestText != null && etMainTestText.length() != 0) {
                    Toast.makeText(MainActivity.this, etMainTestText, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnMainTestAlert:
                /**
                 * AlertDialog显示: new + setTitle + setMessage + setCancelable + setPositiveButton + setNegativeButton + show
                 * setCancelable(true):点击Back(非AlertDialog部分)时可取消
                 */

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("FBI Warning");
                dialog.setMessage("Self Check");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "You click OK", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "You click Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
            case R.id.btnMainTestProgress:
                /**
                 * ProgressDialog显示: new + setTitle + setMessage + setCancelable + show
                 * Note: 一旦setCancelable(false)一定要在加载完毕后调用progressDialog.dismiss();
                 */
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("FBI Progress");
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(true);
                progressDialog.show();
                break;
            case R.id.btnGotoLinearLayout:
                Intent gotoLinearLayoutIntent = new Intent(MainActivity.this, LinearLayoutActivity.class);
                startActivity(gotoLinearLayoutIntent);
                break;
            case R.id.btnGotoRelativeLayout:
                Intent gotoRelativeLayoutIntent = new Intent(MainActivity.this, RelativeLayoutActivity.class);
                startActivity(gotoRelativeLayoutIntent);
                break;
            case R.id.btnGotoFrameLayout:
                Intent gotoFrameLayoutIntent = new Intent(MainActivity.this, FrameLayoutActivity.class);
                startActivity(gotoFrameLayoutIntent);
                break;
            case R.id.btnGotoTableLayout:
                Intent gotoTableLayoutIntent = new Intent(MainActivity.this, TableLayoutActivity.class);
                startActivity(gotoTableLayoutIntent);
                break;
            default:
                break;
        }
    }
}
