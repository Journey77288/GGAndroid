package io.ganguo.rxqiniu;


import android.support.annotation.NonNull;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * 七牛图片上传管理器
 * Created by Roger on 7/27/16.
 */
public class RxQiNiu {
    private static String TAG = RxQiNiu.class.getSimpleName();

    private static RxQiNiu sSingleton;
    private UploadManager uploadManager;

    private static boolean isDebug = false;
    private static UrlGenerator urlGenerator = null;
    private static UploadOptions defaultUploadOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
        @Override
        public void progress(String key, double percent) {
            if (isDebug) {
                logDebug(TAG, "key: %s , upload progress: %s", key, percent);
            } else {
                // do Nothing
            }
        }
    }, null);


    public static RxQiNiu getInstance() {
        if (sSingleton == null) {
            // double check
            synchronized (RxQiNiu.class) {
                if (sSingleton == null) {
                    sSingleton = new RxQiNiu();
                }
            }
        }
        return sSingleton;
    }

    private RxQiNiu() {
        this.uploadManager = new UploadManager();
    }

    public boolean isDebug() {
        return isDebug;
    }

    /**
     * 开启调试模式
     */
    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * 设置自定义UploadOptions
     */
    public static void setUploadOptions(@NonNull UploadOptions uploadOptions) {
        defaultUploadOptions = uploadOptions;
    }

    /**
     * 设置图片url生成器,可根据文件名动态生成图片url
     */
    public static void setUrlGenerator(@NonNull UrlGenerator imageUrlGenerator) {
        urlGenerator = imageUrlGenerator;
    }

    public UrlGenerator getUrlGenerator() {
        return urlGenerator;
    }


    /**
     * 根据Builder参数上传图片
     *
     * @param builder 上传参数Builder
     * @return return a Observable emit upload result
     */
    public Observable<UploadResult> upload(@NonNull UploadParamBuilder builder) {
        checkUrlGenerator();

        logDebug(TAG, builder.toString());

        switch (builder.getMode()) {
            case UploadParamBuilder.SINGLE_REQUEST:
                return upload(builder.getRequest(), builder.getKeyGenerator(), builder.getTokenGenerator());
            case UploadParamBuilder.MULTI_REQUEST:
                return upload(builder.getRequestList(), builder.getKeyGenerator(), builder.getTokenGenerator(), false);
            case UploadParamBuilder.QUEUE_REQUEST | UploadParamBuilder.MULTI_REQUEST:
                return upload(builder.getRequestList(), builder.getKeyGenerator(), builder.getTokenGenerator(), true);
            default:
                return Observable.empty();
        }
    }


    /**
     * 上传单张图片
     *
     * @param uploadRequest 图片上传请求
     * @param keyGenerator  图片key生成器
     * @param token         获取token的Observable
     * @return return a observable emit single upload result
     */
    public Observable<UploadResult> upload(@NonNull final UploadRequest uploadRequest, final KeyGenerator keyGenerator, final Observable<String> token) {
        checkUrlGenerator();

        return Observable.<Ignore>just(Ignore.INSTANCE)
                .flatMap(new Function<Ignore, Observable<UploadResult>>() {
                    @Override
                    public Observable<UploadResult> apply(Ignore ignore) throws Exception {
                        return requestSingle(uploadRequest, keyGenerator, token);
                    }
                });
    }

    /**
     * 上传多张图片
     *
     * @param requestList   a list of upload request
     * @param keyGenerator  图片key生成器
     * @param token         获取token的Observable
     * @param isQueueUpload 是否开启队列上传
     * @return
     */
    public Observable<UploadResult> upload(@NonNull final List<UploadRequest> requestList, final KeyGenerator keyGenerator, final Observable<String> token, final boolean isQueueUpload) {
        checkUrlGenerator();

        return Observable.<Ignore>just(Ignore.INSTANCE)
                .flatMap(new Function<Ignore, Observable<UploadResult>>() {
                    @Override
                    public Observable<UploadResult> apply(Ignore ignore) throws Exception {
                        if (isQueueUpload) {
                            return queueRequestWithFiles(requestList, keyGenerator, token);
                        } else {
                            return requestWithFiles(requestList, keyGenerator, token);
                        }
                    }
                });
    }

    private Observable<UploadResult> queueRequestWithFiles(final List<UploadRequest> requestList, final KeyGenerator keyGenerator, final Observable<String> token) {
        logDebug(TAG, "onStart: queueRequestWithFiles");
        final PublishSubject<UploadResult> resultEmitter = PublishSubject.create();
        final List<UploadRequest> uploadRequestList = new ArrayList<>(requestList);
        final TokenEmitter tokenEmitter = new TokenEmitter();
        final PublishSubject<UploadResult> resultSubject = getMultiResultSubject(resultEmitter, tokenEmitter, uploadRequestList);

        tokenEmitter.asObservable().subscribe(new Consumer<String>() {
            @Override
            public void accept(String token) {
                logDebug(TAG, "Start next request!");
                request(uploadRequestList.get(0), keyGenerator, token, resultSubject);
            }
        });

        token.compose(RxQiNiuTransformer.requestEmitter(uploadRequestList, requestList))
                .firstElement()
                .subscribe(new Consumer<Property2<String, UploadRequest>>() {
                    @Override
                    public void accept(Property2<String, UploadRequest> property) {
                        String token = property.getFirst();
                        tokenEmitter.setToken(token);
                        request(property.getSecond(), keyGenerator, token, resultSubject);
                    }
                });

        return resultEmitter.hide();
    }

    private Observable<UploadResult> requestWithFiles(final List<UploadRequest> requestList, final KeyGenerator keyGenerator, final Observable<String> token) {
        logDebug(TAG, "onStart: requestWithFiles");
        final PublishSubject<UploadResult> resultEmitter = PublishSubject.create();
        final List<UploadRequest> uploadRequestList = new ArrayList<>(requestList);
        final PublishSubject<UploadResult> resultSubject = getMultiResultSubject(resultEmitter, null, uploadRequestList);

        token.compose(RxQiNiuTransformer.requestEmitter(uploadRequestList, requestList))
                .subscribe(new Consumer<Property2<String, UploadRequest>>() {
                    @Override
                    public void accept(Property2<String, UploadRequest> property) {
                        request(property.getSecond(), keyGenerator, property.getFirst(), resultSubject);
                    }
                }, printError("subscribe request"));

        return resultEmitter.hide();
    }

    private Observable<UploadResult> requestSingle(final UploadRequest uploadRequest, final KeyGenerator keyGenerator, final Observable<String> token) {
        logDebug(TAG, "onStart: requestSingle");
        final PublishSubject<UploadResult> resultEmitter = PublishSubject.create();
        final PublishSubject<UploadResult> resultSubject = getSingleResultSubject(resultEmitter);

        token.subscribe(new Consumer<String>() {
            @Override
            public void accept(String token) {
                if (!uploadRequest.isSuccess()) {
                    request(uploadRequest, keyGenerator, token, resultSubject);
                }
            }
        }, printError("request single"));

        return resultEmitter.hide();
    }


    private void request(final UploadRequest uploadRequest, KeyGenerator keyGenerator, final String token, final Observer<UploadResult> emitter) {
        final String key = keyGenerator.call(uploadRequest.getFile().getName());
        uploadManager.put(uploadRequest.getFile(), key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key1, ResponseInfo info, JSONObject res) {
                logDebug(TAG, "complete: " + "upload info: " + info);
                UploadResult uploadResult = new UploadResult(uploadRequest, urlGenerator.call(key), key, info, res);
                emitter.onNext(uploadResult);
            }
        }, defaultUploadOptions);
    }


    private PublishSubject<UploadResult> getMultiResultSubject(final PublishSubject<UploadResult> resultEmitter,
                                                               final TokenEmitter tokenEmitter,
                                                               final List<UploadRequest> requestList) {
        PublishSubject<UploadResult> resultSubject = PublishSubject.create();

        resultSubject
                .compose(RxQiNiuTransformer.resultProcessor())
                .subscribe(new DisposableObserver<UploadResult>() {
                    @Override
                    public void onComplete() {
                        resultEmitter.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultEmitter.onError(e);
                    }

                    @Override
                    public void onNext(UploadResult result) {
                        logDebug(TAG, "onNext isSuccess: %s", result.isSuccess());
                        boolean isRemoved = requestList.remove(result.getUploadRequest());
                        logDebug(TAG, "request isRemoved: %s", isRemoved);

                        resultEmitter.onNext(result);
                        if (requestList.isEmpty()) {
                            resultEmitter.onComplete();
                        } else {
                            if (tokenEmitter != null) {
                                tokenEmitter.onNext();
                            }
                        }
                    }
                });

        return resultSubject;
    }

    private PublishSubject<UploadResult> getSingleResultSubject(final PublishSubject<UploadResult> resultEmitter) {
        PublishSubject<UploadResult> resultSubject = PublishSubject.create();

        resultSubject
                .compose(RxQiNiuTransformer.resultProcessor())
                .subscribe(new DisposableObserver<UploadResult>() {
                    @Override
                    public void onComplete() {
                        resultEmitter.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultEmitter.onError(e);
                    }

                    @Override
                    public void onNext(UploadResult result) {
                        logDebug(TAG, "onNext isSuccess: %s", result.isSuccess());
                        resultEmitter.onNext(result);
                        resultEmitter.onComplete();
                    }
                });
        return resultSubject;
    }

    private Consumer<Throwable> printError(final String msg) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                logError("%s_onError: ", msg, throwable);
            }
        };
    }

    private void checkUrlGenerator() {
        if (urlGenerator == null) {
            throw new NullPointerException("please set up urlGenerator before upload image");
        }
    }

    static void logDebug(String tag, String format, Object... objects) {
        if (isDebug) {
            Log.d(tag, String.format(Locale.getDefault(), format, objects));
        }
    }

    static void logError(String tag, String format, Object... objects) {
        if (isDebug) {
            Log.e(tag, String.format(Locale.getDefault(), format, objects));
        }
    }
}
