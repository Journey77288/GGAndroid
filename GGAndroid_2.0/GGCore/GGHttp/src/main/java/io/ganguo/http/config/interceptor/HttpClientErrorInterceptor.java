package io.ganguo.http.config.interceptor;

import java.io.IOException;

import io.ganguo.http.R;
import io.ganguo.http.bean.HttpResponseCode;
import io.ganguo.http.error.ExceptionFactory;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.exception.BaseException;
import io.ganguo.utils.util.log.Logger;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * <p>
 * 常见Http Response code 拦截处理
 * Response Code规范参考以下文档链接
 *
 * @see <a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status">Response code规范文档</a>
 * </p>
 * Created by leo on 2018/7/31.
 */
public class HttpClientErrorInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        try {
            if (response.code() < 300) {
                if (response.body() == null) {
                    throw createServiceNotRespondingException();
                }
                return response;
            }
            if (response.code() >= 400 && response.code() < 500) {
                return createResponse(chain, HttpResponseCode.RESPONSE_CODE_SUCCESS, response.body());
            }
            throw createServiceErrorException(response);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * function：创建一个新的Response对象
     *
     * @param chain
     * @param resCode
     * @param body
     * @return
     */
    protected Response createResponse(Chain chain, int resCode, ResponseBody body) {
        return new Response.Builder()
                .code(resCode)
                .body(body)
                .request(chain.request())
                .message(ResHelper.getString(R.string.str_http_service_error))
                .protocol(Protocol.HTTP_2)
                .build();
    }


    /**
     * function：创建一个服务器无数据返回Exception
     *
     * @return
     */
    protected BaseException createServiceNotRespondingException() {
        int code = HttpResponseCode.SERVER_NOT_RESPONDING;
        String errorMsg = ResHelper.getString(R.string.str_http_service_not_responding);
        Logger.e(getClass().getSimpleName() + " ServiceNotResponding error code = " + code + " error message = " + errorMsg);
        return ExceptionFactory.createServerException(code, errorMsg);
    }

    /**
     * function：创建一个服务器RuntimeException
     *
     * @param response
     * @return
     */
    protected BaseException createServiceErrorException(Response response) {
        int code = response.code();
        String msg = response.message();
        Logger.e(getClass().getSimpleName() + "ServiceErrorException error code = " + code + " error message = " + msg);
        String errorMsg = "Http server error " + response.code() + " " + response.message();
        return ExceptionFactory.createServerException(response.code(), errorMsg);
    }


}
