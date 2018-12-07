package com.example.skin.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by Administrator on 2018/11/12.
 */

public class SkinAttr {
    private String mResName;
    private SkinType mType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName=resName;
        this.mType=skinType;
    }

    public void skin(View mView) {

        mType.skin(mView,mResName);
    }


}
