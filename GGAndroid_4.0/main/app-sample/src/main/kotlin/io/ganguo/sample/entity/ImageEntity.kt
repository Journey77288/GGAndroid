package io.ganguo.sample.entity

import kotlinx.serialization.Serializable

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/13
 *   @desc   : 图片数据实体类
 * </pre>
 */
@Serializable
data class ImageEntity(
        val color: String,
        val height: Int,
        val urls: Urls,
        val width: Int
)
