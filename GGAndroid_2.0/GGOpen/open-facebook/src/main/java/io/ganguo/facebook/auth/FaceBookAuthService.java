package io.ganguo.facebook.auth;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import io.ganguo.facebook.FaceBookAPIFactory;
import io.ganguo.facebook.callback.IFaceBookCallBack;
import io.ganguo.open.sdk.IService;

/**
 * <p>
 * Facebook 第三方登录
 * </p>
 * Created by leo on 2018/9/10.
 */
public class FaceBookAuthService implements IService, FacebookCallback<LoginResult> {
    private IFaceBookCallBack<LoginResult> iLoginCallBack;
    private Activity activity;


    private FaceBookAuthService() {
    }

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static FaceBookAuthService get() {
        return FaceBookAuthService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static FaceBookAuthService HOLDER = new FaceBookAuthService();
    }


    /**
     * function：处理FaceBook Login 参数
     *
     * @param activity
     * @param iLoginCallBack
     * @return
     */
    public FaceBookAuthService applyLoginConfig(Activity activity, IFaceBookCallBack<LoginResult> iLoginCallBack) {
        this.activity = activity;
        this.iLoginCallBack = iLoginCallBack;
        return this;
    }

    /**
     * function：FaceBook Login
     */
    @Override
    public boolean apply() {
        LoginManager
                .getInstance()
                .registerCallback(FaceBookAPIFactory.get().getCallbackManager(), this);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null || accessToken.isExpired()) {
            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends"));
        }
        return true;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        if (iLoginCallBack == null) {
            return;
        }
        iLoginCallBack.onSuccess(loginResult);
    }

    @Override
    public void onCancel() {
        if (iLoginCallBack == null) {
            return;
        }
        iLoginCallBack.onCancel();
    }

    @Override
    public void onError(FacebookException error) {
        if (iLoginCallBack == null) {
            return;
        }
        iLoginCallBack.onFailed(error);
    }


    /**
     * function：资源释放
     */
    @Override
    public void release() {
        iLoginCallBack = null;
        activity = null;
    }
}
