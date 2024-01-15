package io.ganguo.alipay.share

import android.app.Activity
import com.alipay.share.sdk.openapi.*
import io.ganguo.alipay.AliPayConstants
import io.ganguo.alipay.AliPayHandler
import io.ganguo.alipay.AliPayShareResultObservable
import io.ganguo.alipay.IAliPayShareService
import io.ganguo.alipay.annotation.AliPayShareType
import io.ganguo.alipay.entity.AliPayShareEntity
import io.ganguo.sample.sdk.OpenHelper
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.exception.OpenServiceException
import java.io.File
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝分享服务
 * </pre>
 */
class AliPayShareService(activity: Activity, appId: String, var shareEntity: AliPayShareEntity) : IAliPayShareService() {
    private var weakActivity: WeakReference<Activity> = WeakReference(activity)
    private var aliPayAPI: IAPApi

    init {
        aliPayAPI = APAPIFactory.createZFBApi(weakActivity.get()!!, appId, false)
    }


    /**
     * 调起支付宝分享
     * @return [ShareResultObservable]
     */
    override fun startService(): AliPayShareResultObservable {
        val webReq = SendMessageToZFB.Req()
        webReq.message = buildAPMediaMessage()
        webReq.transaction = formatTransaction()
        aliPayAPI.sendReq(webReq)
        return resultObserver
    }

    /**
     * 支付宝SDK异常检测
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        var exception: OpenServiceException? = null
        if (!AliPayConstants.ALI_PAY_SDK_IS_INIT) {
            val msg = "Please initialize AliPay sdk in Application!!!"
            val errorCode = AliPayConstants.ErrorCode.ALIPAY_SDK_NOT_INIT
            exception = OpenServiceException(OpenChannel.ALI_PAY, errorCode, msg)
        } else if (exception == null) {
            exception = AliPayHandler.checkException(weakActivity.get()!!)
        }
        return exception
    }


    /**
     * 格式化分享类型
     * @return [ShareResultObservable]
     */
    private fun formatTransaction(): String {
        var type = when (shareEntity.shareType) {
            AliPayShareType.TYPE_IMAGE_URL,
            AliPayShareType.TYPE_IMAGE_LOCAL_PATH,
            AliPayShareType.TYPE_IMAGE_BITMAP -> {
                AliPayShareType.TYPE_IMAGE
            }
            else -> {
                shareEntity.shareType
            }
        }
        return OpenHelper.buildTransaction(type)
    }


    /**
     * 创建分享数据对象
     * @return [APMediaMessage]
     */
    private fun buildAPMediaMessage(): APMediaMessage {
        return when (shareEntity.shareType) {
            AliPayShareType.TYPE_WEB_PAGE -> {
                newWebPageAPMediaMessage()
            }
            AliPayShareType.TYPE_TEXT -> {
                newTextAPMediaMessage()
            }
            AliPayShareType.TYPE_IMAGE_LOCAL_PATH -> {
                newImageLocalPathAPMediaMessage()
            }
            AliPayShareType.TYPE_IMAGE_BITMAP -> {
                newImageBitmapAPMediaMessage()
            }
            else -> {
                newImageUrlAPMediaMessage()
            }
        }
    }

    /**
     * 网页分享数据对象
     * @return [APMediaMessage]
     */
    private fun newWebPageAPMediaMessage(): APMediaMessage {
        val webPage = APWebPageObject()
        val message = APMediaMessage()
        webPage.webpageUrl = shareEntity.webPageUrl
        message.title = shareEntity.title
        message.description = shareEntity.description
        message.mediaObject = webPage
        message.thumbUrl = shareEntity.thumbUrl
        return message
    }


    /**
     * 文字分享数据对象
     * @return [APMediaMessage]
     */
    private fun newTextAPMediaMessage(): APMediaMessage {
        val text = APTextObject()
        val message = APMediaMessage()
        text.text = shareEntity.text
        message.mediaObject = text
        return message
    }


    /**
     * 图片链接分享数据对象
     * @return [APMediaMessage]
     */
    private fun newImageUrlAPMediaMessage(): APMediaMessage {
        val image = APImageObject()
        image.imageUrl = shareEntity.imageUrl
        val message = APMediaMessage()
        message.mediaObject = image
        return message
    }

    /**
     * Bitmap位图分享数据对象
     * @return [APMediaMessage]
     */
    private fun newImageBitmapAPMediaMessage(): APMediaMessage {
        val image = APImageObject(shareEntity.imageBitmap)
        val message = APMediaMessage()
        message.mediaObject = image
        return message
    }

    /**
     * 本地图片链接分享数据对象
     * @return [APMediaMessage]
     */
    private fun newImageLocalPathAPMediaMessage(): APMediaMessage {
        if (shareEntity.imageLocalPath.isNullOrEmpty() ||
                !File(shareEntity.imageLocalPath).exists()) {
            throw RuntimeException("onShareImage：File does not exist！")
        }
        val image = APImageObject()
        val message = APMediaMessage()
        image.imagePath = shareEntity.imageLocalPath
        message.mediaObject = image
        return message
    }
}