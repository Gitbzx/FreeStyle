package com.bzx.vmovie.microfilm.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPhotoWindowActivity extends FragmentActivity {

    @BindView(R.id.btn_take_photo)
    TextView btnTakePhoto;
    @BindView(R.id.btn_pick_photo)
    TextView btnPickPhoto;
    @BindView(R.id.select_photo_ll_take)
    LinearLayout selectPhotoLlTake;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.select_photo_layout)
    RelativeLayout selectPhotoLayout;

    /**
     * 获取到的图片路径
     */
    private String picPath;  //图片路径
    private Intent lastIntent = new Intent();
    private Uri photoUri;

    public static final String KEY_PHOTO_PATH = "photo_path";//从Intent获取图片路径的KEY
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;//使用照相机拍照获取图片
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;  // 使用相册中的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo_window);
        ButterKnife.bind(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);// 设置宽度充满屏幕
    }

    @OnClick({R.id.btn_take_photo, R.id.btn_pick_photo, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_take_photo:
                takePhoto();
                break;
            case R.id.btn_pick_photo:
                pickPhoto();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    /***
     *  打开相机拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
        /***
         * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
         * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
         */
        ContentValues values = new ContentValues();
        photoUri = this.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
        this.startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);

    }

    /***
     *  从相册中取图片
     */
    private void pickPhoto() {
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);*/

        Intent intent1 = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent1, SELECT_PIC_BY_PICK_PHOTO);
    }

    /**
     * 选择图片后，获取图片的路径
     */
    public void doPhoto(int requestCode, Intent data) {
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {// 从相册取图片，有些手机有异常情况，请注意
            if (data == null) {
                Toast.makeText(getApplicationContext(), "选择图片文件出错",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错",  Toast.LENGTH_SHORT).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            cursor.close();
        }
        if (picPath != null
                && (picPath.endsWith(".png") || picPath.endsWith(".PNG")
                || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
            lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
            setResult(Activity.RESULT_OK, lastIntent);
            finish();
        } else {
            Toast.makeText(this, "文件出错或不支持此格式文件",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        doPhoto(requestCode,data);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
