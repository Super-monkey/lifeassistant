package com.supermonkey.lifeassistant.ui.setting;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.ui.base.BaseActivity;
import com.supermonkey.lifeassistant.view.switchbutton.SwitchButton;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/21
 * @Description 设置界面
 */
public class SettingActivity extends BaseActivity {

    private LinearLayout wifi_settings;//wifi item
    private LinearLayout item_clear_cache;//clear cache

    private SwitchButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        wifi_settings = (LinearLayout) findViewById(R.id.wifi_settings);
        item_clear_cache = (LinearLayout) findViewById(R.id.item_clear_cache);
        switchButton = (SwitchButton) findViewById(R.id.wifi_switch);
    }

    @Override
    public void initListeners() {
        wifi_settings.setOnClickListener(this);
        item_clear_cache.setOnClickListener(this);
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                if (isChecked) {
                    showToast("打开");
                } else {
                    showToast("关闭");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wifi_settings:
                switchButton.setChecked(!switchButton.isChecked());
                break;
            case R.id.item_clear_cache:
                break;
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void setHeader() {
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
