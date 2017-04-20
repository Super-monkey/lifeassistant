package com.supermonkey.lifeassistant.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.view.MyGridLayout;


/**
 * @author supermonkey
 * @version 1.0
 * @date 创建时间：2017/4/20
 * @Description 格子布局Adapter
 */
public class GridLayoutAdapter implements MyGridLayout.GridAdatper {

    private int[] srcs;
    private String[] titles;
    private Context mContext;

    public GridLayoutAdapter(Context context,int[] srcs,String[] titles){
        mContext = context;
        this.srcs = srcs;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public View getView(int index) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_view_gridlayout_item,
                null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        iv.setImageResource(srcs[index]);
        tv.setText(titles[index]);
        return view;
    }
}
