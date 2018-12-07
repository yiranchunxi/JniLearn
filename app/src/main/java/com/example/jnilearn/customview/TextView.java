package com.example.jnilearn.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jnilearn.R;

import java.lang.reflect.Field;
import java.security.MessageDigest;

/**
 * Created by Yan on 2018/11/30.
 * 画一个自定义TextView
 */

public class TextView extends LinearLayout {

    private String mText;
    private int mTextSize=15;
    private int mTextColor = Color.BLACK;
    private Paint mPaint;
    //代码中调用
    // TextView tv = new TextView(this);
    public TextView(Context context) {
        this(context,null);
    }
    //xml文件中调用
    /*<com.darren.view_day01.TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World!" />*/
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    //xml并且含有自定义属性时调用
    /**
     <com.darren.view_day01.TextView
     style="@style/defualt"
     android:text="Hello World!"
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取自定义属性
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText=array.getString(R.styleable.TextView_darrenText);
        mTextColor=array.getColor(R.styleable.TextView_darrenTextColor, mTextColor);
        // 15 15px 15sp
        mTextSize=array.getDimensionPixelSize(R.styleable.TextView_darrenTextSize,sp2px(mTextSize));

        // 回收
        array.recycle();

        mPaint=new Paint();

        // 抗锯齿
        mPaint.setAntiAlias(true);

        // 设置字体的大小和颜色
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        //  默认给一个背景
         setBackgroundColor(Color.TRANSPARENT);

        setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 布局的宽高都是由这个方法指定
        // 指定控件的宽高，需要测量
        // 获取宽高的模式
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        // 1.确定的值，这个时候不需要计算，给的多少就是多少
        int width=MeasureSpec.getSize(widthMeasureSpec);
        // 2.给的是wrap_content 需要计算
        if(widthMode==MeasureSpec.AT_MOST){
            // 计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
            Rect bounds=new Rect();
            // 获取文本的Rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width=bounds.width()+getPaddingLeft()+getPaddingRight();
        }


        int height= MeasureSpec.getSize(heightMeasureSpec);

        if(heightMode==MeasureSpec.AT_MOST){

            Rect bounds=new Rect();

            mPaint.getTextBounds(mText,0,mText.length(),bounds);

            height=bounds.height()+getPaddingTop()+getPaddingBottom();
        }
        // 设置控件的宽高
        setMeasuredDimension(width,height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*// 画文本
        canvas.drawText();
        // 画弧
        canvas.drawArc();
        // 画圆
        canvas.drawCircle();*/
        // 画文字 text  x  y  paint


        // 计算dy方式1 dy 代表的是：高度的一半到 baseLine的距离
        Paint.FontMetricsInt fontMetricsInt= mPaint.getFontMetricsInt();

        Log.e("test",fontMetricsInt.toString());

        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
        // 必须要清楚的，可以自己打印就好
        //int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;

        //int baseline=getHeight()/2+dy;

        // 计算dy方式2
       // float baseLineY
        //        = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
       // = -fontMetrics.ascent / 2 - fontMetrics.descent / 2;
      //  = -(fontMetrics.ascent + fontMetrics.descent) / 2;
       // = Math.abs(fontMetrics.ascent + fontMetrics.descent) / 2;

       int dy=Math.abs(fontMetricsInt.ascent+fontMetricsInt.descent)/2;

       int baseline=getHeight()/2+dy;



        int x=getPaddingLeft();

        canvas.drawText(mText,x,baseline,mPaint);
    }

    /**
     * 处理跟用户交互的，手指触摸等等
     * @param event 事件分发事件拦截
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 手指按下
                Log.e("TAG","手指按下");
                break;

            case MotionEvent.ACTION_MOVE:
                // 手指移动
                Log.e("TAG","手指移动");
                break;

            case MotionEvent.ACTION_UP:
                // 手指抬起
                Log.e("TAG","手指抬起");
                break;
        }

        invalidate();

        return super.onTouchEvent(event);
    }



    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,
                getResources().getDisplayMetrics());
    }
}
