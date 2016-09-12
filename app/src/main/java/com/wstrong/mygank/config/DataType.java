package com.wstrong.mygank.config;

/**
 * Created by pengl on 2016/9/11.
 */
public enum DataType {
    ALL(1,"热门"),
    ANDROID(2,"Android"),
    IOS(3,"IOS"),
    VIDEOS(4,"休息视频"),
    WELFARE(5,"福利"),
    RESOURCES(6,"拓展资源"),
    RECOMMEND(7,"瞎推荐"),
    JS(8,"前端"),
    APP(9,"App");

    int value;

    String name;

    DataType(int value,String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String[] getAllNames(){
        int length = DataType.values().length;
        String[] names = new String[length];

        for (int i = 0; i < length; i++) {
            names[i] = DataType.values()[i].getName();
        }

        return names;
    }

}
