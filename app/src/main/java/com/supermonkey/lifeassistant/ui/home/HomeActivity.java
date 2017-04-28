package com.supermonkey.lifeassistant.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.constant.Event;
import com.supermonkey.lifeassistant.ui.base.BaseActivity;
import com.supermonkey.lifeassistant.view.TitleBar;
import com.supermonkey.lifeassistant.view.pager.PagerSlidingTabStrip;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity {

    private TitleBar titleBar;
    private Banner banner;
    private ViewPager viewPager;
    private PagerSlidingTabStrip pagerSliding;

    private Fragment lifeFragment;
    private Fragment learnFragment;

    private PagerSlidingAdapter pagerAdapter;

    private List<Fragment> fragmentList;
    private List<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void initViews() {
        titleBar = (TitleBar) findViewById(R.id.home_title_bar);
        banner = (Banner) findViewById(R.id.banner);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerSliding = (PagerSlidingTabStrip) findViewById(R.id.pager_sliding);

        //banner.setImages(images).setImageLoader(new GlideImageLoader()).start();

        pagerAdapter = new PagerSlidingAdapter(getSupportFragmentManager(),
                fragmentList);
        viewPager.setAdapter(pagerAdapter);
        pagerSliding.setViewPager(viewPager);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        fragmentList = new ArrayList<>();
        lifeFragment = new LifeFragment();
        learnFragment = new LearnFragment();
        fragmentList.add(lifeFragment);
        fragmentList.add(learnFragment);
        /*Integer[] imageTmp = {R.mipmap.notice_loading,R.mipmap.notice_loading};
        images = Arrays.asList(imageTmp);*/
    }

    @Override
    public void onEventMainThread(Event event) {
        super.onEventMainThread(event);
    }


    @Override
    public void setHeader() {
        titleBar.setTitle("主页");
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

    @Override
    public void onClick(View v) {

    }
}
