package io.ganguo.sample.entity

import kotlinx.serialization.Serializable

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/13
 *   @desc   : 图片链接
 * </pre>
 */
@Serializable
data class Urls(
        val full: String,
        val raw: String,
        val regular: String,
        val small: String,
        val thumb: String
)