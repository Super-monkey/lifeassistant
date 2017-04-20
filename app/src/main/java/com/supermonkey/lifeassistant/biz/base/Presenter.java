package com.supermonkey.lifeassistant.biz.base;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <基础业务类>
 */
public interface Presenter<V> {
    void attachView(V view);

    void detachView(V view);

    String getName();
}
