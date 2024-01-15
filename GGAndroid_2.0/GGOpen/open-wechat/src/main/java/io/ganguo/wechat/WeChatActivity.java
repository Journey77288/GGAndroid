package io.ganguo.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import io.ganguo.utils.common.ResHelper;
import io.ganguo.wechat.callback.WeChatCallBack;

/**
 * 微信分享、认证回调Activity
 * Created by zoyen on 2018/7/10.
 */
public class WeChatActivity extends Activity implements IWXAPIEventHandler {
    public static String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeChatManager.iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WeChatManager.iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        WeChatCallBack callback = WeChatManager.getCallback();
        if (callback != null) {
            onFilterResult(baseResp, callback);
        }
        WeChatManager.setCallback(null);
        finish();
    }

    /**
     * function: 对返回结果做过滤处理
     *
     * @param baseResp
     */
    protected void onFilterResult(BaseResp baseResp, WeChatCallBack callBack) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                onResultSuccess(baseResp, callBack);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                callBack.onCancel();
                break;
            default:
                onResultFailed(baseResp, callBack);
                break;
        }
    }

    /**
     * function: 分享/认证成功
     *
     * @param baseResp
     * @param callBack
     */
    protected void onResultSuccess(BaseResp baseResp, WeChatCallBack callBack) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            callBack.onSuccess(((SendAuth.Resp) baseResp).code);
        } else {
            callBack.onSuccess(ResHelper.getString(R.string.str_wechat_share_success));
        }
    }


    /**
     * function: 分享/认证成功
     *
     * @param baseResp
     * @param callBack
     */
    protected void onResultFailed(BaseResp baseResp, WeChatCallBack callBack) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            callBack.onFailed(new RuntimeException(ResHelper.getString(R.string.str_wechat_auth_failed) + BaseResp.ErrCode.ERR_AUTH_DENIED));
        } else {
            callBack.onFailed(new RuntimeException(ResHelper.getString(R.string.str_wechat_share_failed) + BaseResp.ErrCode.ERR_AUTH_DENIED));
        }
    }

}
