package com.supermonkey.lifeassistant.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/28
 * @Description
 */
public class PagerSlidingAdapter extends FragmentPagerAdapter {

    private final String[] titles = { "本月领取", "累积领取" };
    private List<Fragment> fragmentLists;

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return titles[position];
    }

    public PagerSlidingAdapter(FragmentManager fm,
                                  List<Fragment> fragmentLists) {
        super(fm);
        this.fragmentLists = fragmentLists;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return fragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return fragmentLists.size();
    }
}
