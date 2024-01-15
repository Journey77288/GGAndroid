package io.ganguo.google;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.plus.PlusShare;

import io.ganguo.google.auth.GoogleAuthService;
import io.ganguo.google.auth.IGoogleAuthCallback;
import io.ganguo.google.shareplus.GooglePlusShareService;
import io.ganguo.google.shareplus.IGooglePlusShareCallback;


/**
 * Google服务相关Manager
 * <p>
 * 注意：使用Google相关分享、认证服务，需要去https://firebase.google.com/添加应用，并且需要保证包名、sha1和控制台填写的一致。且需要按
 * 说明将相应json文件放到项目中，并开启相关API服务，方可使用相关服务。
 * </p>
 * Created by leo on 2018/9/4.
 */
public class GoogleManager {

    /**
     * function: Google账号登录
     *
     * @param activity
     * @param callback
     * @return
     */
    public static boolean onAuth(Activity activity, IGoogleAuthCallback callback) {
        GoogleSignInClient client = GoogleSignIn.getClient(activity, GoogleAPIFactory.createSignInOptions());
        return GoogleAuthService
                .get()
                .applyAuthData(activity, client, callback)
                .apply();
    }


    /**
     * function: Google取消认证状态
     *
     * @param activity
     * @return
     */
    public static boolean onCancelAuth(Activity activity) {
        GoogleSignInClient client = GoogleSignIn.getClient(activity, GoogleAPIFactory.createSignInOptions());
        client.asGoogleApiClient().clearDefaultAccountAndReconnect();
        client.signOut();
        return true;
    }


    /**
     * function: Google+ 分享
     *
     * @param callback
     * @param builder
     * @param activity
     * @return
     */
    public static boolean onPlusShare(Activity activity, PlusShare.Builder builder, IGooglePlusShareCallback callback) {
        return GooglePlusShareService
                .get()
                .applyShareData(activity, builder, callback)
                .apply();
    }

}
