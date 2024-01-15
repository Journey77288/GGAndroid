package io.ganguo.open.sdk.entity

import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 返回结果
 * </pre>
 * @property channel 调用渠道，具体参考[OpenChannel]
 * @property status 调用状态，具体参考[OpenStatus]
 * @property message 错误消息，默认为空
 * @property result 返回结果
 */
data class OpenResult<T : Any?>(@OpenChannel var channel: String,
                                @OpenStatus var status: Int,
                                var message: String? = "",
                                var result: T? = null) {

    companion object {

        /**
         * 创建一个发生错误 OpenResult
         * @param channel String
         * @param message String
         * @return null
         */
        @JvmStatic
        fun <T> newFailedResult(@OpenChannel channel: String, message: String): OpenResult<T> {
            return OpenResult(channel, OpenStatus.FAILED, message)
        }
    }
}