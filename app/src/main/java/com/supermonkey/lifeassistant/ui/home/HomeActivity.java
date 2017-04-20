package com.supermonkey.lifeassistant.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.constant.Event;
import com.supermonkey.lifeassistant.ui.base.BaseActivity;
import com.supermonkey.lifeassistant.view.MyGridLayout;


public class HomeActivity extends BaseActivity {

    private MyGridLayout gridLayout;

    private int[] srcs;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        gridLayout = (MyGridLayout) findViewById(R.id.act_home_gridlayout);
        gridLayout.setGridAdapter(new GridLayoutAdapter(this, srcs, titles));
    }

    @Override
    public void initListeners() {
        gridLayout.setOnItemClickListener(new MyGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(getApplicationContext(), "item=" + index,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void initData() {
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

    @Override
    public void onEventMainThread(Event event) {
        super.onEventMainThread(event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void setHeader() {
        super.setHeader();
        title.setText("主页");
    }

    @Override
    public void onError(String errorMsg, String code) {
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
