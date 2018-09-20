package com.bzx.vmovie.microfilm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.adapters.PhotoGridViewAdapter;
import com.bzx.vmovie.microfilm.constants.PopDialog;
import com.bzx.vmovie.microfilm.utils.FileUtil;
import com.bzx.vmovie.microfilm.utils.ImageUtil;
import com.bzx.vmovie.microfilm.utils.MyToast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity {

    @BindView(R.id.grid_photo)
    GridView gridPhoto;

    private PhotoGridViewAdapter photoGridViewAdapter;
    public final static int REQUEST_READ_PHONE_STATE = 1;

    private Context mContext;

    private List<String> photoPathlist = new ArrayList<>();
    private List<String> tempPathlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        mContext = this;

        init();
    }

    private void init() {
        photoGridViewAdapter = new PhotoGridViewAdapter(this,photoPathlist);
        gridPhoto.setAdapter(photoGridViewAdapter);
        gridPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((PhotoActivity) mContext, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                    return;
                }
                if(!FileUtil.isCameraPermission()){
                    PopDialog.showDialog(mContext, "相机打开失败，请打开相机权限：设置-应用(应用管理)-移动代维-权限，开启相机权限", 5, null, null);
                    return;
                }
                if(!FileUtil.isGrantExternalRW((PhotoActivity)mContext)){
                    PopDialog.showDialog(mContext, "请先开启存储空间权限！", 5, null, null);
                    return;
                }
                //判断是否是最后一个。
                if (position==parent.getChildCount()-1){
                    if (position==6){//不能点击了
                        MyToast.showToast(mContext,"最多选择六张图片");
                    }else{
                        Intent intent = new Intent(PhotoActivity.this,SelectPhotoWindowActivity.class);
                        ((PhotoActivity) mContext).startActivityForResult(intent,100);
                    }
                }else {
                    Intent intent_pic = new Intent(mContext, PictureShowActivity.class);
                    intent_pic.putExtra("pictureUri",photoPathlist.get(position));
                    mContext.startActivity(intent_pic);

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode==100){
                String mFilePath = data.getStringExtra(SelectPhotoWindowActivity.KEY_PHOTO_PATH);
                Bitmap bitmap = ImageUtil.getSmallBitmap(mFilePath); //有路径获取 bitmap
                bitmap = ImageUtil.imageZoom(bitmap,160); //压缩至 xx kb
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (!mFilePath.contains("Libra")){//判断是否已经添加过水印
                    bitmap = ImageUtil.createWatermarkText(mContext,bitmap,"IPHONE XS MAX","AI DUAL CAMER");
                }

                try {// 保存图片
                    mFilePath = Environment.getExternalStorageDirectory().getPath()  + "/" + "Libra" + df.format(new Date()) + ".png";
                    File file = new File(mFilePath);
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                ImageUtil.savePic2system(mContext,mFilePath);
                if (photoPathlist.size()>=6){
                    MyToast.showToast(mContext,"最多选择六张图片");
                }else{
                    tempPathlist.clear();
                    tempPathlist.add(String.valueOf(mFilePath));
                    photoPathlist.addAll(tempPathlist);
                }
                photoGridViewAdapter.notifyDataSetChanged();
            }
        }
    }
}
