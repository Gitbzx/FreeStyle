package com.bzx.vmovie.microfilm.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Describe:引导页面ViewPager适配器
 * Created by bzx on 2018/8/13/013
 * Email:seeyou_x@126.com
 */

public class GuideAdapter extends FragmentPagerAdapter{

    private List<Fragment> dataList;

    public GuideAdapter(FragmentManager fm,List<Fragment> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        if (dataList != null && dataList.size() > position) {
            return dataList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
