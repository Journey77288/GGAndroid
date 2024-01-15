package io.ganguo.qq.auth;


import android.app.Activity;
import android.os.Bundle;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import io.ganguo.open.sdk.IService;
import io.ganguo.open.sdk.callback.IQQLoginCallBack;
import io.ganguo.qq.R;
import io.ganguo.utils.common.ResHelper;

/**
 * QQ第三方登录
 * Created by leo on 2018/11/7.
 */
public class QQAuthService implements IService, IUiListener {
    public static final String OPENID = "openid";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EXPIRES_IN = "expires_in";
    private IQQLoginCallBack loginCallBack;
    private Activity activity;
    public Tencent tencent;

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static QQAuthService get() {
        return QQAuthService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static QQAuthService HOLDER = new QQAuthService();
    }

    /**
     * function：设置Auth参数
     *
     * @param activity
     * @param loginCallBack
     * @param tencent
     * @return
     */
    public QQAuthService applyAuthData(Activity activity, Tencent tencent, IQQLoginCallBack loginCallBack) {
        this.activity = activity;
        this.tencent = tencent;
        this.loginCallBack = loginCallBack;
        return this;
    }

    /**
     * function：QQ Auth
     *
     * @return
     */
    @Override
    public boolean apply() {
        if (!tencent.isSessionValid()) {
            tencent.login(activity, "all", this);
        }
        return true;
    }


    /**
     * function：登录认证失败
     *
     * @return
     */
    @Override
    public void onError(UiError uiError) {
        if (loginCallBack != null) {
            loginCallBack.onLoginFailed(uiError.errorMessage);
        }
    }

    /**
     * function：取消登录认证
     */
    @Override
    public void onCancel() {
        if (loginCallBack != null) {
            loginCallBack.onLoginCancel();
        }
    }

    /**
     * function：登录认证成功
     *
     * @return
     */
    @Override
    public void onComplete(Object o) {
        if (loginCallBack == null) {
            return;
        }
        Bundle bundle = toUserBundle(o);
        if (bundle == null) {
            loginCallBack.onLoginFailed(ResHelper.getString(R.string.qq_login_fail));
        } else {
            loginCallBack.onQQLoginQSuccess(
                    bundle.getString(OPENID),
                    bundle.getString(ACCESS_TOKEN),
                    bundle.getString(EXPIRES_IN));
        }
    }

    /**
     * function：JSONObject to QQ User Bundle
     *
     * @param o
     * @return
     */
    public Bundle toUserBundle(Object o) {
        if (null == o || ((JSONObject) o).length() == 0) {
            return null;
        }
        Bundle bundle = null;
        JSONObject jsonResponse = (JSONObject) o;
        try {
            String openId = jsonResponse.getString(OPENID);
            String accessToken = jsonResponse.getString(ACCESS_TOKEN);
            String expiresIn = jsonResponse.getString(EXPIRES_IN);
            bundle = new Bundle();
            bundle.putString(OPENID, openId);
            bundle.putString(ACCESS_TOKEN, accessToken);
            bundle.putString(EXPIRES_IN, expiresIn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }


    /**
     * function：资源释放
     */
    @Override
    public void release() {
        loginCallBack = null;
        activity = null;
        tencent = null;
    }

}
