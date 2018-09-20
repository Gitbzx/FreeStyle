package com.bzx.vmovie.microfilm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe:首页fragment
 * Created by bzx on 2018/8/28/028
 * Email:seeyou_x@126.com
 */

public class HomeFragment extends BaseFragment implements OnBannerListener {

    @BindView(R.id.home_fg_banner)
    Banner homeFgBanner;
    Unbinder unbinder;

    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void getRootView(View v) {
        unbinder = ButterKnife.bind(this, v);
    }

    @Override
    protected void initView() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        /**
         * NOT_INDICATOR、CIRCLE_INDICATOR、NUM_INDICATOR、NUM_INDICATOR_TITLE、CIRCLE_INDICATOR_TITLE、CIRCLE_INDICATOR_TITLE_INSIDE
         */
        homeFgBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器，图片加载器在下方
        homeFgBanner.setImageLoader(new HomeFragment.MyLoader());
        //设置图片网址或地址的集合
        homeFgBanner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        /**
         * Default、Accordion、BackgroundToForeground、ForegroundToBackground、CubeIn
         * CubeOut、DepthPage、FlipHorizontal、FlipVertical、RotateDown、RotateUp、ScaleInOut
         * Stack、Tablet、ZoomIn、ZoomOut、ZoomOutSlide
         */
        homeFgBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置轮播图的标题集合
        homeFgBanner.setBannerTitles(list_title);
        //设置轮播间隔时间
        homeFgBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        homeFgBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        homeFgBanner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getContext(),"点击了"+position, Toast.LENGTH_SHORT).show();
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}

