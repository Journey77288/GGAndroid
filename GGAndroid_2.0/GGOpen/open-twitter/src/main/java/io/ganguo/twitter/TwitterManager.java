package io.ganguo.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterSession;

import io.ganguo.twitter.auth.TwitterAuthService;
import io.ganguo.twitter.entity.TwitterShareData;
import io.ganguo.twitter.share.TwitterShareService;

import static com.twitter.sdk.android.core.TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE;

/**
 * <p>
 * Twitter SDK Manager
 * Twitter 分享回调
 * </p>
 * Created by leo on 2018/9/30.
 */
public class TwitterManager {
    public static final int TWITTER_REQUEST_CODE = DEFAULT_AUTH_REQUEST_CODE;

    /**
     * Twitter SDK init
     */
    public static void init(Context context, String consumerKey, String consumerSecret) {
        init(context, consumerKey, consumerSecret, true);
    }


    /**
     * Twitter SDK init
     *
     * @param context
     * @param consumerKey
     * @param consumerSecret
     * @param isDebug
     */
    public static void init(Context context, String consumerKey, String consumerSecret, boolean isDebug) {
        TwitterConfig config = new TwitterConfig.Builder(context)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(consumerKey, consumerSecret))//手动使用代码添加key,secret.
                .debug(isDebug)//打开或者关闭debug模式
                .build();
        Twitter.initialize(config);
    }


    /**
     * Twitter Login
     *
     * @param activity
     * @return
     */
    public static boolean onAuth(Activity activity, Callback<TwitterSession> callback) {
        return TwitterAuthService
                .get()
                .applyAuthData(activity, callback)
                .apply();
    }


    /**
     * Twitter share
     *
     * @param activity
     * @param shareData
     */
    public static boolean onShare(Activity activity, TwitterShareData shareData) {
        return TwitterShareService
                .get()
                .applyShareData(activity, shareData)
                .apply();
    }


    /**
     * 注册onActivityResult回调监听
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    public static void registerActivityResultListener(int requestCode, int resultCode, Intent data) {
        if (requestCode != TwitterManager.TWITTER_REQUEST_CODE) {
            return;
        }
        TwitterAuthService
                .get()
                .getAuthClient()
                .onActivityResult(requestCode, resultCode, data);
    }


}
