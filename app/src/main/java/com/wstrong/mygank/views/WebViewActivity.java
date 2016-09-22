package com.wstrong.mygank.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.umeng.analytics.MobclickAgent;
import com.wstrong.mygank.R;
import com.wstrong.mygank.base.BaseToolbarActivity;
import com.wstrong.mygank.data.model.Collection;
import com.wstrong.mygank.presenter.WebViewPresenter;
import com.wstrong.mygank.presenter.iview.WebViewView;
import com.wstrong.mygank.utils.DeviceUtils;
import com.wstrong.mygank.utils.IntentUtils;
import com.wstrong.mygank.utils.LogUtil;
import com.wstrong.mygank.utils.ShareUtils;
import com.wstrong.mygank.utils.WebViewUtils;

import butterknife.Bind;

public class WebViewActivity extends BaseToolbarActivity implements WebViewView{

    private static final String EXTRA_URL = "EXTRA_URL";
    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.web_view)
    WebView mWebView;

    WebViewPresenter mPresenter;

    private boolean isExists;

    private Collection mCollection;
    /**
     * @param context Any context
     * @param url A valid url to navigate to
     */
    public static void toUrl(Context context, String url) {
        toUrl(context, url, android.R.string.untitled);
    }


    /**
     * @param context Any context
     * @param url A valid url to navigate to
     * @param titleResId A String resource to display as the title
     */
    public static void toUrl(Context context, String url, int titleResId) {
        toUrl(context, url, context.getString(titleResId));
    }


    /**
     * @param context Any context
     * @param url A valid url to navigate to
     * @param title A title to display
     */
    public static void toUrl(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    public static void toUrl(Context context, String url, String title,boolean isVideo) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_TYPE, isVideo);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mWebView.destroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //允许运行JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.getSettings().setAppCachePath(getFilesDir() + getPackageName() + "/cache");
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        setCustomClient();

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSupportZoom(true);

        showBack();

        mWebView.loadUrl(getUrl());
        setTitle(getUrlTitle());
        if(isVideoType()){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void setCustomClient() {
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("www.vmovier.com")) {
                    WebViewUtils.injectCSS(WebViewActivity.this,
                            mWebView, "vmovier.css");
                } else if (url.contains("video.weibo.com")) {
                    WebViewUtils.injectCSS(WebViewActivity.this,
                            mWebView, "weibo.css");
                } else if (url.contains("m.miaopai.com")) {
                    WebViewUtils.injectCSS(WebViewActivity.this,
                            mWebView, "miaopai.css");
                }
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }

                super.onProgressChanged(view,newProgress);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new WebViewPresenter();
        mPresenter.attachView(this);
        mPresenter.getCollection(getUrl());
    }


    @Override
    protected void initListener() {
    }

    MenuItem mMenuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view,menu);
        return true;
    }

    @Override
    //这里getItem()这个却是通过item的索引
    public boolean onPrepareOptionsMenu(Menu menu) {
        mMenuItem = menu.findItem(R.id.menu_web_collection);
        changeMenuItem(isExists);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_web_collection:
                if(isExists){
                    mPresenter.deleteCollection(mCollection.getId());
                }else{
                    mPresenter.saveCollection(getUrl(),getUrlTitle());
                }
                break;
            case R.id.menu_web_refresh:
                mWebView.reload();
                break;
            case R.id.menu_web_copy:
                DeviceUtils.copy2Clipboard(this,getUrl());
                Snackbar.make(mToolbar, this.getString(R.string.copy_success),
                        Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_web_share:
                ShareUtils.share(this,getUrl());
                break;
            case R.id.menu_web_switch_screen_mode:
                switchScreenConfiguration(null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setAppBarLayoutVisibility(true);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setAppBarLayoutVisibility(false);
        }
    }

    private long lastPressedTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (!isVideoType()){
                processScreenLandscape();
            }else{
                if(System.currentTimeMillis() - lastPressedTime > 2000){
                    finish();
                }else{
                    showToast("再按一次退出");
                    lastPressedTime = System.currentTimeMillis();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void processScreenLandscape() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            switchScreenConfiguration(null);
        }else{
            if(mWebView.canGoBack()){
                mWebView.goBack();
            }else{
                finish();
            }
        }
    }

    public void switchScreenConfiguration(MenuItem item) {
        if (this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            if (item != null) item.setTitle(this.getString(R.string.menu_web_vertical));
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            if (item != null) item.setTitle(this.getString(R.string.menu_web_horizontal));
        }
    }

    private String getUrl() {
        return IntentUtils.getStringExtra(this.getIntent(), EXTRA_URL);
    }


    private String getUrlTitle() {
        return IntentUtils.getStringExtra(this.getIntent(), EXTRA_TITLE);
    }

    private boolean isVideoType(){
        return IntentUtils.getBooleanExtra(getIntent(),EXTRA_TYPE,false);
    }

    @Override
    public void onGetCollectionSuccess(Collection collection) {
        LogUtil.d("collection:"+collection);
        if(collection == null){
            changeMenuItem(false);
        }else{
            mCollection = collection;
            changeMenuItem(true);
        }
    }

    private void changeMenuItem(boolean hasCollection) {
        isExists = hasCollection;
        if(mMenuItem == null)
            return;

        if(isExists) {
            mMenuItem.setTitle(getString(R.string.menu_web_cancel_collection));
        }else{
            mMenuItem.setTitle(getString(R.string.menu_web_collection));
        }
    }

    @Override
    public void onGetCollectionFail(String error) {
        showToast(error);
    }

    @Override
    public void onSaveCollectionSuccess(Collection collection) {
        LogUtil.d("save collection:"+collection);
        showToast(getString(R.string.add_collection_tip));
        changeMenuItem(true);
        mCollection = collection;
    }

    @Override
    public void onSaveCollectionFail(String error) {
        showToast(error);
    }

    @Override
    public void onDeleteCollectionSuccess() {
        showToast(getString(R.string.cancel_collection_tip));
        changeMenuItem(false);
    }

    @Override
    public void onDeleteCollectionFail(String error) {
        showToast(error);
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
