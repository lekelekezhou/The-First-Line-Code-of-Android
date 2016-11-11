package com.endergeek.rookie.askillfuldemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnJumpS = (Button) findViewById(R.id.btn_jump_serial);
        Button btnJumpP = (Button) findViewById(R.id.btn_jump_parcel);
        btnJumpS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * getSerializableExtra获取通过参数传递过来的序列化对象，再向下转型成Person对象
                 */
                PersonSerializable personS = new PersonSerializable();
                personS.setName("Tom");
                personS.setAge(20);
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("person_data", personS);
                startActivity(intent);
            }
        });
        btnJumpP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonParcelable personP = new PersonParcelable();
                personP.setName("Jack");
                personP.setAge(18);
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("person_data", personP);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
