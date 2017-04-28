package com.supermonkey.lifeassistant.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.view.MyGridLayout;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/28
 * @Description
 */
public class LearnFragment extends Fragment {
    private MyGridLayout learnLayout;

    private int[] srcs;
    private String[] titles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initData();
        View view = inflater.inflate(R.layout.fragment_home_learn, container, false);
        learnLayout = (MyGridLayout) view.findViewById(R.id.frag_home_learnlayout);
        learnLayout.setGridAdapter(new GridLayoutAdapter(getContext(), srcs, titles));
        learnLayout.setOnItemClickListener(new MyGridLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(getContext(), "Item" + index, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        srcs = new int[]{
                R.mipmap.custom_view_gridlayout_item_booktag, R.mipmap.custom_view_gridlayout_item_comment,
                R.mipmap.custom_view_gridlayout_item_order, R.mipmap.custom_view_gridlayout_item_account,
                R.mipmap.custom_view_gridlayout_item_cent, R.mipmap.custom_view_gridlayout_item_weibo,
                R.mipmap.custom_view_gridlayout_item_feedback, R.mipmap.custom_view_gridlayout_item_about,
                R.mipmap.custom_view_gridlayout_item_booktag, R.mipmap.custom_view_gridlayout_item_comment,
                R.mipmap.custom_view_gridlayout_item_order, R.mipmap.custom_view_gridlayout_item_account,
                R.mipmap.custom_view_gridlayout_item_cent, R.mipmap.custom_view_gridlayout_item_weibo,
                R.mipmap.custom_view_gridlayout_item_feedback, R.mipmap.custom_view_gridlayout_item_about
        };
        titles = new String[]{"书签", "推荐", "订阅", "账户", "积分", "微博", "反馈", "关于我们", "书签",
                "推荐", "订阅", "账户", "积分", "微博", "反馈", "关于我们"};
    }
}
