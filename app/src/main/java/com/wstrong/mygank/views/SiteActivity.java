package com.wstrong.mygank.views;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;
import com.wstrong.mygank.R;
import com.wstrong.mygank.adapter.SiteAdapter;
import com.wstrong.mygank.base.BaseToolbarActivity;
import com.wstrong.mygank.widget.CustomLinearLayoutManager;
import com.wstrong.mygank.widget.DividerItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class SiteActivity extends BaseToolbarActivity {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    List<String> mDataList;

    SiteAdapter mAdapter;

    String[] studySites = new String[]{
            "http://gold.xitu.io/",
            "http://www.jcodecraeer.com/plus/list.php?tid=16",
            "https://github.com/",
            "http://blog.csdn.net/guolin_blog",
            "http://blog.csdn.net/lmj623565791",
            "http://blog.csdn.net/singwhatiwanna",
            "http://blog.csdn.net/xiaanming",
            "http://blog.csdn.net/aigestudio",
            "http://blog.csdn.net/eclipsexys",
            "http://blog.csdn.net/maosidiaoxian",
            "http://blog.csdn.net/cym492224103",
            "http://blog.csdn.net/jdsjlzx"
    };

    String[] studySiteNames = new String[]{
            "掘金专栏",
            "泡在网上的日子",
            "Github",
            "http://blog.csdn.net/guolin_blog",
            "http://blog.csdn.net/lmj623565791",
            "http://blog.csdn.net/singwhatiwanna",
            "http://blog.csdn.net/xiaanming",
            "http://blog.csdn.net/aigestudio",
            "http://blog.csdn.net/eclipsexys",
            "http://blog.csdn.net/maosidiaoxian",
            "http://blog.csdn.net/cym492224103",
            "http://blog.csdn.net/jdsjlzx"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_site;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("站点");
        mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initData() {
        mDataList = Arrays.asList(studySiteNames);
        mAdapter = new SiteAdapter(R.layout.item_site,mDataList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebViewActivity.toUrl(SiteActivity.this,studySites[position],studySiteNames[position]);
            }
        });
    }

    @Override
    protected void initListener() {
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
