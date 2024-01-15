package io.ganguo.sample.sdk.annotation

import androidx.annotation.IntDef
import io.ganguo.sample.sdk.annotation.OpenStatus.Companion.CANCEL
import io.ganguo.sample.sdk.annotation.OpenStatus.Companion.FAILED
import io.ganguo.sample.sdk.annotation.OpenStatus.Companion.SUCCESS

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 调用结果状态
 * </pre>
 * @property SUCCESS 调用成功
 * @property CANCEL 调用取消
 * @property FAILED 调用失败
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(SUCCESS, CANCEL, FAILED)
annotation class OpenStatus {

    companion object {
        const val SUCCESS: Int = 0
        const val CANCEL: Int = 2
        const val FAILED: Int = 3
    }
}