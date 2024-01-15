package io.ganguo.library.core.http;


import io.ganguo.library.BaseContext;
import io.ganguo.library.core.http.base.HttpService;
import io.ganguo.library.core.http.impl.AsyncHttpImpl;
import io.ganguo.library.core.http.impl.VolleyImpl;

/**
 * 网络支持类
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public class HttpFactory {
    private static BaseContext mContext = null;
    private static HttpService mHttpService = null;
    private static HttpEngine mHttpEngine = HttpEngine.ASYNC_HTTP;

    public static void register(BaseContext context) {
        mContext = context;
    }

    public static void setHttpEngine(HttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        mHttpService = null;
    }

    public static HttpService getHttpService() {
        if (mHttpService == null) {
            // 通过Volley进行实现
            switch (mHttpEngine) {
                case ASYNC_HTTP:
                    mHttpService = new AsyncHttpImpl(mContext);
                    break;
                case VOLLEY:
                    mHttpService = new VolleyImpl(mContext);
                    break;
                default:
                    throw new RuntimeException("Unknown engine: " + mHttpEngine);
            }
        }
        return mHttpService;
    }

}
