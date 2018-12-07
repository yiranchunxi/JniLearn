package com.example.skin.framelibrary.base;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/10/23.
 */

public class ViewUtils {


    public static void inject(Activity activity){

        inject(new ViewFinder(activity),activity);
    }


    public static void inject(View view){

        inject(new ViewFinder(view),view);
    }

    public static void inject(View view,Object object){

        inject(new ViewFinder(view),object);
    }

    private  static  void inject(ViewFinder viewFinder,Object object){
        injectField(viewFinder,object);
        injectEvent(viewFinder,object);
    }
    private static void injectField(ViewFinder viewFinder, Object object) {
        //1
        Class<?> clazz=object.getClass();
        Field[] fields= clazz.getDeclaredFields();

        for(Field field:fields){
            ViewById viewById=field.getAnnotation(ViewById.class);

            if(viewById!=null){
                int viewId=viewById.value();
                View view=viewFinder.findViewById(viewId);

                if(view!=null){
                    field.setAccessible(true);

                    try {
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    private static void injectEvent(ViewFinder viewFinder, Object object) {

        Class<?> clazz=object.getClass();

        Method[] methods=clazz.getDeclaredMethods();

        for(Method method:methods){


          OnClick onClick= method.getAnnotation(OnClick.class);

          if(onClick!=null){

              int[] views=onClick.value();

              for(int vid:views){

                  View v=viewFinder.findViewById(vid);

                  if (v!=null){
                      v.setOnClickListener(new DeclaredOnClickListener(method, object));
                  }
              }
          }

        }

    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Method mMethod;
        private Object mObject;

        public DeclaredOnClickListener(Method method, Object object) {
            this.mMethod=method;
            this.mObject=object;
        }

        @Override
        public void onClick(View v) {

            try {
                mMethod.setAccessible(true);
                mMethod.invoke(mObject,v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}
