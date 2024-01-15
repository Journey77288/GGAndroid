package io.ganguo.http.gg.response

import kotlinx.serialization.Serializable

/**
 * <pre>
 *     author : leo
 *     time   : 2018/11/01
 *     desc   : 分页数据
 * </pre>
 * @property last Int 最后一页，页码
 * @property page Int 当前页码
 * @property size Int 每一页的数量
 * @property total Int 总页数
 * @constructor
 */

@Serializable
data class HttpPagination(
        val last: Int,
        val page: Int,
        val size: Int,
        val total: Int
)