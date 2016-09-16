package com.wstrong.mygank.config;

/**
 * Created by pengl on 2016/9/11.
 */
public enum DataType {
    ALL(1,"all","所有"),
    ANDROID(2,"Android","Android"),
    IOS(3,"iOS","IOS"),
    WELFARE(4,"福利","福利"),
    VIDEOS(5,"休息视频","休息视频"),
    RESOURCES(6,"拓展资源","拓展资源"),
    RECOMMEND(7,"瞎推荐","瞎推荐"),
    JS(8,"前端","前端"),
    APP(9,"App","App");

    int value;

    String category;

    String name;

    DataType(int value,String category,String name){
        this.value = value;
        this.category = category;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getCategory() {
        return category;
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

    public static int getMultiType(DataType dataType){
        int retVal;
        switch (dataType){
            case ALL:
                retVal = 1;
                break;
            case WELFARE:
                retVal = 2;
                break;
            default:
                retVal = 3;
                break;
        }

        return retVal;
    }

}
