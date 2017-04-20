package com.supermonkey.lifeassistant.biz.base;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <基础业务类>
 */
public abstract class BasePresenter<V extends IMvpView> implements Presenter<V> {
    protected V mvpView;

    public void attachView(V view) {
        mvpView = view;
    }

    @Override
    public void detachView(V view) {
        mvpView = null;
    }

    @Override
    public String getName() {
        return mvpView.getClass().getSimpleName();
    }
}
