package com.wstrong.mygank.presenter;

import android.util.Log;

import com.wstrong.mygank.Constants;
import com.wstrong.mygank.base.mvp.BasePresenter;
import com.wstrong.mygank.data.model.Collection;
import com.wstrong.mygank.presenter.iview.CollectionView;
import com.wstrong.mygank.utils.RxUtils;

import java.util.List;

import rx.Subscriber;

/**
 * Created by pengl on 2016/9/21.
 */
public class CollectionPresenter extends BasePresenter<CollectionView> {

    public void getCollectionList(){
        mCompositeSubscription.add(mDataManager.getAllCollection()
                .compose(RxUtils.<List<Collection>>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<List<Collection>>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onGetCollectionListFail(Log.getStackTraceString(e));
                        }else{
                            getMvpView().onGetCollectionListFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(List<Collection> dataList) {
                        getMvpView().onGetCollectionListSuccess(dataList);
                    }
                })
        );
    }

    public void deleteCollection(Long id){

        mCompositeSubscription.add(mDataManager.deleteCollection(id)
                .compose(RxUtils.<Void>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onGetCollectionListFail(Log.getStackTraceString(e));
                        }else{
                            getMvpView().onGetCollectionListFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(Void obj) {

                    }
                })
        );
    }
}
