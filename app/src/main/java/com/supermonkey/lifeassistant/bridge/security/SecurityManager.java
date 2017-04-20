package com.supermonkey.lifeassistant.bridge.security;

import android.content.Context;

import com.supermonkey.lifeassistant.bridge.BridgeLifeCycleListener;
import com.supermonkey.lifeassistant.capabilities.security.SecurityUtils;

/**
 * @author supermonkey
 * @version 1.0
 * @date 创建时间：2017/4/20
 * @Description <加解密管理类>
 */
public class SecurityManager  implements BridgeLifeCycleListener {
    @Override
    public void initOnApplicationCreate(Context context) {

    }

    @Override
    public void clearOnApplicationQuit() {

    }

    /**
     * md5 加密
     * @param str
     * @return
     */
    public String get32MD5Str(String str){
        return SecurityUtils.get32MD5Str(str);
    }

}
