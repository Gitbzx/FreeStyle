package com.bzx.vmovie.microfilm.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Describe:
 * Created by bzx on 2018/8/13/013
 * Email:seeyou_x@126.com
 */

public class GuideVPAdapter extends PagerAdapter {

    private ArrayList<ImageView> imageViews;

    public GuideVPAdapter(ArrayList<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 从ViewGroup中移除当前对象（图片）
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }

    /**
     * 当前要显示的对象（图片）
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }
}
