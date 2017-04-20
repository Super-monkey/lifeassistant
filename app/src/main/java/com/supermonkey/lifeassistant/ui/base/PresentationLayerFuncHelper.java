package com.supermonkey.lifeassistant.ui.base;

import android.content.Context;
import android.view.View;

import com.supermonkey.lifeassistant.util.ToastUtil;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <页面基础公共功能实现>
 */
public class PresentationLayerFuncHelper implements PresentationLayerFunc {

    private Context context;

    public PresentationLayerFuncHelper(Context context) {
        this.context = context;
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.makeText(context, msg);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showSoftKeyboard(View focusView) {

    }

    @Override
    public void hideSoftKeyboard() {

    }
}
