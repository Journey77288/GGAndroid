package io.ganguo.sina;

import android.content.Context;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;

import io.ganguo.open.sdk.callback.ISinaLoginCallBack;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.Strings;

/**
 * 新浪微博登录 - 结果回调接口
 * Created by zoyen on 2018/7/10.
 */
public class SinaAuthCallBack implements WbAuthListener {
    private Context context;
    private ISinaLoginCallBack callback;

    public SinaAuthCallBack(Context context, ISinaLoginCallBack callback) {
        this.context = context;
        this.callback = callback;
    }

    /**
     * function: 认证登录成功
     */
    @Override
    public void onSuccess(final Oauth2AccessToken oauth2AccessToken) {
        if (callback == null) {
            return;
        }
        AccessTokenKeeper.writeAccessToken(context, oauth2AccessToken);
        if (Strings.isEmpty(oauth2AccessToken.getToken())) {
            callback.onLoginFailed(ResHelper.getString(R.string.str_accessToken_is_null));
            return;
        }
        if (Strings.isEmpty(oauth2AccessToken.getUid())) {
            callback.onLoginFailed(ResHelper.getString(R.string.str_uid_is_null));
            return;
        }
        callback.onLoginSuccess(oauth2AccessToken.getToken(), oauth2AccessToken.getUid());
    }

    /**
     * function: 取消认证
     */
    @Override
    public void cancel() {
        if (callback != null) {
            callback.onLoginCancel();
        }

    }

    /**
     * function: 认证失败
     */
    @Override
    public void onFailure(WbConnectErrorMessage errorMessage) {
        if (callback != null) {
            String msg = (errorMessage == null) ? ResHelper.getString(R.string.str_auth_failure) : errorMessage.getErrorMessage();
            callback.onLoginFailed(msg);
        }
    }
}