package com.bzx.vmovie.microfilm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Describe:基类fragment
 * Created by bzx on 2018/8/28/028
 * Email:seeyou_x@126.com
 */

public abstract class BaseFragment extends Fragment{

    protected View mLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = inflater.inflate(getLayoutId(),container,false);
        initArguments();
        getRootView(mLayout);
        initView();
        setupView();
        return mLayout;
    }


    /**
     * 方便获取传递过来的参数
     */
    protected void initArguments() {

    }

    /**
     * 方便查找id
     * @param id
     * @return
     */
    public View findViewById(int id){
        return mLayout.findViewById(id);
    }

    /**
     * 设置布局数据
     */
    protected void setupView() {

    }

    /**
     * 设置布局的id
     *
     * @return 返回子类布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    protected abstract void getRootView(View v);
}
