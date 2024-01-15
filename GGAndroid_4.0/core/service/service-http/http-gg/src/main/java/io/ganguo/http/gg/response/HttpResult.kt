package io.ganguo.http.gg.response

import kotlinx.serialization.Serializable

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/24
 *   @desc   : Wrapper Http Result
 * </pre>
 */
@Serializable
data class HttpResult<T>(var data: T?)