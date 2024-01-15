package io.ganguo.demo.http.one;

import io.ganguo.http.error.ExceptionFactory;
import io.ganguo.http.handler.base.BaseResponseHandler;
import io.ganguo.http.bean.HttpResponseCode;
import io.ganguo.http.bean.HttpResponseMessage;
import io.ganguo.utils.exception.BaseException;
import io.ganguo.utils.util.Strings;
import io.reactivex.functions.Function;

/**
 * <p>
 * One开源API  - ResponseHandler
 * </p>
 * Created by leo on 2018/7/30.
 */
public class OnesResponseHandler<T> extends BaseResponseHandler<OnesHttpResponse<T>, T> {

    @Override
    protected Function<OnesHttpResponse<T>, BaseException> onDefaultInterceptFunction() {
        return response1 -> {
            if (response1.getRes() != HttpResponseCode.SUCCESS) {
                String msg = Strings.isEmpty(response1.getMsg()) ? HttpResponseMessage.SERVER_UNKNOWN_ERROR : response1.getMsg();
                return ExceptionFactory.createApiException(msg, response1.getRes());
            }
            return null;
        };
    }

    @Override
    protected T onHandlerResponse(OnesHttpResponse<T> response) {
        return response.getData();
    }
}
