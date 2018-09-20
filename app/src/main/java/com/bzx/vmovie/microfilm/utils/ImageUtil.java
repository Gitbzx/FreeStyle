package com.bzx.vmovie.microfilm.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextPaint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Describe:图片处理类
 * Created by bzx on 2018/9/19/019
 * Email:seeyou_x@126.com
 */

public class ImageUtil {

    /**
     * 添加水印
     * @param context 上下文环境
     * @param target 需要添加水印的图片
     * @param mPhone 手机型号
     * @param describe  自定义描述
     * @return
     */
    public static Bitmap createWatermarkText(Context context, Bitmap target, String mPhone, String describe){
        int width=target.getWidth();
        int height=target.getHeight();
        Bitmap bitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);

        TextPaint paint = new TextPaint();
        //设置水印字体颜色
        paint.setColor(Color.WHITE);
        //设置水印字体大小
        paint.setTextSize(dip2px(context,5));
        //设置防锯齿
        paint.setAntiAlias(true);
        //设置字体样式
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        paint.setTypeface(font);
        //添加原图内容
        canvas.drawBitmap(target,0,0,paint);

        //左下方加水印
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("SHOT ON " + mPhone, dip2px(context,33), height-dip2px(context,18), paint);
        canvas.drawText(describe, dip2px(context,33), height-dip2px(context,12), paint);

        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        // 描边
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(dip2px(context,25),height-dip2px(context,17),dip2px(context,5),mPaint);
        // 描边加填充(填充:Paint.Style.FILL)
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(dip2px(context,12),height-dip2px(context,17),dip2px(context,5),mPaint);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }


    /**
     * 根据手机分辨率从DP转成PX
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    // 根据路径获得图片
    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置为ture,只读取图片的大小，不把它加载到内存中去
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);//此处，选取了480x800分辨率的照片 计算缩放值
        options.inJustDecodeBounds = false;//处理完后，同时需要记得设置为false
        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    private static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 压缩图片
     * @param bitMap  要压缩的bitmap对象
     * @param maxSize 压缩的大小(kb)不是很准确大约比输入值大于100k是因为比例决定的
     * @return
     */
    public static Bitmap imageZoom(Bitmap bitMap, double maxSize) {
        if (bitMap != null) {
            //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            //将字节换成KB
            double mid = b.length / 1024;
            //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
            if (mid > maxSize) {
                //获取bitmap大小 是允许最大大小的多少倍
                double i = mid / maxSize;
                //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
                bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                        bitMap.getHeight() / Math.sqrt(i));
            }
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    private static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static void savePic2system(Context mContext,String mFilePath){
        File mPicDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ""); //图片统一保存在系统的图片文件夹中
        OutputStream out = null;
        try {
            mPicDir.mkdirs();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String mPicPath = new File(mPicDir, "Yddw" + df.format(new Date()) + ".jpg").getAbsolutePath();
            ContentValues values = new ContentValues();
            ContentResolver resolver = mContext.getContentResolver();
            values.put(MediaStore.Images.ImageColumns.DATA, mPicPath);
            values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "Yddw" + df.format(new Date()) + ".jpg");
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpg");
            //将图片的拍摄时间设置为当前的时间
            values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis() + "");
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                out = resolver.openOutputStream(uri);
                Bitmap bitmap1 = BitmapFactory.decodeFile(mFilePath);
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
