package com.wstrong.mygank.config;

/**
 * Created by pengl on 2016/9/11.
 */
public enum DataType {
    HOT(1,"热门"),
    WELFARE(2,"福利"),
    ANDROID(3,"Android"),
    IOS(4,"IOS"),
    JS(5,"前端"),
    VIDEOS(6,"休息视频"),
    RESOURCES(7,"拓展资源"),
    APP(8,"App");

    int value;

    String name;

    DataType(int value,String name){
        this.value = value;
        this.name = name;
    }

}
