package com.bzx.vmovie.microfilm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Descibe:activity基类
 * Created by bzx on 2018/7/13/013.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
        setupView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void setupView();
}
