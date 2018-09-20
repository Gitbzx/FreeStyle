package com.bzx.vmovie.microfilm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bzx.vmovie.microfilm.R;

/**
 * Describe:
 * Created by bzx on 2018/8/30/030
 * Email:seeyou_x@126.com
 */

public class LockPatternView2 extends View{

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //9个点
    private Point[][] points = new Point[3][3];

    private boolean isInit;

    private float width;
    private float height;
    private float offsetsX;
    private float offsetsY;
    private float bitmapR;

    private Bitmap pointsNormal,pointsPressed,pointsError,linePressed,lineError;

    public LockPatternView2(Context context) {
        super(context);
    }

    public LockPatternView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LockPatternView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInit){
            initPoints();
        }
        points2Canvas(canvas);

    }

    /**
     * 将点绘制到画布上
     * @param canvas
     */
    private void points2Canvas(Canvas canvas) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = points[i][j];
                if(point.state==Point.STATE_PRESSED){
                    canvas.drawBitmap(pointsPressed,point.x-bitmapR,point.y-bitmapR,paint);
                }else if(point.state==Point.STATE_NORMAL){
                    canvas.drawBitmap(pointsNormal,point.x-bitmapR,point.y-bitmapR,paint);
                }else if(point.state==Point.STATE_ERROR){
                    canvas.drawBitmap(pointsError,point.x-bitmapR,point.y-bitmapR,paint);
                }

            }
            
        }

    }

    /**
     * 初始化点
     */
    private void initPoints() {
        //1、获取布局的宽、高
        width = getWidth();
        height = getHeight();

        if(width>height){//横屏
            offsetsX = (width - height) / 2;
            width = height;
        }else {//竖屏
            offsetsY = (height - width) / 2;
            height = width;
        }

        pointsNormal = BitmapFactory.decodeResource(getResources(), R.mipmap.dot);
        pointsPressed = BitmapFactory.decodeResource(getResources(), R.mipmap.dot);
        pointsError = BitmapFactory.decodeResource(getResources(), R.mipmap.dot);
        linePressed = BitmapFactory.decodeResource(getResources(), R.mipmap.dot);
        lineError = BitmapFactory.decodeResource(getResources(), R.mipmap.dot);

        points[0][0] = new Point(offsetsX + width/4,offsetsY+width/4);
        points[0][1] = new Point(offsetsX + width/2,offsetsY+width/4);
        points[0][2] = new Point(offsetsX + width-width/4,offsetsY+width/4);

        points[1][0] = new Point(offsetsX + width/4,offsetsY + width/2);
        points[1][1] = new Point(offsetsX + width/2,offsetsY + width/2);
        points[1][2] = new Point(offsetsX + width-width/4,offsetsY + width/2);

        points[2][0] = new Point(offsetsX + width/4,offsetsY + width-width/4);
        points[2][1] = new Point(offsetsX + width/2,offsetsY + width-width/4);
        points[2][2] = new Point(offsetsX + width-width/4,offsetsY + width-width/4);

        bitmapR = pointsNormal.getWidth()/2;
    }

    /**
     * 自定义的点
     */
    public static class Point{
        //正常
        public static int STATE_NORMAL = 0;
        //选中
        public static int STATE_PRESSED = 1;
        //错误
        public static int STATE_ERROR = 2;

        public float x,y;
        public int index = 0, state = 0;

        public Point() {
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
