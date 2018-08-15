package com.bzx.vmovie.microfilm.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.bzx.vmovie.microfilm.utils.HttpUtil;
import com.bzx.vmovie.microfilm.utils.LogUtil;

/**
 * Describe:
 * Created by bzx on 2018/8/13/013
 * Email:seeyou_x@126.com
 */

public class VMovieApplication extends Application implements Application.ActivityLifecycleCallbacks {

    /**
     * 一个全局的Context
     */
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //监测Activity的生命周期事件
        this.registerActivityLifecycleCallbacks(this);

        /*----------在这里初始化各种框架-----------*/
        HttpUtil.initOkHttp();
    }

    /**
     * 获取一个全局的Context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.v(activity.getLocalClassName(), "onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.v(activity.getLocalClassName(), "onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.v(activity.getLocalClassName(), "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.v(activity.getLocalClassName(), "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.v(activity.getLocalClassName(), "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LogUtil.v(activity.getLocalClassName(), "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.v(activity.getLocalClassName(), "onActivityDestroyed");
    }
}
