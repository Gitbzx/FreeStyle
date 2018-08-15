package com.bzx.vmovie.microfilm.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.adapters.GuideVPAdapter;
import com.bzx.vmovie.microfilm.base.BaseActivity;
import com.bzx.vmovie.microfilm.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:导航页面
 * Created by bzx on 2018/8/13/013
 * Email:seeyou_x@126.com
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "FreeStyle_GuideActivity";
    private ViewPager mViewPager;
    private GuideVPAdapter mAdapter;

    private static  int[] imgs = {R.mipmap.guide_bg1,R.mipmap.guide_bg2, R.mipmap.guide_bg3};
    private ArrayList<ImageView> imageViews;

    private List<Fragment> mDataList;
    private RadioGroup mIndicator;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //页面正在滑动
        LogUtil.d(TAG, "onPageScrolled: position:" + position + "    positionOffset:" +
                positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        //页面选中
        LogUtil.d(TAG, "onPageSelected: position:" + position);
        RadioButton childAt = (RadioButton) mIndicator.getChildAt(position);
        childAt.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //当滚动状态发生改变的时候
        LogUtil.d(TAG, "onPageScrollStateChanged: ");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {

        //设置每一张图片都填充窗口
        ViewPager.LayoutParams mParams = new ViewPager.LayoutParams();
        imageViews = new ArrayList<ImageView>();

        for(int i=0; i<imgs.length; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);//设置布局
            iv.setImageResource(imgs[i]);//为ImageView添加图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//这里也是一个图片的适配
            imageViews.add(iv);
            if (i == imgs.length -1 ){
                //为最后一张图片添加点击事件
                iv.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        Intent toMainActivity = new Intent(GuideActivity.this, MainActivity.class);//跳转到主界面
                        startActivity(toMainActivity);
                        finish();
                        return true;
                    }
                });
            }

        }

        mViewPager = (ViewPager) findViewById(R.id.vp_glide);
        mIndicator = (RadioGroup) findViewById(R.id.rg_page);

        //给ViewPager设置适配器
        mAdapter = new GuideVPAdapter(imageViews);
        mViewPager.setAdapter(mAdapter);

        //监听器
        mViewPager.addOnPageChangeListener(this);

        //设置第一个默认为选中状态
        RadioButton childOne = (RadioButton) mIndicator.getChildAt(0);
        childOne.setChecked(true);

    }

    @Override
    protected void setupView() {

    }
}
