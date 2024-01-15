package io.ganguo.line

import com.linecorp.linesdk.auth.LineLoginResult
import io.ganguo.line.auth.LineOAuthProvider
import io.ganguo.line.auth.LineOAuthService
import io.ganguo.line.share.LineShareEntity
import io.ganguo.line.share.LineShareProvider
import io.ganguo.line.share.LineShareService
import io.ganguo.sample.sdk.ShareResult
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.oauth.OAuthMethod
import io.ganguo.sample.sdk.oauth.OAuthProvider
import io.ganguo.sample.sdk.oauth.OAuthService
import io.ganguo.sample.sdk.share.ShareMethod
import io.ganguo.sample.sdk.share.ShareProvider
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : Google登录，相关类别名
 * </pre>
 * @see [LineOAuthResult] Google登录返回结果类型
 * @see [LineOAuthObservable] Google登录[Observable]返回类型
 * @see [ILineOAuthProvider] Google登录，Provider父类
 * @see [ALineOAuthService] Google登录，Service父类
 * @see [ALineOAuthMethod] Google登录，Method父类
 */

typealias LineOAuthResult = OpenResult<LineLoginResult>

typealias LineOAuthObservable = Observable<LineOAuthResult>

typealias  ALineOAuthService = OAuthService<LineOAuthResult, LineOAuthObservable>

typealias  ILineOAuthProvider = OAuthProvider<LineOAuthService>

typealias  ALineOAuthMethod = OAuthMethod<Any?, LineOAuthResult, LineOAuthObservable, LineOAuthService, LineOAuthProvider>

typealias ILineShareProvider = ShareProvider<LineShareService>

typealias ALineShareMethod = ShareMethod<LineShareEntity, ShareResult, ShareResultObservable, LineShareService, LineShareProvider>