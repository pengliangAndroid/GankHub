package com.wstrong.mygank.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pengl on 2016/9/12.
 */
public class GankDailyData implements Serializable {

    @SerializedName("results") public DailyResults results;

    @SerializedName("category") public ArrayList<String> category;

    public String imageUrl;

    public class DailyResults {

        @SerializedName("福利") public ArrayList<GankData> welfareData;

        @SerializedName("Android") public ArrayList<GankData> androidData;

        @SerializedName("iOS") public ArrayList<GankData> iosData;

        @SerializedName("前端") public ArrayList<GankData> jsData;

        @SerializedName("休息视频") public ArrayList<GankData> videoData;

        @SerializedName("拓展资源") public ArrayList<GankData> resourcesData;

        @SerializedName("App") public ArrayList<GankData> appData;

        @SerializedName("瞎推荐") public ArrayList<GankData> recommendData;
    }
}
