package com.example.skin.framelibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;




/**
 * Created by Administrator on 2018/10/30.
 */

public  abstract  class BaseActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        // 一些特定的算法，子类基本都会使用的
        ViewUtils.inject(this);

        initTitle();

        initView();

        initData();
    }
    // 初始化数据
    public abstract void initData();
    // 初始化界面
    public abstract void initView();
    // 初始化头部
    public abstract void initTitle();

    // 设置布局layout
    public abstract void setContentView();

    protected void startActivity(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    protected <T extends View> T viewById(int viewId){
        return (T)findViewById(viewId);
    }


}
