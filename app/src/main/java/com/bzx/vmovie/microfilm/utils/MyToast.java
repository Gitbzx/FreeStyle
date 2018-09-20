package com.bzx.vmovie.microfilm.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Describe:自定义Toast工具类
 * Created by bzx on 2018/8/21/021
 * Email:seeyou_x@126.com
 */

public class MyToast {
    static Toast toast;

    /**
     * 定义的一个Toast,只显示一次
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        if (context == null || message == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.getView().setTag("" + message);
        } else {
            if (!toast.getView().getTag().toString().equals(message)) {
                toast.setText(message);
                toast.getView().setTag("" + message);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
        }
        toast.show();

    }
}
