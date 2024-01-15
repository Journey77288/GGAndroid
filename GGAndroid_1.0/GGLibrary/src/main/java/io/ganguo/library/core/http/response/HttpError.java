package io.ganguo.library.core.http.response;

import android.content.Context;

import io.ganguo.library.common.UIHelper;
import io.ganguo.library.exception.AppException;
import io.ganguo.library.util.StringUtils;


/**
 * 请求返回的错误
 * 分为http错误 和 服务器内部定制错误
 * <p/>
 * Created by tony on 9/28/14.
 */
public class HttpError extends AppException {

    private int code;
    private String message;
    private String response;

    public HttpError() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 产生一个toast
     *
     * @param context
     */
    public void makeToast(Context context) {
        if (context != null && StringUtils.isNotEmpty(getMessage())) {
            UIHelper.toastMessageMiddle(context, getMessage());
        }
    }

    @Override
    public String toString() {
        return "HttpError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
