package com.example.jnilearn.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/12/28.
 */

public class VerticalDragListView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;

    private View mDragView;
    // 后面菜单的高度
    private int mMenuHeight;

    // 菜单是否打开
    private boolean mMenuIsOpen = false;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper=ViewDragHelper.create(this,mCallBack);
    }


    private ViewDragHelper.Callback mCallBack=new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            return mDragView==child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if(top<=0){
                top=0;
            }

            if(top>=mMenuHeight){
                top=mMenuHeight;
            }
            return  top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            if(releasedChild==mDragView){

                if(mDragView.getTop()>mMenuHeight/2){

                    // 滚动到菜单的高度（打开）
                    mViewDragHelper.settleCapturedViewAt(0,mMenuHeight);
                    mMenuIsOpen=true;
                }else{

                    // 滚动到0的位置（关闭）
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                    mMenuIsOpen = false;

                }

                invalidate();
            }

        }
    };
    // 现象就是ListView可以滑动，但是菜单滑动没有效果了
    private float mDownY;

    // ecause ACTION_DOWN was not received for this pointer before ACTION_MOVE
    // VDLV.onInterceptTouchEvent().DOWN -> LV.onTouch() ->
    // VDLV.onInterceptTouchEvent().MOVE -> VDLV.onTouchEvent().MOVE
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 菜单打开要拦截
        if (mMenuIsOpen) {
            return true;
        }
        // 向下滑动拦截，不要给ListView做处理
        // 谁拦截谁 父View拦截子View ，但是子 View 可以调这个方法
        // requestDisallowInterceptTouchEvent 请求父View不要拦截，改变的其实就是 mGroupFlags 的值
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY=ev.getY();
                mViewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:

                float moveY=ev.getY();

                if((moveY-mDownY)>0 && !canChildScrollUp()){
                    // 向下滑动 && 滚动到了顶部，拦截不让ListView做处理
                    return true;
                }

                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount=getChildCount();

        if (childCount != 2) {
            throw new RuntimeException("VerticalDragListView 只能包含两个子布局");
        }

        mDragView=getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            View v=getChildAt(0);
            mMenuHeight=v.getMeasuredHeight();
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragView, -1) || mDragView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragView, -1);
        }
    }
}
