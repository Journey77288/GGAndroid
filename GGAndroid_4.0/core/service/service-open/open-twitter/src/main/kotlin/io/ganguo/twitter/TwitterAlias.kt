package io.ganguo.twitter

import com.twitter.sdk.android.core.TwitterSession
import io.ganguo.sample.sdk.ShareResult
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.oauth.OAuthMethod
import io.ganguo.sample.sdk.oauth.OAuthProvider
import io.ganguo.sample.sdk.oauth.OAuthService
import io.ganguo.sample.sdk.share.ShareMethod
import io.ganguo.sample.sdk.share.ShareProvider
import io.ganguo.sample.sdk.share.ShareService
import io.ganguo.twitter.auth.TwitterOAuthProvider
import io.ganguo.twitter.auth.TwitterOAuthService
import io.ganguo.twitter.entity.TwitterShareEntity
import io.ganguo.twitter.share.TwitterShareProvider
import io.ganguo.twitter.share.TwitterShareService
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   :
 * </pre>
 * @see [TwitterOAuthResult] Twitter登录返回值类型
 * @see [TwitterOAuthObservable] Twitter登录Observable类型
 * @see [ATwitterOAthService] Twitter登录，Service父类
 * @see [ITwitterOAthProvider] Twitter登录，Provider父类
 * @see [ATwitterOAthMethod] Twitter登录，Method父类
 * @see [ATwitterShareService] Twitter分享，Method父类
 * @see [ITwitterShareProvider]Twitter分享，Provider父类
 * @see [ATwitterShareMethod] Twitter分享，Method父类
 */
typealias TwitterOAuthResult = OpenResult<TwitterSession>

typealias TwitterOAuthObservable = Observable<TwitterOAuthResult>

typealias ATwitterOAthService = OAuthService<TwitterOAuthResult, TwitterOAuthObservable>

typealias ITwitterOAthProvider = OAuthProvider<TwitterOAuthService>

typealias ATwitterOAthMethod = OAuthMethod<Any?, TwitterOAuthResult, TwitterOAuthObservable, TwitterOAuthService, TwitterOAuthProvider>

typealias ATwitterShareService = ShareService<ShareResult, ShareResultObservable>

typealias ITwitterShareProvider = ShareProvider<TwitterShareService>

typealias ATwitterShareMethod = ShareMethod<TwitterShareEntity, ShareResult, ShareResultObservable, TwitterShareService, TwitterShareProvider>