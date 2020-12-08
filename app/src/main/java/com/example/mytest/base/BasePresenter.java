package com.example.mytest.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter <V extends BaseView>{

    public CompositeDisposable compositeDisposable;
    private V mvpView;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        compositeDisposable = new CompositeDisposable();
    }

    public void detachView() {
        this.mvpView = null;
        removeDisposable();
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public V getView() {
        return mvpView;
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
