package com.example.jnilearn;

import android.app.Application;

import com.example.skin.framelibrary.skin.SkinManager;

/**
 * Created by Administrator on 2018/11/13.
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.getInstance().init(this);
    }
}
