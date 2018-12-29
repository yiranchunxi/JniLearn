package com.example.jnilearn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.jnilearn.bean.LiaoningBean;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by Administrator on 2018/12/18.
 */

public class PickCityActivity extends AppCompatActivity {

    private Button mPickButton;
    private boolean isLoaded = false;
    private ArrayList<LiaoningBean> options1=new ArrayList<>();
    private ArrayList<ArrayList<String>> options2=new ArrayList<>();

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Toast.makeText(PickCityActivity.this, "加载数据中...", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                //initJsonData();
                                initMyJsonData();

                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    //Toast.makeText(PickCityActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    showMyPickView();
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(PickCityActivity.this, "加载数据失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_city);

        mPickButton=findViewById(R.id.btn_pick_city);


        mPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                if (isLoaded) {
                    // showPickerView();

                } else {
                    Toast.makeText(PickCityActivity.this, "加载数据中...", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void showMyPickView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int op1, int op2, int op3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1.get(op1).getPickerViewText() +
                        options2.get(op1).get(op2);

                Toast.makeText(PickCityActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1, options2);//二级选择器
        // pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();

    }

    private void initMyJsonData() {

        String JsonData = getJson(this, "liaoning.json");//获取assets目录下的json文件数据

        //Log.e("test",JsonData);

        options1=parseJsonData(JsonData);

        for(int i=0;i<options1.size();i++){

            ArrayList<String> areaList = new ArrayList<>();//该省的所有地区列表（第二级）

            for(int j=0;j<options1.get(i).getSub().size();j++){
                areaList.add(options1.get(i).getSub().get(j).getName());
            }

            options2.add(areaList);
        }


        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<LiaoningBean> parseJsonData(String result){

        ArrayList<LiaoningBean>  liaoningBeans=new ArrayList<>();

        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                LiaoningBean entity = gson.fromJson(data.optJSONObject(i).toString(), LiaoningBean.class);
                liaoningBeans.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return liaoningBeans;

    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
