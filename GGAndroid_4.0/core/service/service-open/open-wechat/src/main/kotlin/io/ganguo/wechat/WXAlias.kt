package io.ganguo.wechat

import io.ganguo.sample.sdk.AShareMethod
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.oauth.OAuthMethod
import io.ganguo.sample.sdk.oauth.OAuthService
import io.ganguo.sample.sdk.share.ShareProvider
import io.ganguo.wechat.auth.WXOAuthProvider
import io.ganguo.wechat.auth.WXOAuthService
import io.ganguo.wechat.share.WXShareEntity
import io.ganguo.wechat.share.WXShareProvider
import io.ganguo.wechat.share.WXShareService
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 别名
 * </pre>
 * @see [WXOAuthResult] 微信登录返回值类型
 * @see [WXOAuthObservable] 微信登录Observable类型
 * @see [AWXOAuthService]微信登录，Service父类
 * @see [AWXAuthMethod] 微信登录，Method父类
 * @see [AWXShareMethod] 微信分享，Method父类
 * @see [IWXShareProvider] 微信分享，Provider父类
 *
 */

typealias WXOAuthResult = OpenResult<String?>

typealias WXOAuthObservable = Observable<WXOAuthResult>

internal typealias AWXOAuthService = OAuthService<WXOAuthResult, WXOAuthObservable>

internal typealias AWXAuthMethod = OAuthMethod<Any?, WXOAuthResult, WXOAuthObservable, WXOAuthService, WXOAuthProvider>

internal typealias IWXShareProvider = ShareProvider<WXShareService>

internal typealias AWXShareMethod = AShareMethod<WXShareEntity, ShareResultObservable, WXShareService, WXShareProvider>



