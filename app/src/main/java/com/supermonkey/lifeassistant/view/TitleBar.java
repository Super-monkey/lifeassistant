package com.supermonkey.lifeassistant.view;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.ui.setting.SettingActivity;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/21
 * @Description 公共标题栏
 */
public class TitleBar extends LinearLayout {

    private TextView mTitle;
    private ImageButton mToggle;
    private ImageButton mSetting;

    /**
     * 设置标题
     */
    private String title;

    public TitleBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(final Context context, AttributeSet attrs, int defStyleAttr) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_title, this);
        mTitle = (TextView) view.findViewById(R.id.top_bar_title);
        mToggle = (ImageButton) view.findViewById(R.id.m_toggle);
        mSetting = (ImageButton) view.findViewById(R.id.m_setting);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            title = a.getString(R.styleable.TitleBar_top_title);
            mTitle.setText(title);
        }


        mToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
            }
        });
        mSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, SettingActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public void setTitle(CharSequence text) {
        mTitle.setText(text);
    }

    public void setTitle(int resid) {
        mTitle.setText(getResources().getText(resid));
    }
}
