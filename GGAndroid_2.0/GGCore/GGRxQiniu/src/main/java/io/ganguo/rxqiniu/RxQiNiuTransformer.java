package io.ganguo.rxqiniu;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * Created by Roger on 7/28/16.
 */
public class RxQiNiuTransformer {
    private static final String TAG = RxQiNiuTransformer.class.getSimpleName();

    /**
     * 上传重试Transformer
     *
     * @param retryCount      重试次数
     * @param retryObservable 重新上传前操作
     * @return
     */
    public static ObservableTransformer<UploadResult, UploadResult> retry(final int retryCount, final Observable<?> retryObservable) {
        return new ObservableTransformer<UploadResult, UploadResult>() {

            int retryNum = 0;

            @Override
            public Observable<UploadResult> apply(final Observable<UploadResult> source) {
                return source
                        .retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {

                            @Override
                            public Observable<?> apply(final Observable<? extends Throwable> attempts) {
                                return attempts.flatMap(new Function<Throwable, Observable<?>>() {
                                    @Override
                                    public Observable<?> apply(final Throwable throwable) {
                                        if (throwable instanceof RxQiNiuThrowable && retryNum < retryCount) {
                                            retryNum++;
                                            RxQiNiu.logDebug(TAG, "retry time: %d", retryNum);
                                            return retryObservable;
                                        } else {
                                            RxQiNiu.logDebug(TAG, "don't retry: %d", retryNum);
                                            // some other kind of error: just pass it along and don't retry
                                            return Observable.error(throwable);
                                        }
                                    }
                                });
                            }
                        });
            }
        };
    }

    /**
     * 处理上传结果Transformer
     * 七牛回调 responseInfo.isOk() 为 false 时抛出错误
     * 可用retryTransformer处理错误
     */
    static ObservableTransformer<UploadResult, UploadResult> resultProcessor() {
        return new ObservableTransformer<UploadResult, UploadResult>() {
            @Override
            public Observable<UploadResult> apply(Observable<UploadResult> source) {
                return source.flatMap(new Function<UploadResult, Observable<UploadResult>>() {
                    @Override
                    public Observable<UploadResult> apply(UploadResult result) {
                        if (result.isSuccess()) {
                            result.getUploadRequest().setSuccess(true);
                            return Observable.just(result);
                        }
                        return Observable.error(new RxQiNiuThrowable(result));
                    }
                });
            }
        };
    }

    /**
     * Emit upload request from requestList
     */
    static ObservableTransformer<String, Property2<String, UploadRequest>> requestEmitter(final List<UploadRequest> requestList, final List<UploadRequest> backupRequestList) {

        return new ObservableTransformer<String, Property2<String, UploadRequest>>() {
            @Override
            public Observable<Property2<String, UploadRequest>> apply(Observable<String> tokenObservable) {
                return tokenObservable.flatMap(new Function<String, Observable<Property2<String, UploadRequest>>>() {
                    @Override
                    public Observable<Property2<String, UploadRequest>> apply(final String token) {
                        return Observable.fromIterable(backupRequestList)
                                .filter(new Predicate<UploadRequest>() {
                                    @Override
                                    public boolean test(UploadRequest uploadRequest) throws Exception {
                                        boolean isSuccess = uploadRequest.isSuccess();
                                        if (isSuccess) {
                                            requestList.remove(uploadRequest);
                                        }
                                        return !isSuccess;
                                    }
                                })
                                .map(new Function<UploadRequest, Property2<String, UploadRequest>>() {
                                    @Override
                                    public Property2<String, UploadRequest> apply(UploadRequest uploadRequest) {
                                        return new Property2<>(token, uploadRequest);
                                    }
                                });
                    }
                });
            }
        };
    }
}