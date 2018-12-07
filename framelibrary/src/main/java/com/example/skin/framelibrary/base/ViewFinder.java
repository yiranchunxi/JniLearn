package com.example.skin.framelibrary.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2018/10/23.
 */

public class ViewFinder {

    private Activity mActivity;

    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity=activity;
    }

    public ViewFinder(View view) {
        this.mView=view;
    }

    public View findViewById(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}
