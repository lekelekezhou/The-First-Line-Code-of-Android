package com.endergeek.rookie.alistviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon", "Pear",
            "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };

    private List<Fruit> fruitList = new ArrayList<Fruit>();

    private EditText etInput;

    ListView listView;

    FruitAdapter adapter;

    Fruit tmp_fruit;

    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.activity_list_view);

        etInput = (EditText) findViewById(R.id.et_edit_area);
        btnAdd = (Button) findViewById(R.id.btn_edit);
        btnAdd.setOnClickListener(this);


        /**
         * 获取屏幕的密度值
         */
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;
        Log.w(TAG, "xdpi" + xdpi);
        Log.w(TAG, "ydpi" + ydpi);

        // 定制ListView用法 Begin
        /**
         * initFruits 用以初始化数据集
         * 指定适配的数据类型为定制类型 Fruit
         * args: 上下文， ListView子项布局的id，此处为布局文件 item_list_fruit.xml， 数据 fruitList
         * 调用ListView的setAdapter方法，传入适配器对象
         */
        initFruits();
        adapter = new FruitAdapter(MainActivity.this, R.layout.item_list_fruit, fruitList);
        listView.setAdapter(adapter);
        // 定制ListView用法 End


        // 简单用法 Begin
        /**
         * 通过泛型指定要适配的数据类型 String，然后传入需要适配的数据
         * args: 当前上下文， ListView子项布局的id，此处为Android内置布局， 数据data
         * 调用 ListView的 setAdapter方法，传入适配器对象
         */
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this, android.R.layout.simple_expandable_list_item_1, data
//        );
//        ListView listView = (ListView) findViewById(R.id.activity_list_view);
//        listView.setAdapter(adapter);
        // 简单用法End

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Toast.makeText(MainActivity.this, fruit.getFruitName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 增加 动态添加列表项的功能
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                tmp_fruit = new Fruit(etInput.getText().toString(), R.drawable.ic_mood_bad_black_18dp);
                fruitList.add(tmp_fruit);
                Log.d("Main", "tmp_fruit:" + tmp_fruit + " fruitList:" + fruitList);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void initFruits() {
            Fruit fruit = new Fruit("First", R.drawable.ic_mood_bad_black_18dp);
            fruitList.add(fruit);
    }

}
