package com.wstrong.mygank.presenter.iview;

import com.wstrong.mygank.base.mvp.MvpView;
import com.wstrong.mygank.data.model.Collection;

/**
 * Created by pengl on 2016/9/19.
 */
public interface WebViewView extends MvpView {
    void onGetCollectionSuccess(Collection collection);
    void onGetCollectionFail(String error);

    void onSaveCollectionSuccess(Collection collection);
    void onSaveCollectionFail(String error);

    void onDeleteCollectionSuccess();
    void onDeleteCollectionFail(String error);
}
