package com.wstrong.mygank.utils;

import android.app.Activity;
import android.view.ViewGroup;

import com.wstrong.mygank.R;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by pengl on 2016/10/13.
 */
public class CroutonUtils {

    public static void showCrouton(Activity activity, String content, int viewId) {
        Style style =  new Style.Builder()
                .setHeight(DeviceUtils.dp2px(activity,32))
                .setBackgroundColorValue(activity.getResources().getColor(R.color.core_color_light))
                .build();

        Crouton crouton = Crouton.makeText(activity,content,style,viewId);
        Configuration configuration = new Configuration.Builder().setDuration(1500).build();
        crouton.setConfiguration(configuration);
        crouton.show();
    }

    public static void showCrouton(Activity activity, String content, ViewGroup viewGroup) {
        Style style =  new Style.Builder()
                .setHeight(DeviceUtils.dp2px(activity,32))
                .setBackgroundColorValue(activity.getResources().getColor(R.color.core_color_light))
                .build();

        Crouton crouton = Crouton.makeText(activity,content,style,viewGroup);
        Configuration configuration = new Configuration.Builder().setDuration(1500).build();
        crouton.setConfiguration(configuration);
        crouton.show();
    }
}
