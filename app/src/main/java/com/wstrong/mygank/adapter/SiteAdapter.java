package com.wstrong.mygank.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wstrong.mygank.R;

import java.util.List;

/**
 * Created by pengl on 2016/9/19.
 */
public class SiteAdapter extends BaseQuickAdapter<String> {

    public SiteAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        holder.setText(R.id.tv_site,s);
    }
}
