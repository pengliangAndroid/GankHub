package com.wstrong.mygank.data.model;

import java.io.Serializable;

/**
 * Created by pengl on 2016/9/16.
 */
public class GankDailyData extends GankData implements Serializable {

   private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
