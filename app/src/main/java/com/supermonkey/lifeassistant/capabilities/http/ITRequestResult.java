package com.supermonkey.lifeassistant.capabilities.http;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <功能详细描述>
 */
public interface ITRequestResult<T> {

    public void onSuccessful(T entity);

    public void onFailure(String errorMsg);

    public void onCompleted();
}
