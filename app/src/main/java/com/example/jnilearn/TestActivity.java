package com.example.jnilearn;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skin.framelibrary.BaseSkinActivity;
import com.example.skin.framelibrary.skin.SkinManager;
import com.example.skin.framelibrary.skin.SkinResource;

import java.io.File;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2018/11/13.
 */

public class TestActivity extends BaseSkinActivity {

    private Button btn_switch;
    private TextView tv_name;
    private ImageView iv_star;
    private Button btn_link;
    private Button btn_restore;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        btn_switch=findViewById(R.id.btn_switch);

        tv_name=findViewById(R.id.tv_name);

        iv_star=findViewById(R.id.iv_star);

        btn_link=findViewById(R.id.btn_link);

        btn_restore=findViewById(R.id.btn_restore);
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin();
            }
        });

        btn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestActivity.class);
            }
        });
        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restore();
            }
        });

        View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, View.MeasureSpec.AT_MOST);


    }

    @Override
    public void initTitle() {

    }


    public void skin(){
        String SkinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator +"red.skin";
        int result = SkinManager.getInstance().loadSkin(SkinPath);
    }

    public void restore(){

        int result=SkinManager.getInstance().restoreDefault();
    }


    @Override
    public void changeSkin(SkinResource skinResource) {
        Toast.makeText(TestActivity.this,"change Skin",Toast.LENGTH_SHORT).show();
    }
}
