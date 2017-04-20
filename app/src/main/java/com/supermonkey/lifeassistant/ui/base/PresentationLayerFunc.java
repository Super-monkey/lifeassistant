package com.supermonkey.lifeassistant.ui.base;

import android.view.View;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <页面基础公共功能抽象>
 */
public interface PresentationLayerFunc {
    /**
     * 弹出消息
     *
     * @param msg
     */
    public void showToast(String msg);

    /**
     * 网络请求加载框
     */
    public void showProgressDialog();

    /**
     * 隐藏网络请求加载框
     */
    public void hideProgressDialog();

    /**
     * 显示软键盘
     *
     * @param focusView
     */
    public void showSoftKeyboard(View focusView);

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard();
}
