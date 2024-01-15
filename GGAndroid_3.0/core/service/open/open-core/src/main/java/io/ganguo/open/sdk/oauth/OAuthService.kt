package io.ganguo.open.sdk.oauth

import io.ganguo.factory.service.ResultEmitterService

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 登录认证服务抽象类
 * </pre>
 * @property [ResultObservable] 返回数据类型
 */
abstract class OAuthService<Result, ResultObservable> : ResultEmitterService<Result, ResultObservable>() {

    /**
     * 登录认证
     * @return 返回一个订阅对象
     */
    internal fun oAuth(): ResultObservable {
        return sendErrorOrStartService()
    }
}