package com.endergeek.rookie.acontentsharingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.endergeek.rookie.acontentsharingdemo.MESSAGE";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.select_image_view);
        ImagePicker.setMinQuality(100, 100);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    // 增加一些常见类和方法的笔记总结
    public void shareImageTo(View view) {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//
//        // Set ImageButton background src.
//        imageView = (ImageView) findViewById(R.id.select_image_view);
//        imageView.setImageResource(R.drawable.avatar_selector);
//
//        ImagePicker.setMinQuality(100, 100);

    }

    public void onPickImage(View view) {
        ImagePicker.pickImage(this, "Select your Image:");
    }

    // Handle Text Sharing.
    public void shareTextMessageTo(View view) {
        // Called when user click the send button.
        // An Intent can carry data types as key-value pairs called extras.
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        // use findViewById() to get the EditText element.
        EditText editText = (EditText)findViewById(R.id.edit_message);
        // Assign the text to a local message variable, and use the putExtra() method to add its text value to the intent.
        String message = editText.getText().toString();

        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }


}
