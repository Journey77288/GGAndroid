package io.ganguo.sample.context.initializer

import android.app.Application
import io.ganguo.alipay.helper.AlipayOpenHelper
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.facebook.helper.FacebookOpenHelper
import io.ganguo.google.helper.GoogleOpenHelper
import io.ganguo.line.helper.LineOpenHelper
import io.ganguo.pay.alipay.helper.AlipayPayHelper
import io.ganguo.pay.wxpay.helper.WXPayHelper
import io.ganguo.qq.helper.QQOpenHelper
import io.ganguo.sample.BuildConfig
import io.ganguo.sina.helper.SinaOpenHelper
import io.ganguo.twitter.helper.TwitterOpenHelper
import io.ganguo.wechat.helper.WXOpenHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/18
 *   @desc   : Open sdk init
 * </pre>
 */
class OpenSdkApplicationInitializer : ApplicationInitializer {

    override fun initialize(application: Application) {
        val wechatAppId = BuildConfig.WECHAT_APP_ID
        WXOpenHelper.initShare(wechatAppId)
        WXOpenHelper.initOauth(wechatAppId)
        WXPayHelper.init(wechatAppId)

        val qqAppId = BuildConfig.QQ_APP_ID
        QQOpenHelper.initShare(qqAppId)
        QQOpenHelper.initOauth(qqAppId)

        val sinaAppKey = BuildConfig.SINA_APP_KEY
        val sinaRedirectUrl = BuildConfig.SINA_REDIRECT_URL
        val sinaScope = BuildConfig.SINA_SCOPE
        SinaOpenHelper.initShare(application, sinaAppKey, sinaRedirectUrl, sinaScope)
        SinaOpenHelper.initOauth(application, sinaAppKey, sinaRedirectUrl, sinaScope)

        val alipayAppKey = BuildConfig.ALIPAY_APP_KEY
        AlipayOpenHelper.initShare(alipayAppKey)
        AlipayOpenHelper.initOauth()
        AlipayPayHelper.init()

        val facebookAppId = BuildConfig.FACEBOOK_APP_ID
        FacebookOpenHelper.initShare(application, facebookAppId)
        FacebookOpenHelper.initOauth(application, facebookAppId)

        val idToken = BuildConfig.GOOGLE_ID_TOKEN
        GoogleOpenHelper.initOauth(idToken)

        val channelId = BuildConfig.LINE_CHANNEL_ID
        LineOpenHelper.initOauth(channelId)
        LineOpenHelper.initShare()

        val apiKey = BuildConfig.TWITTER_API_KEY
        val apiSecret = BuildConfig.TWITTER_API_SECRET_KEY
        TwitterOpenHelper.initShare()
        TwitterOpenHelper.initOauth(application, apiKey, apiSecret, BuildConfig.DEBUG)
    }

    companion object : ApplicationInitializerCreator<OpenSdkApplicationInitializer> {
        override fun createInitializer(parameter: Map<String, Any>): OpenSdkApplicationInitializer {
            return OpenSdkApplicationInitializer()
        }

    }
}
