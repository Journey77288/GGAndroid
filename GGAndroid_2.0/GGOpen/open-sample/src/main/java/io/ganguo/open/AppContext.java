package io.ganguo.open;

import io.ganguo.facebook.FaceBookManager;
import io.ganguo.library.BaseApp;
import io.ganguo.line.LineManager;
import io.ganguo.qq.QQManager;
import io.ganguo.sina.SinaManager;
import io.ganguo.twitter.TwitterManager;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.common.ScreenAdaptors;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.wechat.WeChatManager;

/**
 * <p>
 * Open Sample AppContext
 * </p>
 * Created by leo on 2018/9/10.
 */
public class AppContext extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(this);
        ResHelper.init(this);
        ScreenAdaptors.init(this);
        initGGOpen();
    }

    /**
     * function:GGOpen Sdk init
     */
    protected void initGGOpen() {
        LineManager.init(BuildConfig.LINE_CHANNEL_ID);
        FaceBookManager.init(this, BuildConfig.FACEBOOK_APP_ID);
        QQManager.init(this, BuildConfig.QQ_APP_ID);
        TwitterManager.init(this, BuildConfig.TWITTER_API_KEY, BuildConfig.TWITTER_API_SECRET_KEY);
        WeChatManager.init(this, BuildConfig.WECHAT_APP_ID);
        SinaManager.init(this, BuildConfig.SINA_APP_KEY, BuildConfig.SINA_REDIRECT_URL, BuildConfig.SINA_SCOPE);
    }
}
