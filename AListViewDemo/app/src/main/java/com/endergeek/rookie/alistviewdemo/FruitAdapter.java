package com.endergeek.rookie.alistviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangsenhui on 10/14/16.
 */
public class FruitAdapter extends ArrayAdapter<Fruit>{

    private int resourceId;

    /**
     * 重写构造函数，用于传递：上下文context， ListView子项布局id， 数据object
     * @param context
     * @param textViewResourceId
     * @param objects
     */
    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    /**
     * 重写getView方法，其在每个子项被滚动到屏幕内的时候调用
     * 接着从此view获取并设置的元素ImageView, TextView
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * 获取当前项的Fruit实例
         */
        Fruit fruit = getItem(position);
        /**
         * LayoutInflater加载传入的布局 resourceId:textViewResourceId 作为 view
         */
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        ImageView fruitImage = (ImageView) view.findViewById(R.id.imgItemFruit);
        TextView fruitName = (TextView) view.findViewById(R.id.tvItemText);

        fruitImage.setImageResource(fruit.getFruitImageId());
        fruitName.setText(fruit.getFruitName());
        return view;
    }
}
