package com.endergeek.rookie.afragmentbestpractice;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsenhui on 10/24/16.
 */
public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView newsTitleListView;

    private List<News> newsList;

    private NewsAdapter adapter;

    private boolean isTwoPane = false;

    /**
     * 首先执行，因此用作数据初始化
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newsList = getNews(); // 初始化新闻数据
        adapter = new NewsAdapter(context, R.layout.item_news, newsList);
    }

    /**
     * 加载fragment 标题布局，并给新闻列表增加点击事件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_title, container, false);
        newsTitleListView = (ListView) view.findViewById(R.id.listViewNewsTitle);
        newsTitleListView.setAdapter(adapter);
        newsTitleListView.setOnItemClickListener(this);
        return view;
    }

    /**
     * 通过是否能找到frameLayoutNewsContent的View来判断当前是单页还是双页模式，此id由设备sw判断加载
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.frameLayoutNewsContent) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        News news = newsList.get(pos);
        if (isTwoPane) {
            // 如果是双页模式，则刷新NewsContentFragment中的内容，使用activity_main.xml
            NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager()
                    .findFragmentById(R.id.fragmentNewsContent);
            newsContentFragment.refresh(news.getTitle(), news.getContent());
        } else {
            // 如果是单页模式，则直接启动NewsContentActivity
            NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
        }
    }

    public List<News> getNews() {
        List<News> newsList = new ArrayList<News>();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            news.setTitle(i + "ss - Revision");
            news.setContent(i + "Did you know...\n" +
                    "A 2014 census reports less than 1,900 Pandas are left in the wild. Increased development has had major impacts on the forests these Pandas call home.\n" +
                    "How you can help\n" +
                    "The forests of China’s Yunnan and Sichuan provinces are home to the endangered Yunnan golden monkey and the giant panda, and yet these species could disappear. Caring for degraded forests and improving the management of replanted forests are critical to their future. The past decade, has seen giant panda numbers rise by 17%. We can keep that number climbing. Restore China's forests.\n" +
                    "About this nonprofit\n" +
                    "The Nature Conservancy is the leading conservation organization working around the world to protect ecologically important lands and waters for nature and people. We've protected more than 119 million acres of land and thousands of miles of rivers worldwide, impacting conservation in 69 countries and all 50 states.");
            newsList.add(news);
        }
        return newsList;
    }
}
