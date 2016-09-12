package com.wstrong.mygank.base.mvp;

import com.wstrong.mygank.data.DataManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    public CompositeSubscription mCompositeSubscription;
    public DataManager mDataManager;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;

        this.mCompositeSubscription = new CompositeSubscription();
        this.mDataManager = DataManager.getInstance();
    }

    @Override
    public void detachView() {
        mMvpView = null;
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
        this.mDataManager = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}

