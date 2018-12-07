package com.example.jnilearn;


import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnilearn.dialog.MyDialog;
import com.example.skin.framelibrary.base.OnClick;
import com.example.skin.framelibrary.base.ViewById;
import com.example.skin.framelibrary.base.ViewUtils;

public class MainActivity extends AppCompatActivity implements LayoutInflaterFactory{

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    @ViewById(R.id.sample_text) TextView tv;

    @ViewById(R.id.button) Button button;

    @ViewById(R.id.imageView) ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater,this);
        /*LayoutInflaterCompat.setFactory(layoutInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                // 拦截到View的创建
                Log.e("TAG","拦截到View的创建"+name);
                //1创建View

                //2解析属性 src textColor background 自定义属性

                //SkinManager
                return null;// 2天  换肤的框架
            }
        });*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);
        // Example of a call to a native method
        ViewUtils.inject(this);
        String json=outputJsonCode("xong", "21", "man", "code");
        tv.setText(json+parseJsonCode(json));

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"wwwww",Toast.LENGTH_SHORT).show();
                try {
                // 读取本地的一个 .skin里面的资源
                Resources superRes=getResources();

                // 创建AssetManager
                AssetManager asset=AssetManager.class.newInstance();

                // 添加本地下载好的资源皮肤   Native层c和c++怎么搞的
                Method  method=AssetManager.class.getDeclaredMethod("addAssetPath",String.class);

                // method.setAccessible(true); 如果是私有的
                // 反射执行方法
                method.invoke(asset, Environment.getExternalStorageDirectory().getAbsolutePath()+
                        File.separator + "red.skin");

                Resources resources=new Resources(asset,superRes.getDisplayMetrics(),superRes.getConfiguration());

                // 获取资源 id
                int drawableId=resources.getIdentifier("yuanyuan","drawable","com.example.skin");

                Drawable drawable=resources.getDrawable(drawableId);

                imageView.setImageDrawable(drawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });*/


    }
    @OnClick(R.id.sample_text)
    public  void  sampleClick(View v){
        Toast.makeText(MainActivity.this,outputJsonCode("xong", "21", "man", "code"),Toast.LENGTH_SHORT).show();
        MyDialog dialog = new MyDialog.Builder(this)
                .setContentView(R.layout.detail_comment_dialog)
                .formBottom(true)
                .fullWidth()
                .show();
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public native static String outputJsonCode(String name,String age,String sex,String type);

    public native static String parseJsonCode(String json_str);


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 拦截到View的创建
        Log.e("TAG","拦截到View的创建"+name);
        //1创建View

        //2解析属性 src textColor background 自定义属性

        //SkinManager
        return null;// 2天  换肤的框架
    }
}
