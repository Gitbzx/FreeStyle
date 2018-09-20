package com.bzx.vmovie.microfilm.constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.constants.callback.PopCallBack;

/**
 * Describe:共用的Dialog弹框
 * Created by bzx on 2018/9/18/018
 * Email:seeyou_x@126.com
 */

public class PopDialog<T> {

    private static AlertDialog tDialog;

    /**
     * @param context
     * @param content
     * @param maxLine
     * @param title
     * @param callBack
     */
    public static void showDialog(Context context, String content, int maxLine, String title, final PopCallBack callBack) {
        try {
            if (context == null) {
                return;
            }
            if (context instanceof Activity == false) {  //都不能强制类型转换的,还判断什么是否已结束
                return;
            }
            if (((Activity) context).isFinishing()) {
                return;
            }

            final AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            dialog.show();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            window.setContentView(R.layout.power_off_dialog);
            TextView title_tv = (TextView) window.findViewById(R.id.tvtitle);
            TextView tvcontent = (TextView) window.findViewById(R.id.tvcontent);
            Button tvtc = (Button) window.findViewById(R.id.tvtc);
            if (title != null) {
                title_tv.setText(title);
            }
            tvtc.setText("确定");
            tvcontent.setMaxLines(maxLine);
            tvcontent.setText(content);
            tvcontent.setMovementMethod(ScrollingMovementMethod.getInstance());
            tvtc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (callBack != null) {
                        callBack.ok();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param context
     * @param content
     * @param maxLine
     * @param title
     * @param callBack
     */
    public static void show2BtnDialog(Context context, String content, int maxLine, String title,String sureStr, String noStr, final PopCallBack callBack) {
        if(tDialog != null){
            tDialog.dismiss();
        }
        try {
            if (context == null) {
                return;
            }
            if (context instanceof Activity == false) {  //都不能强制类型转换的,还判断什么是否已结束
                return;
            }
            if (((Activity) context).isFinishing()) {
                return;
            }

            tDialog = new AlertDialog.Builder(context).create();
            tDialog.setCancelable(false);
            Window window = tDialog.getWindow();
            tDialog.show();
            window.setContentView(R.layout.power_off_dialog2);
            TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
            TextView title_tv = (TextView) window.findViewById(R.id.tvtitle);
            TextView tvcontent = (TextView) window.findViewById(R.id.tvcontent);
            TextView tvtc = (TextView) window.findViewById(R.id.tv_yes);
            if (title != null) {
                title_tv.setText(title);
            }
            if(sureStr == null){
                sureStr = "确定";
            }
            if(noStr == null){
                noStr = "取消";
            }
            tvtc.setText(sureStr);
            tv_cancel.setText(noStr);
            tvcontent.setMaxLines(maxLine);
            tvcontent.setText(content);
            tvcontent.setMovementMethod(ScrollingMovementMethod.getInstance());
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tDialog.dismiss();
                    if (callBack != null) {
                        callBack.no();
                    }
                }
            });
            tvtc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    tDialog.dismiss();
                    if (callBack != null) {
                        callBack.ok();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
