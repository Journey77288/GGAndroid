package io.ganguo.qq.entity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ 登录成功后，接口数据
 * </pre>
 */
data class QQUserEntity(var openId: String, var accessToken: String, var expiresIn: String)