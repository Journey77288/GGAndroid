package io.ganguo.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import io.ganguo.open.sdk.base.ICallback;
import io.ganguo.open.sdk.callback.IQQLoginCallBack;
import io.ganguo.qq.auth.QQAuthService;
import io.ganguo.qq.share.QQShareData;
import io.ganguo.qq.share.QQShareService;

/**
 * Created by zoyen on 2018/7/10.
 */
public class QQManager {
    /**
     * QQ Request Code
     */
    public static final int QQ_REQUEST_CODE = 11101;

    /**
     * QQ分享接口实例
     */
    public static Tencent tencent;

    /**
     * function: Tencent sdk init
     *
     * @param context
     * @param appId
     */
    public static void init(Context context, String appId) {
        tencent = Tencent.createInstance(appId, context);
    }

    /**
     * function: 分享
     *
     * @param activity
     * @param data
     */
    public static boolean onShare(Activity activity, QQShareData data, ICallback shareCallBack) {
        return QQShareService
                .get()
                .applyShareData(activity, tencent, data, shareCallBack)
                .apply();
    }

    /**
     * function: 登录认证
     *
     * @param activity
     * @param callBack
     */
    public static boolean onAuth(Activity activity, IQQLoginCallBack callBack) {
        return QQAuthService
                .get()
                .applyAuthData(activity, tencent, callBack)
                .apply();
    }


    /**
     * function: 资源释放
     */
    public static void release() {
        QQShareService.get().release();
    }

    /**
     * function: 资源释放
     */
    public static void clearService() {
        QQShareService.get().release();
    }

    /**
     * 注册onActivityResult回调监听
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    public static void registerActivityResultListener(int requestCode, int resultCode, Intent data) {
        if (requestCode != QQManager.QQ_REQUEST_CODE) {
            return;
        }
        Tencent.onActivityResultData(requestCode, resultCode, data, null);
    }
}
