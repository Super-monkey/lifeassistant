package com.supermonkey.lifeassistant.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.supermonkey.lifeassistant.EBApplication;
import com.supermonkey.lifeassistant.R;
import com.supermonkey.lifeassistant.biz.base.BasePresenter;
import com.supermonkey.lifeassistant.biz.base.IMvpView;
import com.supermonkey.lifeassistant.bridge.BridgeFactory;
import com.supermonkey.lifeassistant.bridge.Bridges;
import com.supermonkey.lifeassistant.bridge.http.OkHttpManager;
import com.supermonkey.lifeassistant.constant.Event;

import de.greenrobot.event.EventBus;


/**
 * @author supermonkey
 * @version 1.0
 * @date 创建时间：2017/4/20
 * @Description <基础activity>
 */
public abstract class BaseActivity extends Activity implements CreateInit, PublishActivityCallBack, PresentationLayerFunc, IMvpView, OnClickListener {

    private PresentationLayerFuncHelper presentationLayerFuncHelper;

    /**
     * 返回按钮
     */
    //private LinearLayout back;

    /**
     * 标题，右边字符
     */
    protected TextView title;
    /**
     * 菜单按钮
     */
    protected ImageButton menu;
    /**
     * 设置按钮
     */
    protected ImageButton setting;

    public BasePresenter presenter;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentationLayerFuncHelper = new PresentationLayerFuncHelper(this);

        setHeader();
        initData();
        initViews();
        initListeners();
        EBApplication.ebApplication.addActivity(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setHeader() {
        //back = (LinearLayout) findViewById(R.id.ll_back);
        title = (TextView) findViewById(R.id.top_bar_title);
        menu = (ImageButton) findViewById(R.id.m_toggle);
        setting = (ImageButton) findViewById(R.id.m_setting);
    }

    @Override
    public void onClick(View v) {
    }

    public void onEventMainThread(Event event) {

    }

    @Override
    protected void onResume() {
        EBApplication.ebApplication.currentActivityName = this.getClass().getName();
        super.onResume();
    }

    @Override
    public void startActivity(Class<?> openClass, Bundle bundle) {
        Intent intent = new Intent(this, openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openActivityForResult(Class<?> openClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void setResultOk(Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) ;
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showToast(String msg) {
        presentationLayerFuncHelper.showToast(msg);
    }

    @Override
    public void showProgressDialog() {
        presentationLayerFuncHelper.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        presentationLayerFuncHelper.hideProgressDialog();
    }

    @Override
    public void showSoftKeyboard(View focusView) {
        presentationLayerFuncHelper.showSoftKeyboard(focusView);
    }

    @Override
    public void hideSoftKeyboard() {
        presentationLayerFuncHelper.hideSoftKeyboard();
    }

    @Override
    protected void onDestroy() {
        EBApplication.ebApplication.deleteActivity(this);
        EventBus.getDefault().unregister(this);
        if (presenter != null) {
            presenter.detachView(this);
        }
        OkHttpManager httpManager = BridgeFactory.getBridge(Bridges.HTTP);
        httpManager.cancelActivityRequest(TAG);
        super.onDestroy();
    }
}
