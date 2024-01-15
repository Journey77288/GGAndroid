package io.ganguo.sina;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;

import io.ganguo.open.sdk.callback.ISinaLoginCallBack;
import io.ganguo.sina.auth.SinaAuthService;
import io.ganguo.sina.share.SinaShareEntity;
import io.ganguo.sina.share.SinaShareService;
import io.ganguo.utils.util.log.Logger;

/**
 * sina - share login sdk
 * Created by zoyen on 2018/7/10.
 */
public class SinaManager {
    public static final int REQUEST_CODE_SSO_AUTH = 32973;


    /**
     * function: init sina sdk
     *
     * @param context
     * @param appKey
     * @param redirectUrl
     * @param scope
     */
    public static void init(Context context, String appKey, String redirectUrl, String scope) {
        AuthInfo authInfo = new AuthInfo(context.getApplicationContext(), appKey, redirectUrl, scope);
        WbSdk.install(context, authInfo);
    }


    /**
     * function: Sina 分享
     *
     * @param context
     * @return
     */
    public static boolean onShare(Activity context, SinaShareEntity data, WbShareCallback callback) {
        return SinaShareService
                .get()
                .applyShareData(context, wrapShareCallBack(callback), data)
                .apply();
    }

    /**
     * function: Sina 微博登录
     *
     * @param activity
     * @param callBack
     * @return
     */
    public static boolean onAuth(Activity activity, ISinaLoginCallBack callBack) {
        return SinaAuthService
                .get()
                .applyAuthData(activity, wrapAuthCallBack(activity, callBack))
                .apply();
    }

    /**
     * function: ISinaLoginCallBack wrap to SinaAuthListener
     *
     * @param callback
     * @param context
     * @return
     */
    public static SinaAuthCallBack wrapAuthCallBack(Context context, ISinaLoginCallBack callback) {
        return new SinaAuthCallBack(context, callback);
    }

    /**
     * function: WbShareCallback to wrap SinaShareCallback
     *
     * @param callback
     */
    public static SinaShareCallback wrapShareCallBack(WbShareCallback callback) {
        return new SinaShareCallback(callback);
    }

    /**
     * function:资源释放
     */
    public static void clearService() {
        SinaShareService.get().release();
        SinaAuthService.get().release();
    }


    /**
     * 注册onActivityResult回调监听
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    public static void registerActivityResultListener(int requestCode, int resultCode, Intent data) {
        if (requestCode != SinaManager.REQUEST_CODE_SSO_AUTH) {
            return;
        }
        SinaAuthService
                .get()
                .getSsoHandler()
                .authorizeCallBack(requestCode, resultCode, data);
    }


    /**
     * function: WbShareCallback to wrap SinaShareCallback
     *
     * @param callback
     */
    public static SinaShareCallback wrap(WbShareCallback callback) {
        return new SinaShareCallback(callback);
    }


    /**
     * function:微博分享回调需要重写 onNewIntent
     *
     * @param intent
     */
    public static void registerShareActivityNewIntent(Intent intent) {
        if (SinaShareService.get().getShareHandler() == null) {
            return;
        }
        SinaShareService.get().doResultIntent(intent);
    }

}

