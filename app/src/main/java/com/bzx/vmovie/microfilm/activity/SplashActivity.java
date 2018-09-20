package com.bzx.vmovie.microfilm.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.base.BaseActivity;
import com.bzx.vmovie.microfilm.constants.SharedParams;
import com.bzx.vmovie.microfilm.utils.SpUtil;

import java.util.prefs.PreferencesFactory;

public class SplashActivity extends BaseActivity implements Animation.AnimationListener{

    private FrameLayout skipLayout;
    private ImageView mBgImage;
    private TextView mCountDownTextView;
    private MyCountDownTimer mCountDownTimer;
    private Handler tmpHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        skipLayout = (FrameLayout) findViewById(R.id.start_skip);
        mBgImage = (ImageView) findViewById(R.id.iv_splash_bg);
        mCountDownTextView = (TextView) findViewById(R.id.start_skip_count_down);
        skipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTo();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_scale);
        mBgImage.startAnimation(animation);

        animation.setAnimationListener(this);

        mCountDownTextView.setText("跳过3s");
        //创建倒计时类
        mCountDownTimer = new MyCountDownTimer(3000, 1000);
        mCountDownTimer.start();
        //这是一个 Handler 里面的逻辑是从 Splash 界面跳转到 Main 界面，这里的逻辑每个公司基本上一致
//        tmpHandler.postDelayed(runnable, 3000);
    }

    @Override
    protected void setupView() {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束时做页面跳转
        skipTo();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture  表示以「 毫秒 」为单位倒计时的总数
         *      例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval  表示 间隔 多少微秒 调用一次 onTick()
         *      例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        public void onFinish() {
            mCountDownTextView.setText("跳过0s");
        }
        public void onTick(long millisUntilFinished) {
            mCountDownTextView.setText("跳过"+millisUntilFinished / 1000+"s");
        }
    }

    public void skipTo(){
        //决定需要跳转到导航页还是主界面   需要使用数据持久化技术
        boolean isFirstCome = SpUtil.getBoolan(this, SharedParams.FIRST_COME, true);
        Intent intent = new Intent();
        if (isFirstCome) {
            //第一次用,进入导航界面
            intent.setClass(this, GuideActivity.class);
            //设置isFirstCome  SP中的值为false
            SpUtil.putBoolean(this, SharedParams.FIRST_COME, false);
        } else {
            //进入主界面
            intent.setClass(this, MainActivity.class);
        }

        startActivity(intent);

        //必须结束这个页面,不能回退到欢迎页
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
