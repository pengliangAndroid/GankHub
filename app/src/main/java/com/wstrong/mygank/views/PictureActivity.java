package com.wstrong.mygank.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.umeng.analytics.MobclickAgent;
import com.wstrong.mygank.R;
import com.wstrong.mygank.base.BaseToolbarActivity;
import com.wstrong.mygank.presenter.PicturePresenter;
import com.wstrong.mygank.presenter.iview.PictureView;
import com.wstrong.mygank.utils.DeviceUtils;
import com.wstrong.mygank.utils.IntentUtils;
import com.wstrong.mygank.utils.ShareUtils;

import java.io.File;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

public class PictureActivity extends BaseToolbarActivity implements PictureView{
    private static final String ARGUMENT_TITLE = "ARGUMENT_TITLE";
    private static final String ARGUMENT_IMAGE_URL = "ARGUMENT_URL";
    private static final String ARGUMENT_IMAGE_NAME = "ARGUMENT_NAME";

    private static final String SHARED_ELEMENT_NAME = "PictureActivity";

    @Bind(R.id.iv_image)
    PhotoView mIvImage;

    GlideBitmapDrawable mBitmapDrawable;

    PicturePresenter mPresenter;

    private boolean isDownload;

    private String mDownloadFilePath;

    public static void startActivity(Context context, String title, String imageUrl,String imageName) {
        Intent intent = createIntent(context, title, imageUrl,imageName);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String title, String imageUrl) {
        Intent intent = createIntent(context, title, imageUrl,imageUrl);
        context.startActivity(intent);
    }

    public static void startActivityByActivityOptionsCompat(Activity activity,String title,String imageUrl, String imageName, View view) {
        Intent intent = createIntent(activity,title,imageUrl,imageName);

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                view, view.getWidth() / 2, view.getHeight() / 2, view.getWidth(), view.getHeight());
        try {
            ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(activity,title,imageUrl);
        }
    }

    @NonNull
    private static Intent createIntent(Context context, String title, String imageUrl,String imageName) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(ARGUMENT_TITLE, title);
        intent.putExtra(ARGUMENT_IMAGE_URL, imageUrl);
        intent.putExtra(ARGUMENT_IMAGE_NAME, imageName);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ViewCompat.setTransitionName(mIvImage, SHARED_ELEMENT_NAME);

        mPresenter = new PicturePresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void initData() {
        showBack();
        setTitle(getTitleStr());

        Glide.with(this).load(getUrlStr())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mBitmapDrawable = (GlideBitmapDrawable) resource;
                        return false;
                    }
                })
                .crossFade()
                .into(mIvImage)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if(!mIvImage.isShown()){
                            mIvImage.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    protected void initListener() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_picture_download:
                if(!isDownload) {
                    mPresenter.downloadImage(this, mBitmapDrawable, getNameStr());
                }else{
                    showToast("文件已下载，"+mDownloadFilePath);
                }
                break;
            case R.id.menu_picture_copy:
                DeviceUtils.copy2Clipboard(this,getUrlStr());

                Snackbar.make(mToolbar, this.getString(R.string.copy_success),
                        Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_picture_share:
                if(!isDownload){
                    mPresenter.shareImage(this,mBitmapDrawable,getNameStr());
                }else{
                    onShareImageSuccess(Uri.fromFile(new File(mDownloadFilePath)));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getTitleStr() {
        return IntentUtils.getStringExtra(getIntent(),ARGUMENT_TITLE);
    }

    public String getUrlStr() {
        return IntentUtils.getStringExtra(getIntent(),ARGUMENT_IMAGE_URL);
    }

    public String getNameStr() {
        return IntentUtils.getStringExtra(getIntent(),ARGUMENT_IMAGE_NAME);
    }

    @Override
    public void onShareImageSuccess(Uri uri) {
        ShareUtils.shareImage(this,uri,getString(R.string.share_image));
    }

    @Override
    public void onShareImageFail(String error) {
        showToast(error);
    }

    @Override
    public void onDownloadImageSuccess(String imageFilePath) {
        showToast("文件已下载到:"+imageFilePath);
        isDownload = true;
        mDownloadFilePath = imageFilePath;
    }

    @Override
    public void onDownloadImageFail(String error) {
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
