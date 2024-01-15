package io.ganguo.http.gg.response

import kotlinx.serialization.Serializable

/**
 * <pre>
 *     author : leo
 *     time   : 2018/7/17
 *     desc   : 根据 甘果API 规范约定数据结构，link:(https://gitlab.ganguomob.com/ganguo_group/standard/blob/master/%E7%94%98%E6%9E%9C%E5%90%8E%E7%AB%AFAPI%E6%8E%A5%E5%8F%A3%E8%A7%84%E8%8C%83%E6%96%87%E6%A1%A3.md)
 * </pre>
 * @param T
 * @property code Int 错误码，需要同后台约定不同情况
 * @property `data` T 结果，成功时返回
 * @property message String 错误消息，成功时，一般默认不返回
 * @property status String 对应状态类型，success/error
 * @constructor
 */
@Serializable
data class HttpResponse<T>(
        val code: Int,
        val data: T?,
        val message: String,
        val status: String
)