package io.ganguo.facebook

import com.facebook.login.LoginResult
import com.facebook.share.Sharer
import io.ganguo.facebook.auth.FacebookOAuthProvider
import io.ganguo.facebook.auth.FacebookOAuthService
import io.ganguo.facebook.entity.FacebookShareEntity
import io.ganguo.facebook.share.FacebookShareProvider
import io.ganguo.facebook.share.FacebookShareService
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.open.sdk.oauth.OAuthMethod
import io.ganguo.open.sdk.oauth.OAuthProvider
import io.ganguo.open.sdk.oauth.OAuthService
import io.ganguo.open.sdk.share.ShareMethod
import io.ganguo.open.sdk.share.ShareProvider
import io.ganguo.open.sdk.share.ShareService
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook相关类，别名
 * </pre>
 * @see [FBOAuthResult] Facebook登录返回值类型
 * @see [FBOAuthResultObservable] Facebook登录Observable类型
 * @see [AFBOAuthService]Facebook登录，Service父类
 * @see [IFBOAuthProvider]Facebook登录，Provider父类
 * @see [AFBOAuthMethod] Facebook登录，Method父类
 * @see [FBShareResult] Facebook分享返回值类型
 * @see [FBShareResultObservable] Facebook分享Observable类型
 * @see [AFBShareService]Facebook分享，Service父类
 * @see [IFBShareProvider]Facebook分享，Provider父类
 * @see [AFBShareMethod] Facebook分享，Method父类
 */
typealias FBOAuthResult = OpenResult<LoginResult?>

typealias FBOAuthResultObservable = Observable<FBOAuthResult>

typealias AFBOAuthService = OAuthService<FBOAuthResult, FBOAuthResultObservable>

typealias IFBOAuthProvider = OAuthProvider<FacebookOAuthService>

typealias AFBOAuthMethod = OAuthMethod<Any?, FBOAuthResult, FBOAuthResultObservable, FacebookOAuthService, FacebookOAuthProvider>

typealias FBShareResult = OpenResult<Sharer.Result?>

typealias FBShareResultObservable = Observable<FBShareResult>

typealias AFBShareService = ShareService<FBShareResult, FBShareResultObservable>

typealias IFBShareProvider = ShareProvider<FacebookShareService>

typealias AFBShareMethod = ShareMethod<FacebookShareEntity, FBShareResult, FBShareResultObservable, FacebookShareService, FacebookShareProvider>


