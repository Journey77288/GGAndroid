package io.ganguo.google.auth;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;

import io.ganguo.google.GoogleAPIFactory;
import io.ganguo.open.sdk.IService;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxActivityResult;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;

/**
 * <p>
 * Google账号登录
 * </p>
 * Created by leo on 2018/9/4.
 */
public class GoogleAuthService implements IService {
    public static final int RC_GET_AUTH_CODE = 9003;//登录
    protected GoogleSignInClient signInClient;
    protected Activity activity;
    protected Disposable disposable;
    protected IGoogleAuthCallback callback;


    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static GoogleAuthService get() {
        return LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static GoogleAuthService HOLDER = new GoogleAuthService();
    }


    /**
     * function：添加认证相关参数
     *
     * @param activity
     * @param callback
     * @param signInClient
     * @return
     */
    public GoogleAuthService applyAuthData(Activity activity, GoogleSignInClient signInClient, IGoogleAuthCallback callback) {
        this.callback = callback;
        this.signInClient = signInClient;
        this.activity = activity;
        return this;
    }


    /**
     * function：Google 登录
     */
    @Override
    public boolean apply() {
        onGoogleAuth();
        return true;
    }


    /**
     * function：进行Google账号认证
     *
     * @return
     */
    protected void onGoogleAuth() {
        Intent signInIntent = signInClient.getSignInIntent();
        disposable = RxActivityResult
                .startIntent(activity, signInIntent, RC_GET_AUTH_CODE)
                .filter(result -> result.getRequestCode() == RC_GET_AUTH_CODE)
                .filter(result -> result.isResultOK())
                .map(result -> GoogleSignIn.getSignedInAccountFromIntent(result.getData()))
                .map(task -> task.getResult(ApiException.class))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(account -> {
                    callback.onSuccess(account);
                })
                .doOnError(throwable -> callback.onFailed(throwable))
                .doFinally(() -> {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                    disposable = null;
                })
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "onGoogleAuth_apply_"));
    }

    /**
     * function：资源释放
     */
    @Override
    public void release() {
        activity = null;
        callback = null;
        if (signInClient!=null) {
            signInClient.signOut();
            signInClient = null;
        }
        if (disposable!=null) {
            disposable.dispose();
            disposable = null;
        }

    }

}
