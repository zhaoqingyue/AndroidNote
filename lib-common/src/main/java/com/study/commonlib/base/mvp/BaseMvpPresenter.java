package com.study.commonlib.base.mvp;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhao.qingyue on 2018/10/16.
 * Presenter层基类
 */
public abstract class BaseMvpPresenter<P> {

    protected P mView;
    protected CompositeDisposable disposables = new CompositeDisposable();

    public BaseMvpPresenter(P p) {
        this.mView = p;
    }

    /**
     * 页面销毁时取消所有网络请求
     */
    public void cancelAllRequest() {
        disposables.clear();
    }
}
