package com.example.jnilearn.http;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/1.
 */

public class HttpUtils {


    // 默认OkHttpEngine
   private static IHttpEngine mHttpEngine=new OkHttpEngine();

   public static  void init(IHttpEngine httpEngine){
        mHttpEngine=httpEngine;
    }

   private Context mContext;

   private Map<String,Object> mParams;

   private String mUrl;

   // 请求方式
   private int mType=GET_TYPE;

   private final static  int GET_TYPE=0x0011;

   private final static int  POST_TYPE=0x0022;

   public HttpUtils(Context context){
        this.mContext=context;
        this.mParams=new HashMap<>();
   }

    public static HttpUtils with(Context context){
       return new HttpUtils(context);
    }

    public HttpUtils url(String url){
        this.mUrl=url;
        return  this;
    }


    public HttpUtils get(){
        this.mType=GET_TYPE;
        return  this;
    }

    public HttpUtils post(){
        this.mType=POST_TYPE;
        return  this;
    }

    // 添加参数
    public HttpUtils addParams(String key,Object value){
        mParams.put(key,value);
        return  this;
    }

    public HttpUtils addParams(Map<String,Object> params){
        mParams.putAll(params);
        return  this;
    }


    public void execute(EngineCallBack callBack){

        if(callBack==null){
            callBack=EngineCallBack.DEFAULT_CALL_BACK;
        }
        callBack.onPreExecute(mContext,mParams);

        if(mType==GET_TYPE){
            get(mUrl,mParams,callBack);
        }

        if(mType==POST_TYPE){
            post(mUrl,mParams,callBack);
        }
    }


    // get请求
    private  void get(String url, Map<String,Object> params,EngineCallBack callBack){
        mHttpEngine.get(mContext,url,params,callBack);
    }

    // post请求
    private  void post(String url,Map<String,Object> params,EngineCallBack callBack){
        mHttpEngine.post(mContext,url,params,callBack);
    }
    public void execute(){
        execute(null);
    }
    /**
     * 每次可以自带引擎
     */
    public HttpUtils exchangeEngine(IHttpEngine httpEngine){
        mHttpEngine = httpEngine;
        return this;
    }

    public static String jointParams(String url,Map<String,Object> params){

        if(params==null||params.size()<0){
            return url;
        }
        StringBuffer stringBuffer=new StringBuffer(url);

        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for(Map.Entry<String,Object> entry:params.entrySet()){
             stringBuffer.append(entry.getKey()+ "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    public static  Class<?> analysisClazzInfo(Object object){
        Type genType=object.getClass().getGenericSuperclass();
        Type[] params=((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
