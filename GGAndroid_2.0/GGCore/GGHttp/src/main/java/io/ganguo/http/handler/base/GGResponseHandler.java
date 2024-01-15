package io.ganguo.http.handler.base;


import io.ganguo.http.entity.response.BHttpResponse;
import io.ganguo.http.error.ExceptionFactory;
import io.ganguo.http.bean.HttpResponseCode;
import io.ganguo.http.bean.HttpResponseStatus;
import io.ganguo.utils.exception.BaseException;
import io.ganguo.utils.util.Strings;
import io.reactivex.functions.Function;

/**
 * <p>
 * 1、封装了甘果API通用的错误过滤规则
 * 2、封装了通用的错误异常提示信息
 * </p>
 * Created by leo on 2018/7/17.
 */
public abstract class GGResponseHandler<B extends BHttpResponse, R> extends BaseResponseHandler<B, R> {


    @Override
    protected Function<B, ? extends BaseException> onDefaultInterceptFunction() {
        return b -> isResponseSuccess(b);
    }

    /**
     * function：api request is success
     *
     * @param response
     */
    protected BaseException isResponseSuccess(B response) {
        if (Strings.isEquals(response.getStatus(), HttpResponseStatus.SUCCESS)) {
            return null;
        }
        if (response.getCode() == HttpResponseCode.TOKEN_ERROR) {
            return ExceptionFactory.createTokenException();
        }
        if (Strings.isNotEmpty(response.getMessage())) {
            return ExceptionFactory.createApiException(response.getMessage(), response.getCode());
        }
        return ExceptionFactory.createUnknownException();
    }

}
