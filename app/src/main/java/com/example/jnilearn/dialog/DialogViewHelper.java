package com.example.jnilearn.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/10/25.
 */

class DialogViewHelper {

    private View mContentView = null;
    // 防止霸气侧漏
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
            this();
            mContentView= LayoutInflater.from(mContext).inflate(mViewLayoutResId,null);
    }

    public DialogViewHelper(){
        mViews=new SparseArray<>();
    }


    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View mContentView) {
        this.mContentView = mContentView;
    }

    public <T extends View> T getView(int viewId){

        WeakReference<View> viewWeakReference=mViews.get(viewId);
        View v=null;
        if(viewWeakReference!=null){
           v=viewWeakReference.get();
        }
        if(v==null){
              v=mContentView.findViewById(viewId);
              if(v!=null){
                  mViews.put(viewId, new WeakReference<>(v));
              }
        }
        return (T) v;
    }

    public void setText(int i, CharSequence text) {

        TextView tv=getView(i);
        if (tv != null) {
            tv.setText(text);
        }

    }

    public void setOnclickListener(int i, View.OnClickListener listener) {
        View view = getView(i);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }
}
