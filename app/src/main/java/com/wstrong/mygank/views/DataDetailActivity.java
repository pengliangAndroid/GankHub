package com.wstrong.mygank.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.wstrong.mygank.R;
import com.wstrong.mygank.base.BaseToolbarActivity;
import com.wstrong.mygank.utils.IntentUtils;

import butterknife.Bind;

public class DataDetailActivity extends BaseToolbarActivity {

    private static final String EXTRA_CONTENT = "EXTRA_CONTENT";

    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_detail;
    }

    public static void toActivity(Context context,String content){
        Intent intent = new Intent(context,DataDetailActivity.class);
        intent.putExtra(EXTRA_CONTENT,content);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTvContent.setText(Html.fromHtml(getContent()));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private String getContent(){
        return IntentUtils.getStringExtra(getIntent(),EXTRA_CONTENT);
    }
}
