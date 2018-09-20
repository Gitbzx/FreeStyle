package com.bzx.vmovie.microfilm.utils;

/**
 * Describe:
 * Created by bzx on 2018/8/24/024
 * Email:seeyou_x@126.com
 */

public class KeyModel {
    private Integer code;
    private String lable;

    public KeyModel(Integer code,String lable){
        this.code = code;
        this.lable = lable;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
