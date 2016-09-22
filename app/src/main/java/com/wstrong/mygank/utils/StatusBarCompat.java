package com.wstrong.mygank.utils;

/**
 * Created by pengl on 2015/12/24.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

public class StatusBarCompat
{
    private static final int INVALID_VAL = -1;
    //private static final int COLOR_DEFAULT = Color.parseColor("#20000000");
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");
    private static final int COLOR_TRANSPARENT = Color.parseColor("#00000000");

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (statusColor != INVALID_VAL)
            {
                /*if(hasDrawerLayout){
                    activity.getWindow().setStatusBarColor(COLOR_DEFAULT);
                }else{
                    activity.getWindow().setStatusBarColor(statusColor);
                }*/
                activity.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (statusColor != INVALID_VAL)
            {
                color = statusColor;
            }
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }

    }


    public static void compat(Activity activity)
    {
        compat(activity,COLOR_DEFAULT);
    }


    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
