package com.example.skin.framelibrary.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/10/23.
 */
@Target(ElementType.FIELD)    //属性 Method方法   TYPE类上 CONSTRUCE 构造函数
@Retention(RetentionPolicy.RUNTIME)  //运行时生效
public @interface ViewById {
    //@ViewById (R.id.xxx)
    int value();
}
