package com.wstrong.mygank.presenter.iview;

import com.wstrong.mygank.base.mvp.MvpView;
import com.wstrong.mygank.data.model.Collection;

import java.util.List;

/**
 * Created by pengl on 2016/9/19.
 */
public interface CollectionView extends MvpView {
    void onGetCollectionListSuccess(List<Collection> collection);
    void onGetCollectionListFail(String error);

}
