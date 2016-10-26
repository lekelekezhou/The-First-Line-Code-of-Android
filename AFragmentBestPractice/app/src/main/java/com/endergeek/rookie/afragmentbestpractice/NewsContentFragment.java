package com.endergeek.rookie.afragmentbestpractice;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wangsenhui on 10/24/16.
 */
public class NewsContentFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_news_content, container, false);
        return view;
    }

    /**
     * textView.setMovementMethod(new ScrollingMovementMethod()); + scrollbars="vertical"
     * 即可让TextView滚动起来
     * @param newsTitle
     * @param newsContent
     */
    public void refresh(String newsTitle, String newsContent) {
        View visibilityLayout = view.findViewById(R.id.llayout_visibility);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = (TextView) view.findViewById(R.id.tvNewsTitle);
        TextView newsContentText = (TextView) view.findViewById(R.id.tvNewsContent);
        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
        newsContentText.setMovementMethod(new ScrollingMovementMethod());
    }
}
