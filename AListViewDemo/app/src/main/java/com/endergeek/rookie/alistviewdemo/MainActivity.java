package com.endergeek.rookie.alistviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon", "Pear",
            "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };

    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 定制ListView用法 Begin
        /**
         * initFruits 用以初始化数据集
         * 指定适配的数据类型为定制类型 Fruit
         * args: 上下文， ListView子项布局的id，此处为布局文件 item_list_fruit.xml， 数据 fruitList
         * 调用ListView的setAdapter方法，传入适配器对象
         */
        initFruits();
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.item_list_fruit, fruitList);
        ListView listView = (ListView) findViewById(R.id.activity_list_view);
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

    }

    private void initFruits() {
        Fruit apples = new Fruit("Sad", R.drawable.ic_mood_bad_black_18dp);
        Fruit banana = new Fruit("Smile", R.drawable.ic_mood_black_18dp);
        Fruit orange = new Fruit("Sorrow", R.drawable.ic_sentiment_dissatisfied_black_18dp);

        for(int i = 0; i < 100; i++ ) {
            fruitList.add(apples);
            fruitList.add(banana);
            fruitList.add(orange);
        }
    }
}
