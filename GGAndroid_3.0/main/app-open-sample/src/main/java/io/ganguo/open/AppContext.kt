package io.ganguo.open

import io.ganguo.alipay.auth.AliPayOAuthMethod
import io.ganguo.alipay.share.AliPayShareMethod
import io.ganguo.facebook.auth.FacebookOAuthMethod
import io.ganguo.facebook.share.FacebookShareMethod
import io.ganguo.factory.GGFactory
import io.ganguo.google.auth.GoogleOAuthMethod
import io.ganguo.image.core.ImageHelper
import io.ganguo.image.glide.engine.GlideImageEngine
import io.ganguo.core.context.BaseApp
import io.ganguo.line.auth.LineOAuthMethod
import io.ganguo.line.share.LineShareMethod
import io.ganguo.qq.auth.QQOAuthMethod
import io.ganguo.qq.share.qq.QQShareMethod
import io.ganguo.qq.share.zone.QQZoneShareMethod
import io.ganguo.sina.auth.SinaOAuthMethod
import io.ganguo.sina.share.SinaShareMethod
import io.ganguo.twitter.auth.TwitterOAuthMethod
import io.ganguo.twitter.share.TwitterShareMethod
import io.ganguo.wechat.auth.WXOAuthMethod
import io.ganguo.wechat.share.WXShareMethod

/**
 * <pre>
 * author : leo
 * time   : 2018/11/01
 * desc   : Open Sample AppContext
</pre> *
 */

class AppContext : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        initImageHelper()
        initGGOpen()
    }


    /**
     * 初始化ImageHelper
     */
    private fun initImageHelper() {
        var builder = ImageHelper.Builder()
                .setEngine(GlideImageEngine())
        ImageHelper.init(builder)

    }

    /**
     * GGOpen Sdk init
     */
    private fun initGGOpen() {
        GGFactory.registerMethod(WXShareMethod((BuildConfig.WECHAT_APP_ID)))
        GGFactory.registerMethod(WXOAuthMethod((BuildConfig.WECHAT_APP_ID)))
        GGFactory.registerMethod(QQOAuthMethod(BuildConfig.QQ_APP_ID))
        GGFactory.registerMethod(QQShareMethod(BuildConfig.QQ_APP_ID))
        GGFactory.registerMethod(QQZoneShareMethod(BuildConfig.QQ_APP_ID))
        GGFactory.registerMethod(SinaShareMethod(this, BuildConfig.SINA_APP_KEY, BuildConfig.SINA_REDIRECT_URL, BuildConfig.SINA_SCOPE))
        GGFactory.registerMethod(SinaOAuthMethod(this, BuildConfig.SINA_APP_KEY, BuildConfig.SINA_REDIRECT_URL, BuildConfig.SINA_SCOPE))
        GGFactory.registerMethod(AliPayShareMethod(BuildConfig.ALIPAY_APP_KEY))
        GGFactory.registerMethod(AliPayOAuthMethod())
        GGFactory.registerMethod(FacebookOAuthMethod(this, BuildConfig.FACEBOOK_APP_ID))
        GGFactory.registerMethod(FacebookShareMethod(this, BuildConfig.FACEBOOK_APP_ID))
        GGFactory.registerMethod(GoogleOAuthMethod())
        GGFactory.registerMethod(LineOAuthMethod(BuildConfig.LINE_CHANNEL_ID))
        GGFactory.registerMethod(LineShareMethod())
        GGFactory.registerMethod(TwitterShareMethod())
        GGFactory.registerMethod(TwitterOAuthMethod(this, BuildConfig.TWITTER_API_KEY, BuildConfig.TWITTER_API_SECRET_KEY, true))
    }
}
