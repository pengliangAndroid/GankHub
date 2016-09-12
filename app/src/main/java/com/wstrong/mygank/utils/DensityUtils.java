package com.wstrong.mygank.utils;

import android.content.Context;

/**
 * Created by pengl on 2016/9/8.
 */
public class DensityUtils {
    /**
     * dp 转化为 px
     *
     * @param context context
     * @param dpValue dpValue
     * @return int
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px 转化为 dp
     *
     * @param context context
     * @param pxValue pxValue
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
