package com.supermonkey.lifeassistant.ui.base;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <公共方法抽象>
 */
public interface CreateInit {
    /**
     * 初始化布局组件
     */
    public void initViews();

    /**
     * 增加按钮点击事件
     */
    void initListeners();

    /**
     * 初始化数据
     */
    public void initData();


    /**
     * 初始化公共头部
     */
    public void setHeader();
}

