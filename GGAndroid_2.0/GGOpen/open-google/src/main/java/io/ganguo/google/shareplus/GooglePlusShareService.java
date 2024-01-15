package io.ganguo.google.shareplus;

import android.app.Activity;

import com.google.android.gms.plus.PlusShare;

import io.ganguo.google.auth.GoogleAuthService;
import io.ganguo.open.sdk.IService;
import io.ganguo.rx.ActivityResult;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxActivityResult;
import io.ganguo.rx.RxFilter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 * Google + 分享
 * 分享说明文档见链接
 *
 * @see {@link <a>herf="https://developers.google.com/+/mobile/android/share/"<a/>}
 * </p>
 * Created by leo on 2018/9/4.
 */
public class GooglePlusShareService implements IService {
    private static final int RC_GET_PLUS_SHARE = 9005;//google+ 分享
    protected Activity activity;
    protected PlusShare.Builder builder;
    protected IGooglePlusShareCallback callback;
    protected Disposable disposable;


    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static GooglePlusShareService get() {
        return GooglePlusShareService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static GooglePlusShareService HOLDER = new GooglePlusShareService();
    }

    /**
     * function：add share data
     *
     * @param callback
     * @param activity
     * @param builder
     * @return
     */
    public GooglePlusShareService applyShareData(Activity activity, PlusShare.Builder builder, IGooglePlusShareCallback callback) {
        this.activity = activity;
        this.builder = builder;
        this.callback = callback;
        return this;
    }

    /**
     * function：执行分享
     *
     * @return
     */
    @Override
    public boolean apply() {
        disposable = RxActivityResult
                .startIntent(activity, builder.getIntent(), RC_GET_PLUS_SHARE)
                .subscribeOn(Schedulers.io())
                .compose(RxFilter.filterNotNull())
                .filter(result -> result.getRequestCode() == RC_GET_PLUS_SHARE)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> {
                    boolean isOk = result.isResultOK();
                    if (!isOk) {
                        throw new RuntimeException("Google Share Plus failed");
                    }
                    return isOk;
                })
                .doOnError(throwable -> callback.onFailed(throwable))
                .doOnNext(result -> callback.onSuccess(new Object()))
                .doFinally(() -> {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                    disposable = null;
                })
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "_apply_"));
        return true;
    }

    /**
     * function：资源释放
     *
     * @return
     */
    @Override
    public void release() {
        builder = null;
        activity = null;
        callback = null;
        if (disposable!=null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
