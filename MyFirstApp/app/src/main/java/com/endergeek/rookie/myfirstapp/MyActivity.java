package com.endergeek.rookie.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;


public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.endergeek.rookie.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void sendMessage(View view){
        // Called when the user click the Send Button.
        // An Intent can carry data types as key-value pairs called extras.
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        // use findViewById() to get the EditText element.
        EditText editText = (EditText)findViewById(R.id.edit_message);
        // Assign the text to a local message variable, and use the putExtra() method to add its text value to the intent.
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
