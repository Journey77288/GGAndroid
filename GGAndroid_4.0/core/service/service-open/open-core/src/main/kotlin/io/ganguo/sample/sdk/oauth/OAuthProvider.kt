package io.ganguo.sample.sdk.oauth

import io.ganguo.factory.IProvider

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 认证服务生产类
 * </pre>
 */
interface OAuthProvider<T : OAuthService<*, *>> : IProvider<T>