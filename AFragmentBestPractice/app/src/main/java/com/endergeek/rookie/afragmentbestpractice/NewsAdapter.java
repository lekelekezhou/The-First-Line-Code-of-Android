package com.endergeek.rookie.afragmentbestpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangsenhui on 10/24/16.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceId;
    public NewsAdapter(Context context, int textViewResourceId, List<News> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }
        TextView newsTitleText = (TextView) convertView.findViewById(R.id.tvNewsItem);
        newsTitleText.setText(news.getTitle());
        return convertView;
    }
}
