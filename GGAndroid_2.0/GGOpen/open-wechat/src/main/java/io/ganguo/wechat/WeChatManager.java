package io.ganguo.wechat;

import android.app.Activity;
import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.ganguo.open.sdk.callback.IWeChatCallBack;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.AppInstalls;
import io.ganguo.wechat.auth.WeChatAuthService;
import io.ganguo.wechat.callback.WeChatCallBack;
import io.ganguo.wechat.share.WeChatShareService;
import io.ganguo.wechat.share.WeChatShareEntity;

/**
 * 微信管理
 * Created by zoyen on 2018/7/10.
 */
public class WeChatManager {
    /**
     * 微信分享接口实例
     */
    public static IWXAPI iwxapi;
    private static WeChatCallBack iCallback;

    /**
     * function: WeChat sdk init
     *
     * @param context
     * @param id
     */
    public static void init(Context context, String id) {
        iwxapi = WXAPIFactory.createWXAPI(context, id, true);
        iwxapi.registerApp(id);
    }

    /**
     * function: 微信分享
     *
     * @param activity
     * @param callback
     * @param data
     */
    public static boolean share(Activity activity, WeChatShareEntity data, IWeChatCallBack callback) {
        if (!checkWeChat(activity, callback)) {
            return false;
        }
        setCallback(new WeChatCallBack(callback));
        return WeChatShareService
                .get()
                .applyShareData(activity, iwxapi, data)
                .apply();
    }

    /**
     * function: IWeChatCallBack to wrap WeChatCallBack
     *
     * @param chatCallBack
     * @return
     */
    public static WeChatCallBack wrap(IWeChatCallBack chatCallBack) {
        return new WeChatCallBack(chatCallBack);
    }


    /**
     * function:微信登录
     *
     * @param activity
     * @param callback
     */
    public static boolean onAuth(Activity activity, IWeChatCallBack callback) {
        if (!checkWeChat(activity, callback)) {
            return false;
        }
        setCallback(new WeChatCallBack(callback));
        return WeChatAuthService
                .get()
                .applyAuthData(iwxapi)
                .apply();
    }

    /**
     * function: 检查微信安装状态
     *
     * @param activity
     * @param callback
     * @return
     */
    public static boolean checkWeChat(Activity activity, IWeChatCallBack callback) {
        if (!AppInstalls.isWeChatInstalled(activity)) {
            ToastHelper.showMessage(ResHelper.getString(R.string.str_wechat_version_hint));
            callback.onCancel();
            return false;
        }
        return true;
    }

    /**
     * function: 分享/认证，结果回调
     *
     * @return
     */
    public static WeChatCallBack getCallback() {
        return WeChatManager.iCallback;
    }

    public static void setCallback(WeChatCallBack callback) {
        WeChatManager.iCallback = callback;
    }

    public static void release() {
        iwxapi = null;
        iCallback = null;
    }
}
