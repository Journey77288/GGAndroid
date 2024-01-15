package io.ganguo.sina

import com.sina.weibo.sdk.auth.Oauth2AccessToken
import io.ganguo.sample.sdk.AShareMethod
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.oauth.OAuthMethod
import io.ganguo.sample.sdk.oauth.OAuthProvider
import io.ganguo.sample.sdk.oauth.OAuthService
import io.ganguo.sample.sdk.share.ShareProvider
import io.ganguo.sina.auth.SinaOAuthProvider
import io.ganguo.sina.auth.SinaOAuthService
import io.ganguo.sina.share.SinaShareEntity
import io.ganguo.sina.share.SinaShareProvider
import io.ganguo.sina.share.SinaShareService
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 别名
 * </pre>
 * @see [SinaOAuthResult]新浪微博登录返回结果
 * @see [SinaOAuthObservable] 新浪微博登录授权，结果回调Observable
 * @see [ASinaOAuthService] 新浪微博登录授权，Service父类
 * @see [ISinaOAuthProvider] 新浪微博登录授权，Provider父类
 * @see [ASinaOAuthMethod]新浪微博登录授权，Method父类
 * @see [ISinaShareProvider] 新浪微博分享，Provider父类
 * @see [ASinaShareMethod] 新浪微博分享，Method父类
 */

typealias SinaOAuthResult = OpenResult<Oauth2AccessToken?>

typealias SinaOAuthObservable = Observable<SinaOAuthResult>

typealias ASinaOAuthService = OAuthService<SinaOAuthResult, SinaOAuthObservable>

typealias ISinaOAuthProvider = OAuthProvider<SinaOAuthService>

typealias ASinaOAuthMethod = OAuthMethod<Any?, SinaOAuthResult, SinaOAuthObservable, SinaOAuthService, SinaOAuthProvider>

typealias ISinaShareProvider = ShareProvider<SinaShareService>

typealias ASinaShareMethod = AShareMethod<SinaShareEntity, ShareResultObservable, SinaShareService, SinaShareProvider>