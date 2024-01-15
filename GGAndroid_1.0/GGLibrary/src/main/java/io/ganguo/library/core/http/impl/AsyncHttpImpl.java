package io.ganguo.library.core.http.impl;

import android.content.Context;
import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.base.AbstractHttpService;
import io.ganguo.library.core.http.base.HttpListener;
import io.ganguo.library.core.http.interceptor.HttpInterceptorManager;
import io.ganguo.library.core.http.request.HttpMethod;
import io.ganguo.library.core.http.request.Request;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.core.http.util.HttpUtils;
import io.ganguo.library.util.NetworkUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * Created by Tony on 3/8/15.
 */
public class AsyncHttpImpl extends AbstractHttpService {
    private Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);
    private final Context mContext;
    private AsyncHttpClient mAsyncHttpClient;

    public AsyncHttpImpl(Context context) {
        mContext = context;
        // SSL
        mAsyncHttpClient = new AsyncHttpClient(true, 80, 443);

//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            // 证书
//            trustStore.load(null, null);
//            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
//            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            mAsyncHttpClient.setSSLSocketFactory(sf);
//        } catch (Exception e) {
//            Log.e(TAG, "initSSL fail.", e);
//            mAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
//        }
    }

    /**
     * 发送一个请求
     *
     * @param request
     * @param httpListener
     */
    @Override
    public void sendRequest(final Request request, final HttpListener<?> httpListener) {
        logger.v("[" + request.getMethod() + "] " + request.getUrl());

        // 网络不可用
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            if (httpListener == null) {
                return;
            }
            httpListener.onStart();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpError httpError = new HttpError();
                        httpError.setCode(HttpConstants.Error.NETWORK_ERROR.getCode());
                        httpError.setMessage(HttpConstants.Error.NETWORK_ERROR.getMessage());
                        httpListener.onFailure(httpError);
                    } finally {
                        httpListener.onFinish();
                    }
                }
            }, 1000);
            return;
        }

        executeRequest(request, httpListener);
    }

    private void executeRequest(final Request request, final HttpListener<?> httpListener) {
        ResponseHandlerInterface responseHandler = new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                if (httpListener != null) httpListener.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logger.w(responseString, throwable);

                if (httpListener != null) {
                    if (responseString != null) {
                        HttpError httpError = new HttpError();
                        httpError.setCode(statusCode);
                        httpError.setResponse(responseString);
                        httpListener.handleError(httpError);
                    } else {
                        httpListener.handleError(makeError(throwable));
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                logger.v(responseString);
                if (httpListener != null) {
                    try {
                        HttpResponse httpResponse = HttpInterceptorManager.getInstance().invoke(new HttpResponse(statusCode, responseString));
                        httpListener.handleResponse(httpResponse);
                    } catch (HttpError httpError) {
                        httpListener.handleError(httpError);
                    } finally {
                        // remove unnecessary  onFinish method
                        //httpListener.onFinish();
                    }
                }
                // put to cache
                if (request.getMethod() == HttpMethod.GET && request.getCacheTime() > 0) {
                    putCache(request.getUrl(), responseString, request.getCacheTime());
                }
            }

            @Override
            public void onFinish() {
                if (httpListener != null) httpListener.onFinish();
            }
        };
        // 添加全局 headers
        request.getHeaders().putAll(getHeaderMap());
        Header[] headers = HttpUtils.toHeaders(request.getHeaders());
        switch (request.getMethod()) {
            case GET:
                if (request.getCacheTime() > 0) {
                    // 是否已经有缓存了，存在自动返回缓存数据
                    if (fireCache(request.getUrl(), httpListener)) return;
                }
                mAsyncHttpClient.get(mContext, request.getUrl(), headers, null, responseHandler);
                break;
            case TRACE:
                // AsyncHttp not supported
                request.addHeader("X-HTTP-Method-Override", "TRACE");
            case PATCH:
                // AsyncHttp not supported
                request.addHeader("X-HTTP-Method-Override", "PATCH");
            case OPTIONS:
                // AsyncHttp not supported
                request.addHeader("X-HTTP-Method-Override", "OPTIONS");
            case POST:
                mAsyncHttpClient.post(mContext, request.getUrl(), headers, request.getHttpEntity(), request.getContentType(), responseHandler);
                break;
            case PUT:
                mAsyncHttpClient.put(mContext, request.getUrl(), headers, request.getHttpEntity(), request.getContentType(), responseHandler);
                break;
            case DELETE:
                mAsyncHttpClient.delete(mContext, request.getUrl(), headers, responseHandler);
                break;
            case HEAD:
                mAsyncHttpClient.head(mContext, request.getUrl(), headers, new RequestParams(), responseHandler);
                break;
            default:
                logger.w("HTTP Method not supported!!! " + request.getMethod());
        }
    }

    private HttpError makeError(Throwable throwable) {
        HttpError httpError = new HttpError();
        httpError.setCode(HttpConstants.Error.DEFUALT_ERROR.getCode());
        httpError.setResponse(HttpConstants.Error.DEFUALT_ERROR.getMessage());
        return httpError;
    }
}
