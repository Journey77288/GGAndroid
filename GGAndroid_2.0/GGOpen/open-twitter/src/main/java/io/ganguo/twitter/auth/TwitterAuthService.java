package io.ganguo.twitter.auth;

import android.app.Activity;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import io.ganguo.open.sdk.IService;
import io.ganguo.utils.util.log.Logger;

/**
 * Twitter Auth
 * Created by leo on 2018/11/6.
 */
public class TwitterAuthService implements IService {
    private TwitterAuthClient authClient = new TwitterAuthClient();
    private Callback<TwitterSession> callback;
    private Activity activity;


    private TwitterAuthService() {
    }

    /**
     * 单例
     *
     * @return
     */
    public static TwitterAuthService get() {
        return TwitterAuthService.LazyHolder.HOLDER;
    }


    /**
     * 单例 - 懒加载
     */
    public static class LazyHolder {
        private static TwitterAuthService HOLDER = new TwitterAuthService();
    }

    /**
     * get TwitterAuthClient
     *
     * @return
     */
    public TwitterAuthClient getAuthClient() {
        if (authClient == null) {
            authClient = new TwitterAuthClient();
        }
        return authClient;
    }

    /**
     * function: 推特登录认证
     *
     * @param callback
     * @param activity
     */
    public TwitterAuthService applyAuthData(Activity activity, Callback<TwitterSession> callback) {
        this.activity = activity;
        this.callback = callback;
        return this;
    }

    @Override
    public boolean apply() {
        getAuthClient().authorize(activity, callback);
        return true;
    }

    @Override
    public void release() {
        activity = null;
        callback = null;
    }
}
