package com.example.skin.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2018/11/12.
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView=view;
        this.mSkinAttrs=skinAttrs;
    }

    public void skin(){

        for (SkinAttr attr : mSkinAttrs) {

            attr.skin(mView);
        }  

    }

}
