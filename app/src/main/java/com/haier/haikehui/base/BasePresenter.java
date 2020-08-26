package com.haier.haikehui.base;

import android.util.Log;

import com.haier.haikehui.entity.duodu.TokenBean;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 11:43 AM
 * desc   : 最基本的presenter
 * version: 1.0
 */
public abstract class BasePresenter<V extends BaseView> {

    private String TAG = getClass().getSimpleName();

    protected V view;

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(V view) {
        this.view = view;

    }

    //Activity关闭把view对象置为空
    public void detach() {
        unDisposable();
    }

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    //注销所有请求
    private void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected <T> DisposableObserver<Response<T>> createDisposableObserver(ResponseCallback<Response<T>> responseCallback) {
        DisposableObserver<Response<T>> disposableObserver = new DisposableObserver<Response<T>>() {
            @Override
            public void onNext(@NonNull Response<T> tResponse) {
                //每次都要去添加
                addDisposable(this);
                if ("200".equals(tResponse.getCode())) {
                    responseCallback.onSuccess(tResponse);

                } else {
                    responseCallback.onFailed(tResponse.getCode(), tResponse.getMessage());
                    //弹框提示

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    responseCallback.onError(httpException.code(), httpException.message());
                } else {
                    responseCallback.onError(-1, e.getMessage());
                }
                //弹框提示

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };
        return disposableObserver;
    }

    protected <T> Observer<T> createObserver(ResponseCallback<T> callback){
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(@NonNull T t) {
                callback.onSuccess(t);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    callback.onError(httpException.code(), httpException.message());
                } else {
                    callback.onError(-1, e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        return observer;
    }

    public interface ResponseCallback<T> {
        //返回成功
        void onSuccess(T value);

        //失败了
        void onFailed(String code, String msg);

        //错误
        void onError(int code, String msg);
    }


}
