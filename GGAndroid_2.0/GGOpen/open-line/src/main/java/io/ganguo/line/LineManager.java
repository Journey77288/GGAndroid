package io.ganguo.line;

import android.app.Activity;
import android.content.Intent;

import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import io.ganguo.line.auth.LineAuthService;
import io.ganguo.line.share.LineShareService;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.AppInstalls;

/**
 * LineManager - line登录分享工具类
 * Created by leo on 2018/11/7.
 */
public class LineManager {
    //line登录request code
    public static final int LINE_LOGIN_REQUEST = 20012;

    /**
     * function: init
     *
     * @param channelId
     */
    public static void init(String channelId) {
        LineAuthService.get().setLineChannelId(channelId);
    }

    /**
     * function: init
     *
     * @param activity
     * @param callback
     * @return
     */
    public static boolean onAuth(Activity activity, ILineAuthCallback callback) {
        if (!AppInstalls.isLineInstalled(activity)) {
            ToastHelper.showMessage(ResHelper.getString(R.string.str_line_version_hint));
            return false;
        }
        return LineAuthService
                .get()
                .applyLineAuthData(activity, callback)
                .apply();
    }

    /**
     * function: init
     *
     * @param activity
     * @param builder
     * @return
     */
    public static boolean onShare(Activity activity, LineShareService.Builder builder) {
        if (!AppInstalls.isLineInstalled(activity)) {
            ToastHelper.showMessage(ResHelper.getString(R.string.str_line_version_hint));
            return false;
        }
        return LineShareService
                .get()
                .appShareData(builder)
                .apply();
    }


    /**
     * 注册onActivityResult回调监听，必须要有，否则无法回调
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    public static void registerActivityResultListener(int requestCode, int resultCode, Intent data) {
        if (requestCode != LINE_LOGIN_REQUEST || LineAuthService.get().getCallback() == null) {
            return;
        }
        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
        switch (result.getResponseCode()) {
            case SUCCESS:
                LineAuthService.get().getCallback().onAuthSuccess(result);
                break;
            case CANCEL:
                LineAuthService.get().getCallback().onAuthCancel();
                break;
            default:
                LineAuthService.get().getCallback().onAuthFailed(result.getErrorData().toString());
                break;
        }
        LineAuthService.get().release();
    }
}
