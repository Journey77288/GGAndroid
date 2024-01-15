package io.ganguo.qq.annotation

import androidx.annotation.IntDef

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : QQ小程序版本类型
 * </pre>\
 * @property TYPE_RELEASE 正式版本
 * @property TYPE_PREVIEW 预览版本
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(QQMiniProgramVersionType.TYPE_RELEASE, QQMiniProgramVersionType.TYPE_PREVIEW)
annotation class QQMiniProgramVersionType {
    companion object {
        const val TYPE_RELEASE = 3
        const val TYPE_PREVIEW = 1
    }
}