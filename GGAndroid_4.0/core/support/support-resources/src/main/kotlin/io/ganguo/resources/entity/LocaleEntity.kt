package io.ganguo.resources.entity

import kotlinx.serialization.Serializable

/**
 * <pre>
 *     author : lucas
 *     time   : 2023/11/23
 *     desc   : 多语言 实体类
 * </pre>
 */
@Serializable
data class LocaleEntity(val language: String, val country: String ?= "")