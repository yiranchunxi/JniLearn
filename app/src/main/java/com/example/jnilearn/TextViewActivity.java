package com.example.jnilearn;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.jnilearn.customview.QQStepView;

/**
 * Created by Administrator on 2018/11/30.
 */

public class TextViewActivity extends AppCompatActivity {

    private QQStepView mqqStepView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        mqqStepView=findViewById(R.id.step_view);
        mqqStepView.setStepMax(4000);
        // 属性动画 后面讲的内容
        ValueAnimator valueAnimator= ObjectAnimator.ofFloat(0,3000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep= (float) animation.getAnimatedValue();
                mqqStepView.setCurrentStep((int)currentStep);
            }
        });
        valueAnimator.start();

        mqqStepView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }


}
