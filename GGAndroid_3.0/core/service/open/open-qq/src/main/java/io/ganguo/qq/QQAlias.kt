package io.ganguo.qq

import io.ganguo.open.sdk.AShareMethod
import io.ganguo.open.sdk.ShareResultObservable
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.open.sdk.oauth.OAuthMethod
import io.ganguo.open.sdk.oauth.OAuthProvider
import io.ganguo.open.sdk.oauth.OAuthService
import io.ganguo.open.sdk.share.ShareProvider
import io.ganguo.qq.auth.QQOAuthProvider
import io.ganguo.qq.auth.QQOAuthService
import io.ganguo.qq.entity.QQUserEntity
import io.ganguo.qq.entity.QQShareEntity
import io.ganguo.qq.entity.QQZoneShareEntity
import io.ganguo.qq.share.qq.QQShareProvider
import io.ganguo.qq.share.qq.QQShareService
import io.ganguo.qq.share.zone.QQZoneShareProvider
import io.ganguo.qq.share.zone.QQZoneShareService
import io.reactivex.Observable

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



