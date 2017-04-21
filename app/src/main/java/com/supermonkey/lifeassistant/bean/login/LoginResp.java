package com.supermonkey.lifeassistant.bean.login;

import com.supermonkey.lifeassistant.bean.base.BaseResp;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <功能详细描述>
 */
public class LoginResp extends BaseResp {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
