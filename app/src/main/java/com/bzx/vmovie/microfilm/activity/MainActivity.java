package com.bzx.vmovie.microfilm.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.adapters.GuideAdapter;
import com.bzx.vmovie.microfilm.base.BaseActivity;
import com.bzx.vmovie.microfilm.constants.Constants;
import com.bzx.vmovie.microfilm.fragment.AttentionFragment;
import com.bzx.vmovie.microfilm.fragment.DiscoveryFragment;
import com.bzx.vmovie.microfilm.fragment.HomeFragment;
import com.bzx.vmovie.microfilm.fragment.ProfileFragment;
import com.bzx.vmovie.microfilm.utils.ACache;
import com.bzx.vmovie.microfilm.utils.MyToast;
import com.bzx.vmovie.microfilm.view.NoScrollViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.main_vp)
    NoScrollViewPager mainVp;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.nv_menu_left)
    NavigationView nvMenuLeft;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private LinearLayout headerView;
    private ImageView iconImg;

    private MenuItem menuItem;
    private GuideAdapter mAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    private ACache aCache;

    private long mExitTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new DiscoveryFragment().newInstance("", ""));
        fragmentList.add(new AttentionFragment().newInstance("", ""));
        fragmentList.add(new ProfileFragment().newInstance("", ""));
        mAdapter = new GuideAdapter(getSupportFragmentManager(), fragmentList);
        mainVp.setAdapter(mAdapter);

        //监听器
        mainVp.addOnPageChangeListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //关闭BottomNavigationView的滑动动画
        disableShiftMode(bottomNavigationView);

        headerView = (LinearLayout) nvMenuLeft.getHeaderView(0);
        iconImg = (ImageView) headerView.findViewById(R.id.icon_person);
        iconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToast.showToast(MainActivity.this,"登录！");
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        aCache = ACache.get(this);

        nvMenuLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()){
                    case R.id.nav1:
                        MyToast.showToast(MainActivity.this,"1");
                        break;
                    case R.id.nav2:
                        MyToast.showToast(MainActivity.this,"2");
                        break;
                    case R.id.nav3:
                        MyToast.showToast(MainActivity.this,"3");
                        break;
                    case R.id.nav4:
                        MyToast.showToast(MainActivity.this,"4");
                        break;
                    case R.id.nav5:
                        MyToast.showToast(MainActivity.this,"5");
                        break;
                    case R.id.nav6:
                        String gesturePassword = aCache.getAsString(Constants.GESTURE_PASSWORD);
                        if(gesturePassword == null || "".equals(gesturePassword)) {
                            Intent intent = new Intent(MainActivity.this, CreateGestureActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, GestureLoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.nav7:
                        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void setupView() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(drawerLayout.isDrawerOpen(nvMenuLeft)){
                drawerLayout.closeDrawers();
            }else {
                exit();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit(){
        if(System.currentTimeMillis() - mExitTime > 2000){
            MyToast.showToast(this,"再按一次退出FreeStyle");
            mExitTime = System.currentTimeMillis();
        }else {
            System.exit(0);
        }
    }

    /**
     * 通过反射机制关闭BottomNavigationView的滑动效果
     * @param view
     */
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
