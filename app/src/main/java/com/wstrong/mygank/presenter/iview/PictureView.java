package com.wstrong.mygank.presenter.iview;

import android.net.Uri;

import com.wstrong.mygank.base.mvp.MvpView;

/**
 * Created by pengl on 2016/9/19.
 */
public interface PictureView extends MvpView {
    void onShareImageSuccess(Uri uri);
    void onShareImageFail(String error);

    void onDownloadImageSuccess(String imageFilePath);

    void onDownloadImageFail(String error);
}
