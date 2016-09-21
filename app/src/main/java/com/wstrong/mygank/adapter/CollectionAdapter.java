package com.wstrong.mygank.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wstrong.mygank.R;
import com.wstrong.mygank.data.model.Collection;

import java.util.List;

/**
 * Created by pengl on 2016/9/19.
 */
public class CollectionAdapter extends BaseQuickAdapter<Collection> {

    public CollectionAdapter(int layoutResId, List<Collection> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Collection item) {
        holder.setText(R.id.tv_site,item.getTitle());
    }
}
