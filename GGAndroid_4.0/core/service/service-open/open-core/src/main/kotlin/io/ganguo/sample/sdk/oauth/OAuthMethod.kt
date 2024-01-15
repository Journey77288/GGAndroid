package io.ganguo.sample.sdk.oauth

import androidx.fragment.app.FragmentActivity
import io.ganguo.factory.method.Method

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 登录认证
 * </pre>
 */
abstract class OAuthMethod<Param, Result, ResultObservable, Service : OAuthService<Result, ResultObservable>, Provider : OAuthProvider<Service>> :
        Method<Result, ResultObservable,
                Service,
                Provider>() {

    /**
     * 登录
     *
     * @param activity activity引用
     */
    open fun oauth(activity: FragmentActivity): ResultObservable {
        return oauth(activity, null)
    }

    /**
     * @param activity activity引用
     * @param p 生成Service参数，根据sdk场景场景传参，非必要参数
     */
    open fun oauth(activity: FragmentActivity, p: Param?): ResultObservable {
        return newOAuthProvider(activity, p).let {
            asService(activity, it).oAuth()
        }
    }

    /**
     * create Provider
     * @return OProvider
     */
    protected abstract fun newOAuthProvider(activity: FragmentActivity, p: Param?): Provider

}
