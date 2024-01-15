package io.ganguo.wechat.auth;


import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import io.ganguo.open.sdk.IService;

/**
 * 微信登录
 * Created by zoyen on 2018/7/10.
 */
public class WeChatAuthService implements IService {
    private static final String WE_CHAT_SCOPE = "snsapi_userinfo";
    private static final String WE_CHAT_SDK_DEMO_TEST = "wechat_sdk_demo_test";
    private IWXAPI iwxapi;

    private WeChatAuthService() {
    }

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static WeChatAuthService get() {
        return LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static WeChatAuthService HOLDER = new WeChatAuthService();
    }

    /**
     * function：add auth data
     *
     * @return
     */
    public WeChatAuthService applyAuthData(IWXAPI iwxapi) {
        this.iwxapi = iwxapi;
        return this;
    }


    @Override
    public boolean apply() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = WE_CHAT_SCOPE;
        req.state = WE_CHAT_SDK_DEMO_TEST;
        this.iwxapi.sendReq(req);
        return true;
    }

    @Override
    public void release() {
        iwxapi = null;
    }
}
