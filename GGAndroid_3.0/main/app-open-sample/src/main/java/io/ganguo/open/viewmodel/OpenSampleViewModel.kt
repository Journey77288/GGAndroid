package io.ganguo.open.viewmodel

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import io.ganguo.alipay.auth.AliPayOAuthMethod
import io.ganguo.alipay.entity.AliPayShareEntity
import io.ganguo.alipay.share.AliPayShareMethod
import io.ganguo.facebook.auth.FacebookOAuthMethod
import io.ganguo.facebook.entity.FacebookShareEntity
import io.ganguo.facebook.share.FacebookShareMethod
import io.ganguo.factory.GGFactory
import io.ganguo.google.auth.GoogleOAuthMethod
import io.ganguo.image.core.ImageHelper
import io.ganguo.image.core.entity.ImageParam
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.line.auth.LineOAuthMethod
import io.ganguo.line.share.LineShareEntity
import io.ganguo.line.share.LineShareMethod
import io.ganguo.log.core.Logger
import io.ganguo.open.BuildConfig
import io.ganguo.open.R
import io.ganguo.open.sdk.OpenConstants
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.open.sdk.exception.OpenServiceException
import io.ganguo.qq.auth.QQOAuthMethod
import io.ganguo.qq.entity.QQShareEntity
import io.ganguo.qq.entity.QQZoneShareEntity
import io.ganguo.qq.share.qq.QQShareMethod
import io.ganguo.qq.share.zone.QQZoneShareMethod
import io.ganguo.rx.RxActions
import io.ganguo.rximagepicker2.RxImagePicker
import io.ganguo.rximagepicker2.RxImagePickerMode
import io.ganguo.sina.auth.SinaOAuthMethod
import io.ganguo.sina.share.SinaShareEntity
import io.ganguo.sina.share.SinaShareMethod
import io.ganguo.twitter.auth.TwitterOAuthMethod
import io.ganguo.twitter.entity.TwitterShareEntity
import io.ganguo.twitter.share.TwitterShareMethod
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.AppInstalls
import io.ganguo.utils.util.URIs
import io.ganguo.utils.util.requestStoragePermissions
import io.ganguo.viewmodel.pack.base.viewmodel.BaseHFRecyclerVModel
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.pack.common.item.ItemSampleVModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.wechat.annotation.WXMiniProgramVersionType
import io.ganguo.wechat.annotation.WXShareScene
import io.ganguo.wechat.auth.WXOAuthMethod
import io.ganguo.wechat.share.WXShareEntity
import io.ganguo.wechat.share.WXShareMethod
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.internal.functions.Functions
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 * author : leo
 * time   : 2018/8/6
 * desc   : GanGuo Open Sdk Demo
</pre> *
 */

class OpenSampleViewModel : BaseHFRecyclerVModel<ActivityInterface<IncludeHfRecyclerBinding>>() {

