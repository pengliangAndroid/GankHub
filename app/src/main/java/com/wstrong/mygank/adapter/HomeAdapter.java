package com.wstrong.mygank.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wstrong.mygank.R;
import com.wstrong.mygank.config.Config;
import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.config.UrlMatcher;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.utils.DateUtils;
import com.wstrong.mygank.utils.GlideUtils;
import com.wstrong.mygank.widget.RatioImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by pengl on 2016/9/12.
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<MultiDataItem> {

    private Context mContext;

    private SimpleDateFormat mDateFormat;

    public HomeAdapter(Context context, List<MultiDataItem> data) {
        super(data);
        mContext = context;
        mDateFormat = new SimpleDateFormat(Config.DATE_FORMAT);
        addItemType(MultiDataItem.TEXT_AND_IMAGE, R.layout.item_text_image);
        addItemType(MultiDataItem.IMAGE, R.layout.item_image);
        addItemType(MultiDataItem.TEXT, R.layout.item_data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiDataItem item) {
        if(item.getItemType() == MultiDataItem.TEXT_AND_IMAGE){
            convertTextAndImage(helper,item);
        }else if(item.getItemType() == MultiDataItem.IMAGE){
            convertImage(helper,item);
        }else if(item.getItemType() == MultiDataItem.TEXT){
            convertText(helper,item);
        }
    }

    private void convertText(BaseViewHolder helper, MultiDataItem item) {
        GankData data = item.getGankData();

        if(TextUtils.isEmpty(data.getDesc())){
            helper.setText(R.id.tv_data_title,"");
        }else{
            helper.setText(R.id.tv_data_title,data.getDesc());
        }

        if(TextUtils.isEmpty(data.getWho())){
            helper.setText(R.id.tv_data_via,"");
        }else{
            helper.setText(R.id.tv_data_via,data.getWho());
        }

        if(TextUtils.isEmpty(data.getUrl())){
            helper.setText(R.id.tv_data_tag,"");
        }else{
            setTag((TextView) helper.getView(R.id.tv_data_tag),item.getGankData().getUrl());
        }

        if(TextUtils.isEmpty(data.getPublishedAt())){
            helper.setText(R.id.tv_data_date,"");
        }else{
            try {
                helper.setText(R.id.tv_data_date,
                        DateUtils.getTimestampString(mDateFormat.parse(data.getPublishedAt())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    private void convertImage(BaseViewHolder helper, MultiDataItem item) {
        GankData data = item.getGankData();
        RatioImageView imageView = helper.getView(R.id.welfare_iv);

        int position = helper.getLayoutPosition();
        if (position % 2 == 0) {
            imageView.setImageRatio(0.7f);
        } else {
            imageView.setImageRatio(0.6f);
        }

        // 图片
        if (TextUtils.isEmpty(data.getUrl())) {
            GlideUtils.displayNative(imageView, R.mipmap.img_default_gray);
        } else {
            GlideUtils.display(imageView, data.getUrl());
        }

        helper.addOnClickListener(R.id.welfare_iv);
    }

    private void convertTextAndImage(BaseViewHolder helper, MultiDataItem item) {
        GankData data = item.getGankData();
        TextView textView = helper.getView(R.id.daily_android_tag_tv);

        if(DataType.ANDROID.getName().equals(data.getType())){
            textView.setBackgroundResource(R.drawable.bg_android_tag);
        }else if(DataType.IOS.getName().equals(data.getType())){
            textView.setBackgroundResource(R.drawable.bg_ios_tag);
        }else{
            textView.setBackgroundResource(R.drawable.bg_js_tag);
        }
        textView.setText(data.getType());

        helper.setText(R.id.daily_title_tv,data.getDesc());

        helper.setText(R.id.daily_date_tv,data.getPublishedAt().split("T")[0]);

        ImageView imageView = helper.getView(R.id.daily_iv);

        // 图片
        if (TextUtils.isEmpty(data.getImageUrl())) {
            GlideUtils.displayNative(imageView, R.mipmap.img_default_gray);
        } else {
            GlideUtils.display(imageView, data.getImageUrl());
        }

        helper.addOnClickListener(R.id.daily_iv);
    }

    /**
     * @param dataTagTV dataTagTV
     * @param url url
     */
    private void setTag(TextView dataTagTV, String url) {
        String key = UrlMatcher.processUrl(url);
        GradientDrawable drawable = (GradientDrawable) dataTagTV.getBackground();
        if (UrlMatcher.url2Content.containsKey(key)) {
            drawable.setColor(UrlMatcher.url2Color.get(key));
            dataTagTV.setText(UrlMatcher.url2Content.get(key));
        } else {
            /*if (this.type == GankType.video) {
                drawable.setColor(UrlMatcher.OTHER_VIDEO_COLOR);
                dataTagTV.setText(UrlMatcher.OTHER_VIDEO_CONTENT);
            } else {*/
                // github 的要特殊处理
                if (url.contains(UrlMatcher.GITHUB_PREFIX)) {
                    drawable.setColor(UrlMatcher.url2Color.get(UrlMatcher.GITHUB_PREFIX));
                    dataTagTV.setText(UrlMatcher.url2Content.get(UrlMatcher.GITHUB_PREFIX));
                } else {
                    drawable.setColor(UrlMatcher.OTHER_BLOG_COLOR);
                    dataTagTV.setText(UrlMatcher.OTHER_BLOG_CONTENT);
                }
            //}
        }
    }
}
