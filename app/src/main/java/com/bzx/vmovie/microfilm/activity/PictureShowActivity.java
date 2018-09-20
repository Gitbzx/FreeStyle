package com.bzx.vmovie.microfilm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzx.vmovie.microfilm.R;

public class PictureShowActivity extends AppCompatActivity {

    private ImageView showImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_show);
        showImg = (ImageView) findViewById(R.id.img_show);
        Glide.with(this).load(getIntent().getExtras().getString("pictureUri")).into(showImg);//设置
    }
}
