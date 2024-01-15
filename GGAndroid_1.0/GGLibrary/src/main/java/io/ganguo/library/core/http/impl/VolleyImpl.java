package io.ganguo.library.core.http.impl;

import android.content.Context;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import io.ganguo.library.core.event.OnEventListener;
import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.HttpConstants.Error;
import io.ganguo.library.core.http.base.AbstractHttpService;
import io.ganguo.library.core.http.base.HttpListener;
import io.ganguo.library.core.http.interceptor.HttpInterceptorManager;
import io.ganguo.library.core.http.request.HttpMethod;
import io.ganguo.library.core.http.request.Request;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.util.NetworkUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;


/**
 * Volley　实现类
 * <p/>
 * 只对GET请求进行缓存
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public class VolleyImpl extends AbstractHttpService {
    private Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);

    private final Context mContext;
    private final RequestQueue mRequestQueue;
    private final DefaultRetryPolicy mRetryPolicy;

    public VolleyImpl(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        mRetryPolicy = new DefaultRetryPolicy(
                HttpConstants.REQUEST_TIMEOUT_MS,
                HttpConstants.REQUEST_MAX_RETRIES,
                2f);
    }

    /**
     * 发送一个请求
     *
     * @param request
     * @param httpListener
     */
    @Override
    public void sendRequest(final Request request, final HttpListener<?> httpListener) {
        logger.v("request [" + request.getMethod() + "]: " + request.getUrl());
        // start
        if (httpListener != null) {
            httpListener.onStart();
        } else {
            logger.w("httpListener is null!!!");
        }

        // 网络不可用
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            if (httpListener == null) {
                return;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpError httpError = new HttpError();
                        httpError.setCode(Error.NETWORK_ERROR.getCode());
                        httpError.setMessage(Error.NETWORK_ERROR.getMessage());
                        httpListener.onFailure(httpError);
                    } finally {
                        httpListener.onFinish();
                    }
                }
            }, 1000);
            return;
        }
        // volley不支付patch，必须Override
        if (request.getMethod() == HttpMethod.PATCH) {
            request.setMethod(HttpMethod.POST);
            request.addHeader("X-HTTP-Method-Override", "PATCH");
        }
        doStringRequest(request, httpListener);
    }

    /**
     * String 内容请求
     *
     * @param request
     * @param httpListener
     */
    public void doStringRequest(final Request request, final HttpListener<?> httpListener) {
        final HttpMethod method = request.getMethod();

        // 如果为GET方法，查检本地缓存
        if (method == HttpMethod.GET && request.getCacheTime() > 0) {
            // 是否已经有缓存了，存在自动返回缓存数据
            if (fireCache(request.getUrl(), httpListener)) {
                return;
            }
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                logger.v(response);

                if (httpListener == null) return;

                try {
                    HttpResponse httpResponse = HttpInterceptorManager.getInstance().invoke(new HttpResponse(200, response));
                    httpListener.handleResponse(httpResponse);
                } catch (HttpError httpError) {
                    httpListener.handleError(httpError);
                } finally {
                    httpListener.onFinish();
                }

                // put to cache
                if (method == HttpMethod.GET && request.getCacheTime() > 0) {
                    putCache(request.getUrl(), response, request.getCacheTime());
                }

            }
        };
        // 添加全局 headers
        request.getHeaders().putAll(getHeaderMap());
        final StringRequest stringRequest = new StringRequest(method.getId()
                , request.getUrl()
                , responseListener
                , createErrorListener(httpListener)) {

            /**
             * HEAD 参数
             * @return
             * @throws com.android.volley.AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return request.getHeaders();
            }

            @Override
            public String getBodyContentType() {
                return request.getContentType();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return request.getBody();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> headers = response.headers;
                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(mRetryPolicy);
        stringRequest.setTag("http");
        mRequestQueue.add(stringRequest);
        request.setCancelListener(new OnEventListener<Boolean>() {
            @Override
            public void onEvent(Boolean event) {
                if (event) {
                    stringRequest.cancel();
                }
            }
        });
    }

    /**
     * 创建错误监听器
     *
     * @param httpListener
     * @return
     */
    private Response.ErrorListener createErrorListener(final HttpListener<?> httpListener) {
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logger.e("VolleyError", error);

                if (httpListener == null) return;

                HttpError httpError = makeError(error);
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    // 错误内容
                    int statusCode = error.networkResponse.statusCode;
                    String response = new String(error.networkResponse.data);
                    // 转换错误信息
                    httpError.setCode(statusCode);
                    httpError.setResponse(response);

                    logger.e(statusCode + " HttpError: " + response);
                }
                try {
                    httpListener.handleError(HttpInterceptorManager.getInstance().invoke(httpError));
                } finally {
                    httpListener.onFinish();
                }
            }

        };
        return errorListener;
    }

    /**
     * 产生一个错误信息
     *
     * @param error
     * @return
     */
    private HttpError makeError(VolleyError error) {
        HttpError httpError = new HttpError();
        if (error instanceof TimeoutError) {
            httpError.setCode(Error.TIMEOUT_ERROR.getCode());
            httpError.setMessage(Error.TIMEOUT_ERROR.getMessage());
        } else if (error instanceof NoConnectionError) {
            httpError.setCode(Error.NO_CONNECTION_ERROR.getCode());
//            httpError.setMessage(error.getMessage());
            httpError.setMessage(Error.NO_CONNECTION_ERROR.getMessage());
        } else if (error instanceof AuthFailureError) {
            httpError.setCode(Error.AUTH_FAILURE_ERROR.getCode());
            httpError.setMessage(Error.AUTH_FAILURE_ERROR.getMessage());
        } else if (error instanceof ServerError) {
            httpError.setCode(Error.SERVER_ERROR.getCode());
            httpError.setMessage(Error.SERVER_ERROR.getMessage());
        } else if (error instanceof NetworkError) {
            httpError.setCode(Error.NETWORK_ERROR.getCode());
            httpError.setMessage(Error.NETWORK_ERROR.getMessage());
        } else if (error instanceof ParseError) {
            httpError.setCode(Error.PARSE_ERROR.getCode());
            httpError.setMessage(Error.PARSE_ERROR.getMessage());
        } else {
            httpError.setCode(Error.DEFUALT_ERROR.getCode());
            httpError.setMessage(Error.DEFUALT_ERROR.getMessage());
        }
        return httpError;
    }

}
