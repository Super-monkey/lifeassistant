package com.supermonkey.lifeassistant.ui.personcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;
import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.constant.Event;
import com.supermonkey.lifeassistant.ui.base.BaseActivity;
import com.supermonkey.lifeassistant.util.DensityUtil;

import de.greenrobot.event.EventBus;


public class HomeActivity extends BaseActivity {
    /**
     * 返回
     */
    private Button back;

    /**
     * 图片
     */
    private ImageButton image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home_activity);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        back = (Button) findViewById(R.id.back);
        image = (ImageButton) findViewById(R.id.image);
    }

    @Override
    public void initListeners() {
        back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").resize(DensityUtil.dip2px(this,200), DensityUtil.dip2px(this,200)).centerCrop().into(image);
        EventBus.getDefault().post(Event.IMAGE_LOADER_SUCCESS);
    }

    @Override
    public void onEventMainThread(Event event) {
        super.onEventMainThread(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
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
