package com.supermonkey.lifeassistant.bridge.cache.sharePref;

import android.content.Context;

import com.supermonkey.lifeassistant.capabilities.cache.BaseSharedPreference;

/**
 * @author supermonkey
 * @version 1.0
 * @date 创建时间：2017/4/20
 * @Description <设置信息缓存>
 */
public class EBSharedPrefSetting extends BaseSharedPreference {
    /**
     * 声音提醒 默认已开启
     */
    public static final String SOUND_REMINDER = "sound_reminder";

    /**
     * 震动提醒 默认已开启
     */
    public static final String VIBRATION_REMINDER = "vibration_reminder";

    public EBSharedPrefSetting(Context context, String fileName) {
        super(context, fileName);
    }
}
