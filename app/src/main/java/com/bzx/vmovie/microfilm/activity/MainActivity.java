package com.bzx.vmovie.microfilm.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.adapters.GuideAdapter;
import com.bzx.vmovie.microfilm.base.BaseActivity;
import com.bzx.vmovie.microfilm.fragment.AttentionFragment;
import com.bzx.vmovie.microfilm.fragment.DiscoveryFragment;
import com.bzx.vmovie.microfilm.fragment.HomeFragment;
import com.bzx.vmovie.microfilm.fragment.ProfileFragment;
import com.bzx.vmovie.microfilm.view.CustomViewPager;
import com.bzx.vmovie.microfilm.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener{

    @BindView(R.id.main_vp)
    NoScrollViewPager mainVp;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    private MenuItem menuItem;
    private GuideAdapter mAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        fragmentList.add(new HomeFragment().newInstance("",""));
        fragmentList.add(new DiscoveryFragment().newInstance("",""));
        fragmentList.add(new AttentionFragment().newInstance("",""));
        fragmentList.add(new ProfileFragment().newInstance("",""));
        mAdapter = new GuideAdapter(getSupportFragmentManager(),fragmentList);
        mainVp.setAdapter(mAdapter);

        //监听器
        mainVp.addOnPageChangeListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void setupView() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.tab_menu_home:
                mainVp.setCurrentItem(0);
                break;
            case R.id.tab_menu_discovery:
                mainVp.setCurrentItem(1);
                break;
            case R.id.tab_menu_attention:
                mainVp.setCurrentItem(2);
                break;
            case R.id.tab_menu_profile:
                mainVp.setCurrentItem(3);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        menuItem = bottomNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
