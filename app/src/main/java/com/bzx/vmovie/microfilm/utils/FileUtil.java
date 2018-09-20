package com.bzx.vmovie.microfilm.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;

/**
 * Describe:文件工具类
 * Created by bzx on 2018/9/18/018
 * Email:seeyou_x@126.com
 */

public class FileUtil {

    /**
     * 作用：用户是否同意打开相机权限
     *
     * @return true 同意 false 拒绝
     */
    public static boolean isCameraPermission() {

        try {
            Camera.open().release();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }
}
