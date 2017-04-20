package com.supermonkey.lifeassistant.util;

import android.content.Context;
import android.widget.Toast;

import com.supermonkey.lifeassistant.EBApplication;
import com.supermonkey.lifeassistant.constant.Constants;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <提示公共类>
 */
public final class ToastUtil {
    private static Toast toast;

    /**
     * <显示toast提示>
     *
     * @param context
     * @param msg
     * @see [类、类#方法、类#成员]
     */
    public static void makeText(Context context, String msg) {
        if (EBApplication.currentActivityName.equals(context.getClass().getName())) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * <显示toast提示>
     *
     * @param context
     * @param msg
     * @see [类、类#方法、类#成员]
     */
    public static void makeTextLong(Context context, String msg) {
        if (EBApplication.currentActivityName.equals(context.getClass().getName())) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * <显示失败信息 >
     *
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public static void makeTextError(Context context) {
        makeText(context, Constants.ERROR_MESSAGE);
    }

    /**
     * <显示失败信息 >
     *
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public static void makeTextErrorLong(Context context) {
        makeTextLong(context, Constants.ERROR_MESSAGE);
    }

    public static void destory() {
        toast = null;
    }
}
