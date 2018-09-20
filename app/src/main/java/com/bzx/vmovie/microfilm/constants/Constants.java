package com.bzx.vmovie.microfilm.constants;

import static java.lang.System.currentTimeMillis;

/**
 * Describe:
 * Created by bzx on 2018/9/7/007
 * Email:seeyou_x@126.com
 */

public class Constants {

    public static final String GESTURE_PASSWORD = "GesturePassword";

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
