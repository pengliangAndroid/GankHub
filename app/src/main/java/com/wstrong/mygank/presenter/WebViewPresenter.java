package com.wstrong.mygank.presenter;

import android.util.Log;

import com.wstrong.mygank.Constants;
import com.wstrong.mygank.base.mvp.BasePresenter;
import com.wstrong.mygank.data.model.Collection;
import com.wstrong.mygank.presenter.iview.WebViewView;
import com.wstrong.mygank.utils.RxUtils;

import rx.Subscriber;

/**
 * Created by pengl on 2016/9/21.
 */
public class WebViewPresenter extends BasePresenter<WebViewView> {

    public void getCollection(String url){
        mCompositeSubscription.add(mDataManager.getLocalCollection(url)
                .compose(RxUtils.<Collection>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<Collection>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onGetCollectionFail(Log.getStackTraceString(e));
                            e.printStackTrace();
                        }else{
                            getMvpView().onGetCollectionFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(Collection collection) {
                        getMvpView().onGetCollectionSuccess(collection);
                    }
                })
        );
    }

    public void saveCollection(String url,String title){
        Collection collection = new Collection();
        collection.setUrl(url);
        collection.setTitle(title);

        mCompositeSubscription.add(mDataManager.saveCollection(collection)
                .compose(RxUtils.<Collection>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<Collection>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onSaveCollectionFail(Log.getStackTraceString(e));
                        }else{
                            getMvpView().onSaveCollectionFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(Collection collection) {
                        getMvpView().onSaveCollectionSuccess(collection);
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
                            getMvpView().onDeleteCollectionFail(Log.getStackTraceString(e));
                        }else{
                            getMvpView().onDeleteCollectionFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(Void obj) {
                        getMvpView().onDeleteCollectionSuccess();
                    }
                })
        );
    }
}
