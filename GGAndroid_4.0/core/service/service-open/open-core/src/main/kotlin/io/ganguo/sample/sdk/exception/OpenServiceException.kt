package io.ganguo.sample.sdk.exception

import io.ganguo.factory.exception.ServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   : 第三方sdk服务异常
 * </pre>
 * @property [channel] 调用渠道，具体数值参考
 * @property [errorCode] 错误码
 * @property [errorMsg] 错误消息提
 * @property [cause] 自定义异常
 */
open class OpenServiceException( var channel: String, errorCode: Int, errorMsg: String, cause: Throwable? = null)
    : ServiceException(errorCode, errorMsg, cause) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as OpenServiceException

        if (channel != other.channel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + channel.hashCode()
        return result
    }

    override fun toString(): String {
        return "OpenServiceException(channel='$channel')"
    }


}