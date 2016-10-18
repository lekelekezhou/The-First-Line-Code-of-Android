package com.endergeek.rookie.anuibestpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangsenhui on 10/18/16.
 */
public class MessageAdapter extends ArrayAdapter<Message>{

    private int resourceId;

    public MessageAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutLeft);
            viewHolder.rightLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutRight);
            viewHolder.leftMessage = (TextView) convertView.findViewById(R.id.tvMessageLeft);
            viewHolder.rightMessage = (TextView) convertView.findViewById(R.id.tvMessageRight);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /**
         * 根据消息类型判断显示哪侧布局和文字
         */
        if (msg.getType() == Message.TYPE_RECEIVED) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMessage.setText(msg.getContent());
        } else if (msg.getType() == Message.TYPE_SENT) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMessage.setText(msg.getContent());
        }
        return convertView;
    }

    private static class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMessage;
        TextView rightMessage;
    }
}
