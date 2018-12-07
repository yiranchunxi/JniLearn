package com.example.skin.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skin.framelibrary.skin.SkinManager;
import com.example.skin.framelibrary.skin.SkinResource;

/**
 * Created by Administrator on 2018/11/12.
 */

public enum  SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View mView, String mResName) {
            SkinResource skinResource=getSkinResource();
            ColorStateList colorStateList=skinResource.getColorByName(mResName);

            if(colorStateList==null){
                return;
            }
            TextView textView = (TextView) mView;
            textView.setTextColor(colorStateList);
        }
    },BACKGROUND("background") {
        @Override
        public void skin(View view, String mResName) {
            // 背景可能是图片也可能是颜色
            SkinResource skinResource=getSkinResource();
            Drawable drawable=skinResource.getDrawableByName(mResName);

            if(drawable!=null){
                ImageView imageView = (ImageView) view;
                imageView.setBackgroundDrawable(drawable);
                return;
            }

            // 可能是颜色
            ColorStateList color = skinResource.getColorByName(mResName);
            if(color!=null){
                view.setBackgroundColor(color.getDefaultColor());
            }
        }
    },SRC("src") {
        @Override
        public void skin(View mView, String mResName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(mResName);
            if(drawable!=null){
                ImageView imageView = (ImageView) mView;
                imageView.setImageDrawable(drawable);
                return;
            }
        }
    };

    private String mResName;
    SkinType(String resName){
        this.mResName=resName;
    }

    public abstract  void skin(View mView, String mResName);

    public String getResName() {
        return mResName;
    }

    public SkinResource getSkinResource(){
        return SkinManager.getInstance().getSkinResource();
    }
}
