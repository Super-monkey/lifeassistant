
package com.supermonkey.lifeassistant.bridge;

import android.content.Context;

/**
 * @author supermonkey
 * @version 1.0
 * @date 创建时间：2017/4/20
 * @Description 如果Bridge层的生命周期和App的生命周期相关（在Application
 * onCreate的时候初始化，在用户双击back键退出），则实现此接口，届时统一初始化和销毁
 */
public interface BridgeLifeCycleListener {
    public void initOnApplicationCreate(Context context);

    public void clearOnApplicationQuit();
}
