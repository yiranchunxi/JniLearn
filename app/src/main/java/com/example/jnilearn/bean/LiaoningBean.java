package com.example.jnilearn.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class LiaoningBean implements IPickerViewData {

    private String name;
    private String code;
    private List<AreaBean> sub;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AreaBean> getSub() {
        return sub;
    }

    public void setSub(List<AreaBean> sub) {
        this.sub = sub;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    public static class AreaBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
