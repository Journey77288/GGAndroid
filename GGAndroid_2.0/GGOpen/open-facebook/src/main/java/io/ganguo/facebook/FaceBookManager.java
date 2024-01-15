package io.ganguo.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;

import io.ganguo.facebook.callback.IFaceBookCallBack;
import io.ganguo.facebook.auth.FaceBookAuthService;
import io.ganguo.facebook.share.FaceBookShareService;


/**
 * <p>
 * Facebook 分享、登录
 * </p>
 * Created by leo on 2018/9/7.
 */
public class FaceBookManager {
    //Facebook Request code
    public final static int FACEBOOK_REQUEST_CODE = 64207;


    /**
     * Facebook sdk init
     *
     * @param context
     * @param appId
     */
    public static void init(Context context, String appId) {
        FacebookSdk.setApplicationId(appId);
        FacebookSdk.sdkInitialize(context);
        AppEventsLogger.activateApp(context);
    }

    /**
     * Facebook 分享链接
     *
     * @param activity
     * @param builder
     * @param callback
     */
    public static boolean onShareLink(Activity activity, ShareLinkContent.Builder builder, IFaceBookCallBack<Sharer.Result> callback) {
        return FaceBookShareService
                .get()
                .applyShareLinkData(activity, builder, callback)
                .apply();
    }

    /**
     * Facebook 登录
     *
     * @param activity
     * @param callBack
     */
    public static boolean onAuth(Activity activity, IFaceBookCallBack<LoginResult> callBack) {
        return FaceBookAuthService.get()
                .applyLoginConfig(activity, callBack)
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
        FaceBookAPIFactory
                .get()
                .getCallbackManager()
                .onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 资源释放
     */
    public static void release() {
        FaceBookAPIFactory.release();
    }

}
