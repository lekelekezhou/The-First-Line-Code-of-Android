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
     * NOTE： 性能瓶颈，每次都将重新加载布局
     * 重写getView方法，其在每个子项被滚动到屏幕内的时候调用
     * 接着从此view获取并设置的元素ImageView, TextView
     * @param position
     * @param convertView 用于将之间加载好的布局进行缓存，为二次加载提速
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position); // 获取当前项的Fruit实例
        ViewHolder viewHolder;
        /**
         * convertView 为 null则初始化，并设置 viewHolder对象，将 viewHolder存储在 view中
         * 否则重新获取 ViewHolder，setTag 存数据；getTag 取数据
         * 结果： 所有控件实例都缓存在了 ViewHolder里，无需继续findViewById
         */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) convertView.findViewById(R.id.imgItemFruit);
            viewHolder.fruitName = (TextView) convertView.findViewById(R.id.tvItemText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * LayoutInflater加载传入的布局 resourceId:textViewResourceId 作为 view
         */
        viewHolder.fruitImage.setImageResource(fruit.getFruitImageId());
        viewHolder.fruitName.setText(fruit.getFruitName());
        return convertView;
    }

    private static class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
    }
}
