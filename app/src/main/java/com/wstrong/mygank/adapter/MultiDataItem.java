package com.wstrong.mygank.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wstrong.mygank.data.model.GankData;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultiDataItem implements MultiItemEntity {
    public static final int TEXT_AND_IMAGE = 1;
    public static final int IMAGE = 2;
    public static final int TEXT = 3;
    private int itemType;

    private GankData gankData;

    public MultiDataItem(int itemType, GankData gankData) {
        this.itemType = itemType;
        this.gankData = gankData;
    }

    public MultiDataItem(int itemType) {
        this.itemType = itemType;
    }

    public GankData getGankData() {
        return gankData;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
