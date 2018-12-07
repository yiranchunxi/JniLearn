package com.example.skin.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/11/12.
 * 皮肤的资源管理
 */

public class SkinResource {

    private static final String TAG = "SkinResource";
    private String mPackageName;
    // 资源通过这个对象获取
    private Resources mResources;
    public SkinResource(Context mContext, String skinPath) {
        try{
            // 读取本地的一个 .skin里面的资源
            Resources resources=mContext.getResources();
            // 创建AssetManager
            AssetManager assetManager=AssetManager.class.newInstance();

            // 添加本地下载好的资源皮肤   Native层c和c++怎么搞的
            Method method=AssetManager.class.getDeclaredMethod("addAssetPath",String.class);

            // method.setAccessible(true); 如果是私有的
            // 反射执行方法
            /*method.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "red.skin");*/
            method.invoke(assetManager, skinPath);

            mResources=new Resources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());

            // 获取skinPath包名
            mPackageName=mContext.getPackageManager().getPackageArchiveInfo(
                    skinPath, PackageManager.GET_ACTIVITIES).packageName;
            //Log.e("test",mPackageName);
            //mPackageName="com.example.skin";
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 通过名字获取Drawable
     * @param resName
     * @return
     */
    public Drawable getDrawableByName(String resName){
        try {
            int resId = mResources.getIdentifier(resName, "drawable", mPackageName);
            Log.e(TAG,"resId -> "+resId+" mPackageName -> "+mPackageName +" resName -> "+resName);
            Drawable drawable = mResources.getDrawable(resId);
            return drawable;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过名字获取颜色
     * @param resName
     * @return
     */
    public ColorStateList getColorByName(String resName){
        try {
            int resId = mResources.getIdentifier(resName, "color", mPackageName);
            ColorStateList color = mResources.getColorStateList(resId);
            return color;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
