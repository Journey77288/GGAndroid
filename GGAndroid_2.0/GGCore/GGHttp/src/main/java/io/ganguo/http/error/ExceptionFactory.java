package io.ganguo.http.error;

import io.ganguo.http.R;
import io.ganguo.http.bean.HttpResponseCode;
import io.ganguo.http.error.exception.APIErrorException;
import io.ganguo.http.error.exception.UnAuthorizedException;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.exception.BaseException;
import io.ganguo.utils.util.Strings;

/**
 * <p>
 * Exception 工厂类
 * </p>
 * Created by leo on 2018/8/4.
 */
public class ExceptionFactory {

    /**
     * function：create RuntimeException
     *
     * @param exception
     * @return
     */
    public static RuntimeException createRuntimeException(BaseException exception) {
        return new RuntimeException(exception);
    }

    /**
     * function：create  APIErrorException
     *
     * @param message
     * @param code
     * @return
     */
    public static BaseException createApiException(String message, int code) {
        return new APIErrorException(message, code);
    }


    /**
     * function：create UnauthorizedException
     *
     * @return
     */
    public static BaseException createTokenException() {
        return new UnAuthorizedException(
                ResHelper.getString(R.string.str_http_token_failure), HttpResponseCode.TOKEN_ERROR);
    }


    /**
     * function：create Unknown error Exception
     *
     * @return
     */
    public static BaseException createUnknownException() {
        return new BaseException(
                ResHelper.getString(R.string.str_http_unknown_error), HttpResponseCode.SERVER_UNKNOWN_ERROR);
    }


    /**
     * function：create Server error APIErrorException
     *
     * @param message
     * @return
     */
    public static BaseException createServerException(int code, String message) {
        String msg = Strings.isEmpty(message) ? ResHelper.getString(R.string.str_http_unknown_error) : message;
        return createApiException(msg, code);
    }
}
