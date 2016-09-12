package com.wstrong.mygank.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wstrong.mygank.R;
import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.data.model.GankDataWrapper;

import java.util.List;

/**
 * Created by pengl on 2016/9/12.
 */
public class HomeAdapter extends BaseQuickAdapter<GankDataWrapper> {

    private Context mContext;

    public HomeAdapter(Context context,int layoutResId, List<GankDataWrapper> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GankDataWrapper item) {
        TextView textView = helper.getView(R.id.daily_android_tag_tv);

        if(DataType.ANDROID.getName().equals(item.getType())){
            textView.setBackgroundResource(R.drawable.bg_android_tag);
        }else if(DataType.IOS.getName().equals(item.getType())){
            textView.setBackgroundResource(R.drawable.bg_ios_tag);
        }else{
            textView.setBackgroundResource(R.drawable.bg_js_tag);
        }
        textView.setText(item.getType());

        helper.setText(R.id.daily_title_tv,item.getDesc());
        helper.setText(R.id.daily_date_tv,item.getPublishedAt().split("T")[0]);

        ImageView imageView = helper.getView(R.id.daily_iv);
        Glide.with(mContext).load(item.getImageUrl()).into(imageView);
    }
}
