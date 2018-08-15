package com.bzx.vmovie.microfilm.utils;

import android.util.Log;

/**
 * Describe:
 * Created by bzx on 2018/8/13/013
 * Email:seeyou_x@126.com
 */

public class LogUtil {
    private static final boolean DEBUG_MODEL = true;

    public static final void e(String tag, String msg) {
        if (DEBUG_MODEL) Log.e(tag, msg);
    }

    public static final void i(String tag, String msg) {
        if (DEBUG_MODEL) Log.i(tag, msg);
    }

    public static final void w(String tag, String msg) {
        if (DEBUG_MODEL) Log.i(tag, msg);
    }

    public static final void v(String tag, String msg) {
        if (DEBUG_MODEL) Log.v(tag, msg);
    }

    public static final void d(String tag, String msg) {
        if (DEBUG_MODEL) Log.d(tag, msg);
    }

    public static final void systemOut(String msg) {
    }

    public static final void systemErr(String msg) {
    }

    public static final void printStackTrace(Exception e, String Tag) {

    }
}
