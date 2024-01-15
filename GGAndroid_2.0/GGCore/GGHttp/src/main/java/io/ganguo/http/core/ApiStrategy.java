package io.ganguo.http.core;

import android.content.Context;
import android.os.Build;


import io.ganguo.http.error.exception.ApiException;
import io.ganguo.http.error.exception.NetWorkException;
import io.ganguo.http.error.exception.UnAuthorizedException;
import io.ganguo.utils.callback.common.Action1;
import io.ganguo.utils.exception.BaseException;


/**
 * API配置
 * Created by Roger on 30/12/2016.
 */
public class ApiStrategy {
    private Builder builder;

    public ApiStrategy(Builder builder) {
        this.builder = builder;
    }

    public String getAppChannel() {
        return builder.AppChannel;
    }


    public String getAppVersionName() {
        return builder.AppVersionName;
    }


    public boolean isDebug() {
        return builder.isDebug;
    }


    public String getBaseUrl() {
        return builder.baseUrl;
    }

    public Context getContext() {
        return builder.context;
    }

    public Action1<UnAuthorizedException> getUnAuthorizedCallBack() {
        return builder.unAuthorizedCallBack;
    }

    public Action1<NetWorkException> getNetWorkErrorCallBack() {
        return builder.networkErrorCallBack;
    }

    public Action1<BaseException> getExceptionCallBack() {
        return builder.exceptionCallBack;
    }

    public Action1<ApiException> getApiExceptionCallBack() {
        return builder.apiExceptionCallBack;
    }

    /**
     * get the AppVersionName、Build.VERSION.RELEASE and Build.VERSION.SDK_INT
     *
     * @return
     */
    public String generateUserAgent() {
        return "app/" + getAppVersionName() + " (android; " + Build.VERSION.RELEASE + "; " + Build.VERSION.SDK_INT + ")";
    }

    @Override
    public String toString() {
        return "ApiStrategy{" +
                "builder=" + builder +
                '}';
    }

    public static class Builder {
        //上下文
        private Context context;
        private String AppChannel;
        //App版本名称
        private String AppVersionName;
        //是否是debug包
        private boolean isDebug = true;
        private String baseUrl;
        private Action1<UnAuthorizedException> unAuthorizedCallBack;
        private Action1<NetWorkException> networkErrorCallBack;
        private Action1<BaseException> exceptionCallBack;
        private Action1<ApiException> apiExceptionCallBack;


        public ApiStrategy build() {
            return new ApiStrategy(this);
        }


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * function：App包渠道
         *
         * @param appChannel
         * @return
         */
        public Builder setAppChannel(String appChannel) {
            AppChannel = appChannel;
            return this;
        }

        /**
         * function：App版本名称
         *
         * @param appVersionName
         * @return
         */
        public Builder setAppVersionName(String appVersionName) {
            AppVersionName = appVersionName;
            return this;
        }

        /**
         * function：是否是debug包
         *
         * @param debug
         * @return
         */
        public Builder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        /**
         * function：http baseUrl
         *
         * @param baseUrl
         * @return
         */
        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * function：token无效/过期回调接口
         *
         * @param unAuthorizedCallBack
         * @return
         */
        public Builder setUnAuthorizedCallBack(Action1<UnAuthorizedException> unAuthorizedCallBack) {
            this.unAuthorizedCallBack = unAuthorizedCallBack;
            return this;
        }

        /**
         * function：网络发生错误时，回调接口
         *
         * @param networkErrorCallBack
         * @return
         */
        public Builder setNetworkErrorCallBack(Action1<NetWorkException> networkErrorCallBack) {
            this.networkErrorCallBack = networkErrorCallBack;
            return this;
        }

        /**
         * function：通用错误回调，发送错误时，始终会调用
         *
         * @param exceptionCallBack
         * @return
         */
        public Builder setExceptionCallBack(Action1<BaseException> exceptionCallBack) {
            this.exceptionCallBack = exceptionCallBack;
            return this;
        }


        /**
         * function：Api错误回调接口，在API接口返回错误的情况下，会回调该接口
         *
         * @param apiExceptionCallBack
         * @return
         */
        public Builder setApiExceptionCallBack(Action1<ApiException> apiExceptionCallBack) {
            this.apiExceptionCallBack = apiExceptionCallBack;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "context=" + context +
                    ", AppChannel='" + AppChannel + '\'' +
                    ", AppVersionName='" + AppVersionName + '\'' +
                    ", isDebug=" + isDebug +
                    ", baseUrl='" + baseUrl + '\'' +
                    '}';
        }
    }
}
