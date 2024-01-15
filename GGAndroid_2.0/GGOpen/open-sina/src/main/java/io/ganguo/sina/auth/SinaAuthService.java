package io.ganguo.sina.auth;

import android.app.Activity;

import com.sina.weibo.sdk.auth.sso.SsoHandler;

import io.ganguo.open.sdk.IService;
import io.ganguo.sina.SinaAuthCallBack;

/**
 * 新浪登录认证
 * Created by leo on 2018/11/8.
 */
public class SinaAuthService implements IService {
    private SsoHandler ssoHandler;
    private SinaAuthCallBack authListener;
    private Activity activity;

    private SinaAuthService() {
    }

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static SinaAuthService get() {
        return SinaAuthService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static SinaAuthService HOLDER = new SinaAuthService();
    }

    /**
     * function: 添加认证相关参数
     *
     * @param activity
     * @param authListener
     */
    public SinaAuthService applyAuthData(Activity activity, SinaAuthCallBack authListener) {
        this.activity = activity;
        this.authListener = authListener;
        return this;
    }

    /**
     * function: get SsoHandler
     *
     * @return
     */
    public SsoHandler getSsoHandler() {
        if (activity == null) {
            return null;
        }
        if (ssoHandler == null) {
            ssoHandler = new SsoHandler(activity);
        }
        return ssoHandler;
    }


    /**
     * function: sina auth
     */
    @Override
    public boolean apply() {
        getSsoHandler().authorize(authListener);
        return true;
    }

    /**
     * function: 资源释放
     */
    @Override
    public void release() {
        ssoHandler = null;
        authListener = null;
    }
}
