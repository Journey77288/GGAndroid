package io.ganguo.qq

import io.ganguo.sample.sdk.AShareMethod
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.oauth.OAuthMethod
import io.ganguo.sample.sdk.oauth.OAuthProvider
import io.ganguo.sample.sdk.oauth.OAuthService
import io.ganguo.sample.sdk.share.ShareProvider
import io.ganguo.qq.auth.QQOAuthProvider
import io.ganguo.qq.auth.QQOAuthService
import io.ganguo.qq.entity.QQUserEntity
import io.ganguo.qq.entity.QQShareEntity
import io.ganguo.qq.entity.QQZoneShareEntity
import io.ganguo.qq.share.qq.QQShareProvider
import io.ganguo.qq.share.qq.QQShareService
import io.ganguo.qq.share.zone.QQZoneShareProvider
import io.ganguo.qq.share.zone.QQZoneShareService
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : 别名
 * </pre>
 * @see [QQOAuthResult] QQ登录返回结果类型
 * @see [QQOAuthObservable] QQ登录[Observable]返回类型
 * @see [IQQAuthProvider] QQ登录，Provider父类
 * @see [AQQOAuthService] QQ登录，Service父类
 * @see [AQQAuthMethod] QQ登录，Method父类
 * @see [IQQShareProvider] QQ分享,Provider父类
 * @see [AQQShareMethod] QQ分享方法类
 */

typealias QQOAuthResult = OpenResult<QQUserEntity?>

typealias QQOAuthObservable = Observable<QQOAuthResult>

typealias AQQOAuthService = OAuthService<QQOAuthResult, QQOAuthObservable>

typealias IQQAuthProvider = OAuthProvider<QQOAuthService>

typealias AQQAuthMethod = OAuthMethod<Any?, QQOAuthResult, QQOAuthObservable, QQOAuthService, QQOAuthProvider>

typealias IQQShareProvider = ShareProvider<QQShareService>

typealias AQQShareMethod = AShareMethod<QQShareEntity, ShareResultObservable, QQShareService, QQShareProvider>

typealias IQQZoneShareProvider = ShareProvider<QQZoneShareService>

typealias AQQZoneShareMethod = AShareMethod<QQZoneShareEntity, ShareResultObservable, QQZoneShareService, QQZoneShareProvider>



