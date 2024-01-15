package io.ganguo.line

import com.linecorp.linesdk.auth.LineLoginResult
import io.ganguo.line.auth.LineOAuthProvider
import io.ganguo.line.auth.LineOAuthService
import io.ganguo.line.share.LineShareEntity
import io.ganguo.line.share.LineShareProvider
import io.ganguo.line.share.LineShareService
import io.ganguo.open.sdk.ShareResult
import io.ganguo.open.sdk.ShareResultObservable
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.open.sdk.oauth.OAuthMethod
import io.ganguo.open.sdk.oauth.OAuthProvider
import io.ganguo.open.sdk.oauth.OAuthService
import io.ganguo.open.sdk.share.ShareMethod
import io.ganguo.open.sdk.share.ShareProvider
import io.reactivex.Observable

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