package io.ganguo.wechat.annotation

import androidx.annotation.IntDef
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/31
 *     desc   : 微信小程序版本类型
 * </pre>
 * @property TYPE_RELEASE 正式版本
 * @property TYPE_PREVIEW 预览版本
 * @property TYPE_TEST 测试版本
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(WXMiniProgramVersionType.TYPE_RELEASE, WXMiniProgramVersionType.TYPE_PREVIEW, WXMiniProgramVersionType.TYPE_TEST)
annotation class WXMiniProgramVersionType {

    companion object {
        const val TYPE_RELEASE = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE
        const val TYPE_PREVIEW = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW
        const val TYPE_TEST = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST
    }
}