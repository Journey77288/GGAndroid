package io.ganguo.alipay

import com.alipay.share.sdk.openapi.BaseResp
import io.ganguo.alipay.auth.AliPayOAuthProvider
import io.ganguo.alipay.auth.AliPayOAuthService
import io.ganguo.alipay.entity.AliPayShareEntity
import io.ganguo.alipay.entity.AuthResult
import io.ganguo.alipay.share.AliPayShareProvider
import io.ganguo.alipay.share.AliPayShareService
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.oauth.OAuthMethod
import io.ganguo.sample.sdk.oauth.OAuthProvider
import io.ganguo.sample.sdk.oauth.OAuthService
import io.ganguo.sample.sdk.share.ShareMethod
import io.ganguo.sample.sdk.share.ShareProvider
import io.ganguo.sample.sdk.share.ShareService
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝登录、授权相关别名
 * </pre>
 * @see [AliPayOAuthResult]支付宝登录返回结果类型
 * @see [AliPayOAuthObservable] 支付宝登录授权，结果回调Observable
 * @see [AAliPayOAuthService] 支付宝登录授权，Service父类
 * @see [IAliPayOAuthProvider] 支付宝登录授权，Provider父类
 * @see [AAliPayOAuthMethod]支付宝登录授权，Method父类
 * @see [IAliPayShareProvider] 支付宝分享，Provider父类
 * @see [AAliPayShareMethod] 支付宝分享，Method父类
 * @see [AliPayShareResult] 支付宝分享返回结果类型
 * @see [AliPayShareResultObservable] 支付宝分享，结果回调Observable
 * @see [IAliPayShareService] 支付宝分享，Service父类
 */

typealias AliPayOAuthResult = OpenResult<AuthResult>

typealias AliPayOAuthObservable = Observable<AliPayOAuthResult>

typealias AAliPayOAuthService = OAuthService<AliPayOAuthResult, AliPayOAuthObservable>

typealias IAliPayOAuthProvider = OAuthProvider<AliPayOAuthService>

typealias AAliPayOAuthMethod = OAuthMethod<String, AliPayOAuthResult, AliPayOAuthObservable, AliPayOAuthService, AliPayOAuthProvider>

typealias IAliPayShareProvider = ShareProvider<AliPayShareService>

typealias AliPayShareResult = OpenResult<BaseResp?>

typealias AliPayShareResultObservable = Observable<AliPayShareResult>

typealias IAliPayShareService = ShareService<AliPayShareResult, AliPayShareResultObservable>

typealias AAliPayShareMethod = ShareMethod<AliPayShareEntity, AliPayShareResult, AliPayShareResultObservable, AliPayShareService, AliPayShareProvider>
