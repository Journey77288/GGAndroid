package io.ganguo.rxqiniu;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Roger on 7/28/16.
 */
public class UploadParamBuilder {

    /**
     * 单张图片上传
     */
    public static final int SINGLE_REQUEST = 1;

    /**
     * 多张图片异步上传
     */
    public static final int MULTI_REQUEST = 1 << 1;

    /**
     * 队列上传Flag
     */
    public static final int QUEUE_REQUEST = 1 << 2;

    private List<UploadRequest> requestList;
    private UploadRequest singleRequest;
    private KeyGenerator keyGenerator;
    private Observable<String> tokenGenerator;
    @UploadMode
    private int mode;

    public UploadParamBuilder(@UploadMode int mode) {
        this.mode = mode;
    }

    public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    public UploadParamBuilder keyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
        return this;
    }

    @UploadMode
    public int getMode() {
        return mode;
    }

    public static int getMultiRequest() {
        return MULTI_REQUEST;
    }

    public List<UploadRequest> getRequestList() {
        return requestList;
    }

    public UploadParamBuilder requestList(List<UploadRequest> requestList) {
        this.requestList = requestList;
        return this;
    }

    public UploadParamBuilder request(UploadRequest singleRequest) {
        this.singleRequest = singleRequest;
        return this;
    }

    public UploadRequest getRequest() {
        return singleRequest;
    }

    public Observable<String> getTokenGenerator() {
        return tokenGenerator;
    }

    public UploadParamBuilder tokenGenerator(Observable<String> tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
        return this;
    }

    private String parseMode() {
        switch (mode) {
            case SINGLE_REQUEST:
                return "SINGLE_REQUEST";
            case MULTI_REQUEST:
                return "MULTI_REQUEST";
            case MULTI_REQUEST | QUEUE_REQUEST:
                return "QUEUE_MULTI_REQUEST";
            default:
                return "invalid mode";
        }
    }

    @Override
    public String toString() {
        return "UploadParamBuilder{" +
                " mode=" + parseMode() +
                ", requestList=" + requestList +
                ", singleRequest=" + singleRequest +
                '}';
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(flag = true, value = {SINGLE_REQUEST, MULTI_REQUEST, QUEUE_REQUEST})
    public @interface UploadMode {

    }
}
