package io.ganguo.factory.exception

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/05
 *     desc   : 第三方服务异常
 * </pre>
 * @property [errorCode] 错误码
 * @property [errorMsg] 错误提示
 * @property [cause] 自定义已异常
 */
open class ServiceException(var errorCode: Int = 0, var errorMsg: String = "", cause: Throwable? = null) : Throwable(message = errorMsg, cause = cause){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceException

        if (errorCode != other.errorCode) return false
        if (errorMsg != other.errorMsg) return false

        return true
    }

    override fun hashCode(): Int {
        var result = errorCode
        result = 31 * result + errorMsg.hashCode()
        return result
    }

    override fun toString(): String {
        return "ServiceException(errorCode=$errorCode, errorMsg='$errorMsg')"
    }


}