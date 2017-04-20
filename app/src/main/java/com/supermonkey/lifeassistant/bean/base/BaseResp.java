package com.supermonkey.lifeassistant.bean.base;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <网络请求返回体>
 */
public class BaseResp
{
    /**
     * 返回状态码
     */
    protected int retcode;

    /**
     * 返回信息描述
     */
    protected String retinfo;

    public int getRetcode()
    {
        return retcode;
    }

    public void setRetcode(int retcode)
    {
        this.retcode = retcode;
    }

    public String getRetinfo()
    {
        return retinfo;
    }

    public void setRetinfo(String retinfo)
    {
        this.retinfo = retinfo;
    }
}
