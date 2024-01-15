package io.ganguo.twitter

import com.twitter.sdk.android.core.TwitterSession
import io.ganguo.open.sdk.ShareResult
import io.ganguo.open.sdk.ShareResultObservable
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.open.sdk.oauth.OAuthMethod
import io.ganguo.open.sdk.oauth.OAuthProvider
import io.ganguo.open.sdk.oauth.OAuthService
import io.ganguo.open.sdk.share.ShareMethod
import io.ganguo.open.sdk.share.ShareProvider
import io.ganguo.open.sdk.share.ShareService
import io.ganguo.twitter.auth.TwitterOAuthProvider
import io.ganguo.twitter.auth.TwitterOAuthService
import io.ganguo.twitter.entity.TwitterShareEntity
import io.ganguo.twitter.share.TwitterShareProvider
import io.ganguo.twitter.share.TwitterShareService
import io.reactivex.Observable

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