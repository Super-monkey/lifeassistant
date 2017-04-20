package com.supermonkey.lifeassistant.biz.base;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <功能详细描述>
 */
public interface IMvpView {
    void onError(String errorMsg, String code);

    void onSuccess();

    void showLoading();

    void hideLoading();
}
