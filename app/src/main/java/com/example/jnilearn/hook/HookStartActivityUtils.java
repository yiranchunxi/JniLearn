package com.example.jnilearn.hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2018/11/26.
 */

public class HookStartActivityUtils {


    public void hookStartActivity() throws Exception{

        //获取ActivityManagerNative 的 gDefault
        Class<?> amnClass=Class.forName("android.app.ActivityManagerNative");
        Field gDefaultFeild=amnClass.getDeclaredField("gDefault");
        gDefaultFeild.setAccessible(true);
        Object gDefault=gDefaultFeild.get(null);

        //get mInstance
        Class<?> singletonClass=Class.forName("android.util.Singleton");
        Field mInstanceField=singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object iamObject=mInstanceField.get(gDefault);

        //IActivityManager class
        Class<?> iamClass=Class.forName("android.app.IActivityManager");
        //hook IActivityManager
        iamObject=Proxy.newProxyInstance(HookStartActivityUtils.class.getClassLoader(),
                new Class[]{iamClass},new StartActivityInvocationHandler(iamObject));


        //重新指定
        mInstanceField.set(gDefault,iamObject);

    }



    private class StartActivityInvocationHandler implements InvocationHandler{

        private Object mObject;


        public StartActivityInvocationHandler(Object o){
            this.mObject=o;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(mObject,args);
        }
    }
}
