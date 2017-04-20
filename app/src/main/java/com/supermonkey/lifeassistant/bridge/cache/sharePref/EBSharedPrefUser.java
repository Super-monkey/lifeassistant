package com.supermonkey.lifeassistant.bridge.cache.sharePref;

import android.content.Context;

import com.supermonkey.lifeassistant.capabilities.cache.BaseSharedPreference;

/**
 * @author supermonkey
 * @version 1.0
 * @date 创建时间：2017/4/20
 * @Description <用户信息缓存>
 */
public class EBSharedPrefUser extends BaseSharedPreference {
    /**
     * 登录名
     */
    public static final String USER_NAME = "user_name";

    public EBSharedPrefUser(Context context, String fileName) {
        super(context, fileName);
    }
}
