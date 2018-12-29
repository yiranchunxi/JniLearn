package com.example.jnilearn.event;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/12/25.
 */

public class TouchConstrainLayout extends ConstraintLayout {

    private static final String TAG="TouchConstrainLayout";

    public TouchConstrainLayout(Context context) {
        super(context);
    }

    public TouchConstrainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchConstrainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,"dispatchTouchEvent"+ev.getAction());
        return super.dispatchTouchEvent(ev);
       // return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG,"onInterceptTouchEvent"+ev.getAction());
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent"+event.getAction());
        return super.onTouchEvent(event);
        //return false;
        //return true;
    }
}
