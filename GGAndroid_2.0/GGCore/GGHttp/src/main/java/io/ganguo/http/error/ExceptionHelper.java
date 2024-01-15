package io.ganguo.http.error;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import io.ganguo.http.R;
import io.ganguo.http.core.ApiManager;
import io.ganguo.http.error.exception.UnAuthorizedException;
import io.ganguo.utils.AppManager;
import io.ganguo.utils.callback.common.Action1;
import io.ganguo.utils.common.LoadingHelper;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.exception.BaseException;
import io.ganguo.utils.util.Collections;
import io.ganguo.utils.util.Strings;
import io.reactivex.functions.Function;

/**
 * <p>
 * Exception 辅助工具类 - 用于常用错误判断
 * </p>
 * Created by leo on 2018/8/4.
 */

public class ExceptionHelper {


    /**
     * function：处理一些全局的API错误/异常
     *
     * @param throwable
     */
    public static void onHandlerThrowable(Throwable throwable) {
        if (throwable == null) {
            showErrorInfo(ExceptionFactory.createUnknownException(), "");
        } else if (isNetWorkThrowable(throwable)) {
            showErrorInfo(throwable, ResHelper.getString(R.string.str_http_network_error));
        } else if (isTokenThrowable(throwable)) {
            showErrorInfo(throwable, "");
        } else {
            showErrorInfo(throwable, getCauseMessage(throwable));
        }
        LoadingHelper.hideMaterLoading();
    }

    /**
     * function：显示错误信息
     *
     * @param throwable
     * @return
     */
    protected static void showErrorInfo(Throwable throwable, String message) {
        if (Strings.isNotEmpty(message)) {
            ToastHelper.showMessage(message);
        } else if (throwable instanceof BaseException) {
            BaseException exception = (BaseException) throwable;
            ToastHelper.showMessage(exception.getMessage() + "");
        } else {
            ToastHelper.showMessage(throwable.getMessage() + "");
        }
    }

    /**
     * function：get cause Message
     *
     * @param throwable
     * @return
     */
    protected static String getCauseMessage(Throwable throwable) {
        if (throwable.getCause() == null) {
            return "" + throwable.getMessage();
        }
        if (throwable.getCause().getMessage() != null) {
            return throwable.getCause().getMessage();
        }
        return throwable.getMessage();
    }


    /**
     * function：判断是否是网络错误
     *
     * @param throwable
     * @return
     */
    public static boolean isNetWorkThrowable(Throwable throwable) {
        if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
            return true;
        }
        return false;
    }

    /**
     * function：是否是Token相关错误
     *
     * @param throwable
     * @return
     */
    public static boolean isTokenThrowable(Throwable throwable) {
        if (!(throwable instanceof UnAuthorizedException)) {
            return false;
        }
        Action1<UnAuthorizedException> consumer = ApiManager.getApiStrategy().getUnAuthorizedCallBack();
        if (consumer != null) {
            try {
                consumer.call((UnAuthorizedException) throwable.getCause());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