    /**
     * init header
     */
    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        val headerViewModel = HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel("第三方分享/登录Demo"))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity))
                .build()
        ViewModelHelper.bind(container, this, headerViewModel)
    }


    override fun onViewAttached(view: View) {

        //微信好友分享
        addViewModel(R.color.green_dark_translucent, "分享给微信好友", Action {
            shareWXFriend()
        })

        //微信小程序分享
        addViewModel(R.color.green_dark_translucent, "分享小程序给微信好友", Action {
            //微信小程序和微信app必须都是同一个微信开放平台账号下的应用，才能分享成功，所以这里的demo是无法分享成功的
            shareWxMiniApp()
        })

        //微信朋友圈分享
        addViewModel(R.color.green_dark_translucent, "分享到微信朋友圈", Action {
            shareWXTimeline()
        })

        //微信登录
        addViewModel(R.color.green_dark_translucent, "微信登录", Action { loginWX() })

        //QQ分享
        addViewModel(R.color.blue_dark_translucent, "QQ分享", Action { shareQQ() })

        //QQ登录
        addViewModel(R.color.blue_dark_translucent, "QQ登录", Action { loginQQ() })

        //QQ登录
        addViewModel(R.color.blue_dark_translucent, "QQ空间分享", Action { shareQQZone() })

        //新浪分享
        addViewModel(R.color.red_dark_translucent, "新浪微博分享", Action { shareSina() })

        //新浪登录
        addViewModel(R.color.red_dark_translucent, "新浪微博登录", Action { loginSina() })

        //Google认证
        addViewModel(R.color.gray_dark, "google auth", Action { loginGoogle() })

        //Facebook 分享
        addViewModel(R.color.blue_light, "facebook share", Action { shareFacebook() })

        //Facebook 登录
        addViewModel(R.color.blue_light, "facebook login", Action { loginFacebook() })

        //WhatApp Share
        addViewModel(R.color.green, "whatApp share", Action { shareToWhatApp() })

        //Telegram Share
        addViewModel(R.color.blue_light, "Telegram share", Action { shareToTelegram() })

        //Twitter Share
        addViewModel(R.color.blue_light, "Twitter share", Action {
            runWithPermissionsStorage {
                twitterShare()
            }
        })

        //Twitter 登录
        addViewModel(R.color.blue_light, "Twitter login", Action { loginTwitter() })


        //Line 分享文本
        addViewModel(R.color.green_dark_translucent, "Line share", Action {
            runWithPermissionsStorage {
                var bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher)
                var imageData = LineShareEntity.newShareImage(bitmap)
                shareLine(imageData)
            }
        })
        //Line 登录
        addViewModel(R.color.green_dark_translucent, "Line login", Action { loginLine() })

        addViewModel(R.color.blue_dark, "AliPay login", Action { loginAliPay() })

        addViewModel(R.color.blue_dark, "AliPay share", Action { shareAliPay() })

        toggleEmptyView()
    }


    /**
     * add ItemViewModel
     *
     * @param color
     * @param action
     * @param text
     */
    private fun addViewModel(@ColorRes color: Int, text: String, action: Action) {
        adapter.add(ItemSampleVModel.newItemViewModel(color, text, action))
    }


    /**
     * 处理Open SDK 错误
     * @param it Throwable?
     */
    private fun handlerOpenSdkError(it: Throwable?) {
        if (it is OpenServiceException) {
            Logger.e("OpenSdk channel=${it.channel} errorCode=${it.errorCode} errorMsg=${it.errorMsg}")
        }
        ToastHelper.showMessage(it?.message.orEmpty())

    }


    /**
     * 支付宝分享
     */
    private fun shareAliPay() {
        var data = AliPayShareEntity.newShareImageUrl("https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg")
        GGFactory
                .getMethod<AliPayShareMethod>(AliPayShareMethod::class.java)
                .share(viewInterface.activity, data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareAliPay--"))
                .addTo(lifecycleComposite)

    }


    /**
     * 支付宝登录授权
     */
    private fun loginAliPay() {
        GGFactory
                .getMethod<AliPayOAuthMethod>(AliPayOAuthMethod::class.java)
                .oAuth(viewInterface.activity, "....需要填入服务端接口返回的加密认证商户信息....")
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("loginAliPay"))
                .addTo(lifecycleComposite)

    }


    /**
     * 获取内存读取权限
     * @param successFunc Function1<Boolean, Unit>
     */
    private fun runWithPermissionsStorage(successFunc: () -> Unit) {
        viewInterface.activity.requestStoragePermissions {
            if (it) {
                successFunc.invoke()
            } else {
                ToastHelper.showMessage("需要取得相关权限，才能操作")
            }
        }
    }

    /**
     * Line 分享
     * @param data 待分享数据
     */
    private fun shareLine(data: LineShareEntity) {
        GGFactory
                .getMethod<LineShareMethod>(LineShareMethod::class.java)
                .share(viewInterface.activity, data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareLine--"))
                .addTo(lifecycleComposite)
    }


    /**
     * line 登录
     */
    private fun loginLine() {
        GGFactory
                .getMethod<LineOAuthMethod>(LineOAuthMethod::class.java)
                .oAuth(viewInterface.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--loginLine--"))
                .addTo(lifecycleComposite)
    }

    /**
     * twitter 分享
     */
    private fun twitterShare() {
        RxImagePicker
                .get()
                .pickerMode(RxImagePickerMode.PHOTO_PICK)
                .start(context)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    val data = TwitterShareEntity("Twitter share test")
                    data.webPageUrl = "https://www.cnblogs.com/tangZH/p/8206569.html"
                    data.imageFileUri = URIs.fileToUri(context, it, BuildConfig.APPLICATION_ID)
                    data
                }
                .flatMap {
                    GGFactory
                            .getMethod<TwitterShareMethod>(TwitterShareMethod::class.java)
                            .share(viewInterface.activity, it)
                }
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable())
                .addTo(lifecycleComposite)
    }


    /**
     * twitter Login
     */
    private fun loginTwitter() {
        GGFactory
                .getMethod<TwitterOAuthMethod>(TwitterOAuthMethod::class.java)
                .oAuth(viewInterface.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--loginTwitter--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 分享到微信朋友圈
     */
    private fun shareWXTimeline() {
        //如果封面图是网络图片，应该先下载后，获得bitmap再调起分享
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObservable(imageUrl)
                .map {
                    newShareWxWebPageEntity(WXShareScene.WX_SCENE_TIMELINE, it)
                }
                .flatMap {
                    GGFactory
                            .getMethod<WXShareMethod>(WXShareMethod::class.java)
                            .share(viewInterface.activity, it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareWXFriend--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 分享微信小程序
     */
    private fun shareWxMiniApp() {
        //如果封面图是网络图片，应该先下载后，获得bitmap再调起分享
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObservable(imageUrl)
                .map {
                    newShareWxMiniAppEntity(it)
                }
                .flatMap {
                    GGFactory
                            .getMethod<WXShareMethod>(WXShareMethod::class.java)
                            .share(viewInterface.activity, it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareWXFriend--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 下载图片
     * @param imageUrl String 图片链接
     * @return Observable<Bitmap>
     */
    private fun downloadImageObservable(imageUrl: String): Observable<Bitmap> {
        ImageHelper
                .get()
                .getEngine()
                .downloadImage(context, ImageParam(imageUrl, OpenConstants.BITMAP_WIDTH, OpenConstants.BITMAP_HEIGHT))
        return Observable
                .just(imageUrl)
                .subscribeOn(Schedulers.io())
                .map {
                    ImageHelper.get().getEngine().downloadImage(context, ImageParam(imageUrl, OpenConstants.BITMAP_WIDTH, OpenConstants.BITMAP_HEIGHT))
                }
    }


    /**
     * 创建一个微信分享小程序数据类
     * @param bitmap 位图
     * @return
     */
    private fun newShareWxMiniAppEntity(bitmap: Bitmap): WXShareEntity {
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
     * 分享给微信好友
     */
    private fun shareWXFriend() {
        //如果封面图是网络图片，应该先下载后，获得bitmap再调起分享
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObservable(imageUrl)
                .map {
                    newShareWxWebPageEntity(WXShareScene.WX_SCENE_SESSION, it)
                }
                .flatMap {
                    GGFactory
                            .getMethod<WXShareMethod>(WXShareMethod::class.java)
                            .share(viewInterface.activity, it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareWXFriend--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 创建一个微信分享网页数据类
     * @param bitmap 位图
     * @return
     */
    private fun newShareWxWebPageEntity(@WXShareScene scene: Int, bitmap: Bitmap): WXShareEntity {
        return WXShareEntity.newShareWebPage(scene = scene,
                webPageUrl = "https://www.baidu.com/",
                thumbOriginalBitmap = bitmap,
                title = "百度一下",
                description = "测试微信分享")
    }


    /**
     * QQ 分享
     */
    private fun shareQQ() {
        GGFactory
                .getMethod<QQShareMethod>(QQShareMethod::class.java)
                .share(viewInterface.activity, QQShareEntity.newShareWebPage("http://ganguo.io/", "测试", "测试"))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareQQ--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 分享到QQ空间
     */
    private fun shareQQZone() {
        GGFactory
                .getMethod<QQZoneShareMethod>(QQZoneShareMethod::class.java)
                .share(viewInterface.activity, QQZoneShareEntity.newWebPage("http://ganguo.io/", "测试"))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareQQZone--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 微博分享
     */
    private fun shareSina() {
        //如果图是网络图片，应该先下载后，获得bitmap再调起分享
        var imageUrl = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        downloadImageObservable(imageUrl)
                .map {
                    SinaShareEntity.newShareImage(it, "新浪微博分享测试")
                }
                .flatMap {
                    GGFactory.getMethod<SinaShareMethod>(SinaShareMethod::class.java)
                            .share(viewInterface.activity, it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareSina--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 分享回调处理
     * @param it
     */
    private fun handlerShareResult(it: OpenResult<*>) {
        var hint = when {
            it.status == OpenStatus.SUCCESS -> "分享完成"
            it.status == OpenStatus.CANCEL -> "分享取消"
            else -> it.message
        }
        ToastHelper.showMessage(hint)
        Logger.e("${it.channel} Share OpenResult：${it}")
    }


    /**
     * 微信登录
     */
    private fun loginWX() {
        GGFactory
                .getMethod<WXOAuthMethod>(WXOAuthMethod::class.java)
                .oAuth(viewInterface.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--loginWX--"))
                .addTo(lifecycleComposite)
    }


    /**
     * QQ 登录
     */
    private fun loginQQ() {
        GGFactory
                .getMethod<QQOAuthMethod>(QQOAuthMethod::class.java)
                .oAuth(viewInterface.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--loginQQ--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 微博登录
     */
    private fun loginSina() {
        GGFactory
                .getMethod<SinaOAuthMethod>(SinaOAuthMethod::class.java)
                .oAuth(viewInterface.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--loginSina--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 登录回调处理
     * @param it
     */
    private fun handlerLoginResult(it: OpenResult<*>) {
        var hint = when {
            it.status == OpenStatus.SUCCESS -> "登录成功"
            it.status == OpenStatus.CANCEL -> "登录取消"
            else -> it.message
        }
        ToastHelper.showMessage(hint)
        Logger.e("${it.channel} Login OpenResult：message：${it.message}")
        Logger.e("${it.channel} Login OpenResult：result：${it.result}")
    }


    /**
     * Google + 账号认证
     */
    private fun loginGoogle() {
        GGFactory
                .getMethod<GoogleOAuthMethod>(GoogleOAuthMethod::class.java)
                .oAuth(viewInterface.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--loginGoogle--"))
                .addTo(lifecycleComposite)
    }


    /**
     * FaceBook 分享
     */
    private fun shareFacebook() {
        var data = FacebookShareEntity.newShareWebPage("https://www.jianshu.com/p/87a35e971b5c", "【干货】前端开发者最常用的六款IDE")
        GGFactory
                .getMethod<FacebookShareMethod>(FacebookShareMethod::class.java)
                .share(viewInterface.activity, data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerShareResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--shareFacebook--"))
                .addTo(lifecycleComposite)
    }


    /**
     * FaceBook 登录
     *
     */
    private fun loginFacebook() {
        GGFactory
                .getMethod<FacebookOAuthMethod>(FacebookOAuthMethod::class.java)
                .oAuth(viewInterface.activity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerLoginResult(it)
                }
                .doOnError {
                    handlerOpenSdkError(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--loginFacebook--"))
                .addTo(lifecycleComposite)
    }

    /**
     * WhatApp分享，只能文本或者链接
     */
    private fun shareToWhatApp() {
        if (!AppInstalls.isWhatAppInstalled(context)) {
            ToastHelper.showMessage("请先安装WhatApp")
            return
        }
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        //Target whatsapp:
        shareIntent.setPackage("com.whatsapp")
        //Add text and then Image URI
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.baidu.com")
        viewInterface.activity.startActivity(Intent.createChooser(shareIntent, "分享到WhatApp"))
    }

    /**
     * Telegram分享
     */
    private fun shareToTelegram() {
        if (!AppInstalls.isTelegramInstalled(context)) {
            ToastHelper.showMessage("请先安装Telegram")
            return
        }
        val vIt = Intent("android.intent.action.SEND")
        vIt.setPackage("org.telegram.messenger")
        vIt.type = "text/plain"
        vIt.putExtra(Intent.EXTRA_TEXT, "测试分享" + " " + "http://www.baidu.com")
        viewInterface.activity.startActivity(vIt)
    }


}
