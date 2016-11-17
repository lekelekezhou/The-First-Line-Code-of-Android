package com.endergeek.rookie.askillfuldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by wangsenhui on 11/10/16.
 */
public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        PersonSerializable personS = (PersonSerializable) getIntent().getSerializableExtra("person_data"); // 向下转型成PersonSerializable对象
        if (personS != null) {
            Toast.makeText(getApplicationContext(), "Age:" + personS.getAge() + "Name" + personS.getName(), Toast.LENGTH_SHORT).show();
        }
        PersonParcelable personP = getIntent().getParcelableExtra("person_data");
        if (personP != null) {
            Toast.makeText(getApplicationContext(), "Age:" + personP.getAge() + "Name" + personP.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
