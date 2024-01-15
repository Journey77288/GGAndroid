package io.ganguo.sample.viewmodel.service.open

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import io.ganguo.alipay.entity.AliPayShareEntity
import io.ganguo.alipay.helper.AlipayOpenHelper
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.facebook.entity.FacebookShareEntity
import io.ganguo.facebook.helper.FacebookOpenHelper
import io.ganguo.factory.GGFactory
import io.ganguo.factory.isQQInstalled
import io.ganguo.factory.isTelegramInstalled
import io.ganguo.factory.isWhatsAppInstalled
import io.ganguo.line.helper.LineOpenHelper
import io.ganguo.line.share.LineShareEntity
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.permissionRx.requestImageMediaPermission
import io.ganguo.qq.entity.QQShareEntity
import io.ganguo.qq.entity.QQZoneShareEntity
import io.ganguo.qq.helper.QQOpenHelper
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.context.initializer.OpenSdkApplicationInitializer
import io.ganguo.sample.sdk.OpenConstants
import io.ganguo.sample.sdk.URIs
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.exception.OpenServiceException
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.ganguo.sina.helper.SinaOpenHelper
import io.ganguo.sina.share.SinaShareEntity
import io.ganguo.single.ImageChooser
import io.ganguo.twitter.entity.TwitterShareEntity
import io.ganguo.twitter.helper.TwitterOpenHelper
import io.ganguo.wechat.annotation.WXMiniProgramVersionType
import io.ganguo.wechat.annotation.WXShareScene
import io.ganguo.wechat.helper.WXOpenHelper
import io.ganguo.wechat.share.WXShareEntity
import io.image.ImageLoader
import io.image.entity.ImageParam
import io.image.enums.ImageShapeType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/15
 *   @desc   : Third-party share Sample
 * </pre>
 * 1、使用OpenSdk时，请注意先在Application中初始化对应sdk，可参考[OpenSdkApplicationInitializer]
 * 2、运行demo时，请选择openSdk渠道包
 * 3、部分三方平台必须在Activity onActivityResult函数中，调用[GGFactory.registerActivityResult]函数
 */
class ActivityShareSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * set title
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Share Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }


    override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                ItemMenuVModel.create("Share to WeChat Friend Sample") {
                    shareToWeChatFriend()
                },
                ItemMenuVModel.create("Share to WeChat Moments Sample") {
                    shareToWeChatMoments()
                },
                ItemMenuVModel.create("Share to Share Mini Sample") {
                    shareToWeChatMiniApp()
                },
                ItemMenuVModel.create("Share to AliPay Sample") {
                    shareToAliPay()
                },
                ItemMenuVModel.create("Share to QQ Sample") {
                    shareToQQ()
                },
                ItemMenuVModel.create("Share to QQZone Sample") {
                    shareQQZone()
                },
                ItemMenuVModel.create("Share to Sina Sample") {
                    shareToSina()
                },
                ItemMenuVModel.create("Share to Twitter Sample") {
                    shareToTwitter()
                },
                ItemMenuVModel.create("Share to FaceBook Sample") {
                    shareToFacebook()
                },
                ItemMenuVModel.create("Share to Telegram Sample") {
                    shareToTelegram()
                },
                ItemMenuVModel.create("Share to Line Sample") {
                    shareToLine()
                },
                ItemMenuVModel.create("Share to WhatsApp Sample") {
                    shareToWhatsApp()
                }
            )
        )
    }


    /**
     * Share to Telegram Sample
     */
    private fun shareToTelegram() {
        if (!isTelegramInstalled(context)) {
            Toasts.show("Please install the Telegram application first！！！")
            return
        }
        val intent = Intent("android.intent.action.SEND")
        intent.setPackage("org.telegram.messenger")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "share test" + " " + "http://www.baidu.com")
        viewIF.activity.startActivity(intent)
    }


    /**
     * Share to WhatsApp Sample
     */
    private fun shareToWhatsApp() {
        if (!isWhatsAppInstalled(context)) {
            Toasts.show("Please install the WhatsApp application first！！！")
            return
        }
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.setPackage("com.whatsapp")
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.baidu.com")
        viewIF.activity.startActivity(Intent.createChooser(shareIntent, "Share WhatsApp"))
    }


    /**
     * Share to Twitter Sample
     */
    private fun shareToTwitter() {
        viewIF.activity
                .requestImageMediaPermission()
                .flatMap {
                    if (it.success) {
                        ImageChooser.pickPhoto(context)
                    } else {
                        Observable.error(Throwable("Please agree to memory read permissions!!"))
                    }
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    TwitterShareEntity("Twitter share test").apply {
                        webPageUrl = "https://www.cnblogs.com/tangZH/p/8206569.html"
                        imageFileUri = URIs.fileToUri(context, it, context.packageName)
                    }
                }
                .flatMap { TwitterOpenHelper.share(viewIF.activity, it) }
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--twitterShare--"))
                .addTo(lifecycleComposite)
    }


    /**
     * Share to FaceBook Sample
     * If is the network picture, must download first, can initiate the sharing
     */
    private fun shareToFacebook() {
        var data = FacebookShareEntity.newShareWebPage("https://www.jianshu.com/p/87a35e971b5c", "【干货】前端开发者最常用的六款IDE")
        FacebookOpenHelper
                .share(viewIF.activity, data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareFacebook--"))
                .addTo(lifecycleComposite)
    }


    /**
     * Share to Sina Sample
     * If is the network picture, must download first, can initiate the sharing
     */
    private fun shareToSina() {
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObs(imageUrl)
                .filter { it is Bitmap }
                .map { SinaShareEntity.newShareImage(it as Bitmap, "新浪微博分享测试") }
                .flatMap { SinaOpenHelper.share(viewIF.activity, it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareSina--"))
                .addTo(lifecycleComposite)
    }


    /**
     * Share to QQZone Sample
     */
    private fun shareQQZone() {
        if (!isQQInstalled(context)) {
            Toasts.show("Please install the QQ application first！！！")
            return
        }
        val data = QQZoneShareEntity.newWebPage("http://ganguo.io/", "测试")
        QQOpenHelper
                .shareZone(viewIF.activity, data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareQQZone--"))
                .addTo(lifecycleComposite)
    }


    /**
     * Share to QQ Sample
     */
    private fun shareToQQ() {
        if (!isQQInstalled(context)) {
            Toasts.show("Please install the QQ application first！！！")
            return
        }
        val data = QQShareEntity.newShareWebPage("http://ganguo.io/", "测试", "测试")
        QQOpenHelper
                .shareFriend(viewIF.activity, data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareQQ--"))
                .addTo(lifecycleComposite)
    }


    /**
     * Share to AliPay Sample
     * If is the network picture, must download first, can initiate the sharing
     */
    private fun shareToAliPay() {
        var data = AliPayShareEntity.newShareImageUrl("https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg")
        AlipayOpenHelper
                .share(viewIF.activity, data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareAliPay--"))
                .addTo(lifecycleComposite)

    }


    /**
     * Share to  WeChat Moments Sample
     * If is the network picture, must download first, can initiate the sharing
     */
    private fun shareToWeChatMoments() {
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObs(imageUrl)
                .filter { it is Bitmap }
                .map { createShareWxWebPageEntity(WXShareScene.WX_SCENE_TIMELINE, it as Bitmap) }
                .flatMap { WXOpenHelper.share(viewIF.activity, it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareWeChatMoments--"))
                .addTo(lifecycleComposite)
    }


    /**
     * share WeChat Mini App Sample
     * If is the network picture, must download first, can initiate the sharing
     */
    private fun shareToWeChatMiniApp() {
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObs(imageUrl)
                .filter { it is Bitmap }
                .map { createShareWeChatMiniAppEntity(it as Bitmap) }
                .flatMap { WXOpenHelper.share(viewIF.activity, it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareWXFriend--"))
                .addTo(lifecycleComposite)
    }


    /**
     * to download Image Observable<Bitmap>
     * @param imageUrl String
     * @return Observable<Bitmap>
     */
    private fun downloadImageObs(imageUrl: String): Observable<Any> {
        return Observable
                .just(imageUrl)
                .subscribeOn(Schedulers.io())
                .map {
                    downloadImage(imageUrl) ?: Observable.empty<Bitmap>()
                }
    }


    /**
     * download image
     * @param imageUrl String
     * @return Bitmap?
     */
    private fun downloadImage(imageUrl: String): Bitmap? = let {
        ImageLoader.get().downloadImage(context, ImageParam(context, ImageShapeType.SQUARE)
                .apply {
                    url(imageUrl)
                    height = OpenConstants.BITMAP_WIDTH
                    width = OpenConstants.BITMAP_HEIGHT
                })
    }


    /**
     * create share mini app WXShareEntity
     * @param bitmap
     * @return
     */
    private fun createShareWeChatMiniAppEntity(bitmap: Bitmap): WXShareEntity {
        return WXShareEntity.newShareMiniProgram(
                "wx62d13cded22a7459",
                WXMiniProgramVersionType.TYPE_TEST,
                "pages/index",
                thumbOriginalBitmap = bitmap,
                webPageUrl = "https://www.baidu.com/",
                title = "答题小程序",
                description = "测试微信分享")
    }


    /**
     * Share to WeChat Friend Sample
     * If is the network picture, must download first, can initiate the sharing
     */
    private fun shareToWeChatFriend() {
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObs(imageUrl)
                .filter { it is Bitmap }
                .map { createShareWxWebPageEntity(WXShareScene.WX_SCENE_SESSION, it as Bitmap) }
                .flatMap { WXOpenHelper.share(viewIF.activity, it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareWXFriend--"))
                .addTo(lifecycleComposite)
    }


    /**
     *  share webPage
     * @param bitmap
     * @return
     */
    private fun createShareWxWebPageEntity(@WXShareScene scene: Int, bitmap: Bitmap): WXShareEntity {
        return WXShareEntity.newShareWebPage(scene = scene,
                webPageUrl = "https://www.baidu.com/",
                thumbOriginalBitmap = bitmap,
                title = "百度一下",
                description = "测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享测试微信分享1231241231")
    }


    /**
     * Share to Line Sample
     */
    private fun shareToLine() {
        viewIF.activity
                .requestImageMediaPermission()
                .flatMap {
                    if (it.success) {
                        var bitmap = BitmapFactory.decodeResource(context.resources, io.ganguo.core.R.drawable.ic_launcher)
                        var imageData = LineShareEntity.newShareImage(bitmap)
                        LineOpenHelper.share(viewIF.activity, imageData)
                    } else {
                        Observable.error(Throwable("Please agree to memory read permissions!!"))
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerShareSdkError(it)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--shareLine--"))
                .addTo(lifecycleComposite)
    }


    /**
     * share result callback
     * @param it
     */
    private fun handlerShareResult(it: OpenResult<*>) {
        var hint = when (it.status) {
            OpenStatus.SUCCESS -> "分享完成"
            OpenStatus.CANCEL -> "分享取消"
            else -> it.message
        }
        Toasts.show(hint.orEmpty())
        Logger.e("${it.channel} Share OpenResult：${it}")
    }


    /**
     * handler share result error callback
     * @param it Throwable?
     */
    private fun handlerShareSdkError(it: Throwable?) {
        if (it is OpenServiceException) {
            Logger.e("OpenSdk channel=${it.channel} errorCode=${it.errorCode} errorMsg=${it.errorMsg}")
        }
        Toasts.show(it?.message.orEmpty())
    }


}
