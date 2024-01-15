package io.ganguo.sample.viewmodel.service.open

import android.view.View
import android.view.ViewGroup
import io.ganguo.alipay.helper.AlipayOpenHelper
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.facebook.helper.FacebookOpenHelper
import io.ganguo.factory.GGFactory
import io.ganguo.google.helper.GoogleOpenHelper
import io.ganguo.line.helper.LineOpenHelper
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.qq.helper.QQOpenHelper
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.context.initializer.OpenSdkApplicationInitializer
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.exception.OpenServiceException
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.ganguo.sina.helper.SinaOpenHelper
import io.ganguo.twitter.helper.TwitterOpenHelper
import io.ganguo.wechat.helper.WXOpenHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/15
 *   @desc   : Third-party login / Obtain account information
 * </pre>
 * 1、使用OpenSdk时，请注意先在Application中初始化对应sdk，可参考[OpenSdkApplicationInitializer]
 * 2、运行demo时，请选择openSdk渠道包
 * 3、部分三方平台必须在Activity onActivityResult函数中，调用[GGFactory.registerActivityResult]函数
 */
class ActivityOauthSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    /**
     * set title
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Auth Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                ItemMenuVModel.create("WeChat Auth Sample") {
                    oauthWeChat()
                },
                ItemMenuVModel.create("AliPay Auth Sample") {
                    oauthAliPay()
                },
                ItemMenuVModel.create("QQ Auth Sample") {
                    oauthQQ()
                },
                ItemMenuVModel.create("Sina Auth Sample") {
                    oauthSina()
                },
                ItemMenuVModel.create("Twitter Auth Sample") {
                    oauthTwitter()
                },
                ItemMenuVModel.create("FaceBook Auth Sample") {
                    oauthFacebook()
                },
                ItemMenuVModel.create("Google Auth Sample") {
                    oauthGoogle()
                },
                ItemMenuVModel.create("Line Auth Sample") {
                    oauthLine()
                }
            )
        )
    }


    /**
     * Google oauth Sample
     */
    private fun oauthGoogle() {
        GoogleOpenHelper
                .oauth(viewIF.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    // 上传result中的idToken
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthGoogle--"))
                .addTo(lifecycleComposite)
    }

    /**
     * Twitter oauth Sample
     */
    private fun oauthTwitter() {
        TwitterOpenHelper
                .oauth(viewIF.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthTwitter--"))
                .addTo(lifecycleComposite)
    }


    /**
     * WeChat oAuth Sample
     */
    private fun oauthWeChat() {
        WXOpenHelper
                .oauth(viewIF.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthWeChat--"))
                .addTo(lifecycleComposite)
    }


    /**
     * AliPay oAuth Sample
     */
    private fun oauthAliPay() {
        AlipayOpenHelper
                .oauth(viewIF.activity, "....需要填入服务端接口返回的加密认证商户信息....")
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthAliPay--"))
                .addTo(lifecycleComposite)

    }


    /**
     * Sina oAuth Sample
     */
    private fun oauthSina() {
        SinaOpenHelper
                .oauth(viewIF.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthSina--"))
                .addTo(lifecycleComposite)
    }


    /**
     * QQ oauth Sample
     */
    private fun oauthQQ() {
        QQOpenHelper
                .oauth(viewIF.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthQQ--"))
                .addTo(lifecycleComposite)
    }


    /**
     * line oauth Sample
     */
    private fun oauthLine() {
        LineOpenHelper
                .oauth(viewIF.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthLine--"))
                .addTo(lifecycleComposite)
    }


    /**
     * FaceBook oauth Sample
     *
     */
    private fun oauthFacebook() {
        FacebookOpenHelper
                .oauth(viewIF.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerOauthResult(it)
                }
                .doOnError {
                    handlerOauthSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--oauthFacebook--"))
                .addTo(lifecycleComposite)
    }


    /**
     * oauth result callback
     * @param it
     */
    private fun handlerOauthResult(it: OpenResult<*>) {
        var hint = when (it.status) {
            OpenStatus.SUCCESS -> "登录成功"
            OpenStatus.CANCEL -> "登录取消"
            OpenStatus.FAILED -> "登录异常"
            else -> it.message
        }
        Toasts.show(hint.orEmpty())
        Logger.e("${it.channel} Login OpenResult：message：${it.message}")
        Logger.e("${it.channel} Login OpenResult：result：${it.result}")
    }


    /**
     * oauth error callback
     * @param it Throwable?
     */
    private fun handlerOauthSdkError(it: Throwable?) {
        if (it is OpenServiceException) {
            Logger.e("OpenSdk channel=${it.channel} errorCode=${it.errorCode} errorMsg=${it.errorMsg}")
        }
        Toasts.show(it?.message.orEmpty())

    }
}
