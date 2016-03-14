package com.nnero.nnero.views.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.nnero.nnero.R;
import com.nnero.nnero.util.BitmapUtil;


/**
 * Created by nnero on 16/3/10.
 *
 * 转圈的view  不能直接new  必须xml配置使用
 */
public class ProgressRoundView extends View implements LoadingView {

    private static final int CORNER_RADIUS = 20;

    private int mRadius;
    private Drawable mImageSrc;
    private int mBgColor;
    private int mMainColor;
    private int mDuration;

    private int progress; //进度 每进度10度   一共满进度就是360度

    private Handler mHandler;
    private RefreshTask mTask;
    private boolean flag;//判断 正转颜色 还是反转颜色

    private Paint mRectPaint;
    private Paint mBgCirclePaint;
    private Paint mMainArcPaint;

    public ProgressRoundView(Context context) {
        super(context);
        init();
    }

    public ProgressRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ProgressRoundView,
                0, 0);
        mRadius = (int) a.getDimension(R.styleable.ProgressRoundView_radius, 20);
        mBgColor = a.getColor(R.styleable.ProgressRoundView_bgColor, 0xff707070);
        mMainColor = a.getColor(R.styleable.ProgressRoundView_mainColor, 0xaa00ff);
        mDuration = a.getInteger(R.styleable.ProgressRoundView_duration, 500);
        mImageSrc = a.getDrawable(R.styleable.ProgressRoundView_imageSrc);
        a.recycle();
        init();
    }

    private void init(){
        flag = true;
        mHandler = new Handler();
        mTask = new RefreshTask();
        progress = 1;

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);//抗锯齿
        mRectPaint.setColor(0xffffffff);
        mRectPaint.setStyle(Paint.Style.FILL);//填充类型

        mBgCirclePaint = new Paint();
        mBgCirclePaint.setAntiAlias(true);
        mBgCirclePaint.setColor(mBgColor);
        mBgCirclePaint.setStrokeWidth(10);
        mBgCirclePaint.setStyle(Paint.Style.STROKE);//线类型 类似描边

        mMainArcPaint = new Paint();
        mMainArcPaint.setAntiAlias(true);
        mMainArcPaint.setColor(mMainColor);
        mMainArcPaint.setStrokeWidth(10);
        mMainArcPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mRadius*3,mRadius*3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0,0,mRadius*3,mRadius*3,CORNER_RADIUS,CORNER_RADIUS,mRectPaint); //画背景圆角矩形
        canvas.drawBitmap(BitmapUtil.convertDrawableToBitmap(mImageSrc), (int)(mRadius*0.5), (int)(mRadius*0.5), null);//画图标
        canvas.drawCircle((int)(mRadius*1.5), (int)(mRadius*1.5), mRadius, mBgCirclePaint); //画背景圆圈
        canvas.drawArc((int)(mRadius*0.5) , (int)(mRadius*0.5) , (int)(mRadius*2.5), (int)(mRadius*2.5), 270, 10*progress, false, mMainArcPaint);//画圆弧
        inValidateNext(); //不断重绘
    }

    private void inValidateNext(){
        mHandler.postDelayed(mTask,mDuration/36);
    }

    private class RefreshTask implements Runnable{
        @Override
        public void run() {
            progress++;
            if(progress == 37) {
                progress = 0;
                if(flag) {
                    mBgCirclePaint.setColor(mMainColor);
                    mMainArcPaint.setColor(mBgColor);
                    flag = false;
                } else {
                    mBgCirclePaint.setColor(mBgColor);
                    mMainArcPaint.setColor(mMainColor);
                    flag = true;
                }
            }
            postInvalidate();
        }
    }

    @Override
    public void hide(){
        mHandler.removeCallbacksAndMessages(0);
        this.setVisibility(View.GONE);
    }

    @Override
    public void show(){
        this.setVisibility(View.VISIBLE);
        inValidateNext();
    }
}
