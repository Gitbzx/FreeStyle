package com.bzx.vmovie.microfilm.listener;

/**
 * Describe:网络调用回调接口
 * Created by bzx on 2018/8/13/013
 * Email:seeyou_x@126.com
 */

public interface HttpCallbackListener {
    /**
     * 网络数据访问成功回调
     * @param from  由谁发起的调用,用于区别调用者
     * @param response 访问成功返回的数据
     */
    void onFinish(int from, String response);

    /**
     * 在这里对异常情况进行处理
     * @param e
     */
    void onError(Exception e);
}
