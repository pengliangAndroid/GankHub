package com.wstrong.mygank.base.mvp;

/**
 * Created by pengl on 2016/9/6.
 */
public interface Presenter<T extends MvpView> {
    void attachView(T view);

    void detachView();
}
