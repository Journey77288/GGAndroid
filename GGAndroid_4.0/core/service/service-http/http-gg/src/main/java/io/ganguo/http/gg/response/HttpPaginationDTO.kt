package io.ganguo.http.gg.response

import kotlinx.serialization.Serializable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/01/04
 *     desc   : 根据甘果API规范约定，有分页数据结构，link:(https://gitlab.ganguomob.com/ganguo_group/standard/blob/master/%E7%94%98%E6%9E%9C%E5%90%8E%E7%AB%AFAPI%E6%8E%A5%E5%8F%A3%E8%A7%84%E8%8C%83%E6%96%87%E6%A1%A3.md)
 * </pre>
 * @param T 分页数据类型
 * @param E 额外扩展字段类型
 * @property content T 分页数据字段
 * @property extra E 额外扩展字段
 * @property pagination HttpPagination 当前分页参数
 * @constructor
 */
@Serializable
data class HttpPaginationDTO<T, E>(
        val content: T,
        val extra: E?,
        val pagination: HttpPagination
)