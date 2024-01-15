package io.ganguo.google

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.ganguo.google.auth.GoogleOAuthService
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.open.sdk.oauth.OAuthMethod
import io.ganguo.open.sdk.oauth.OAuthProvider
import io.ganguo.open.sdk.oauth.OAuthService
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : Google登录，相关类别名
 * </pre>
 * @see [GoogleOAuthResult] Google登录返回结果类型
 * @see [GoogleOAuthObservable] Google登录[Observable]返回类型
 * @see [IGoogleOAuthProvider] Google登录，Provider父类
 * @see [AGoogleOAuthService] Google登录，Service父类
 * @see [AGoogleOAuthMethod] Google登录，Method父类
 */

typealias GoogleOAuthResult = OpenResult<GoogleSignInAccount>

typealias GoogleOAuthObservable = Observable<GoogleOAuthResult>

typealias  AGoogleOAuthService = OAuthService<GoogleOAuthResult, GoogleOAuthObservable>

typealias  IGoogleOAuthProvider = OAuthProvider<GoogleOAuthService>

typealias  AGoogleOAuthMethod = OAuthMethod<Any?, GoogleOAuthResult, GoogleOAuthObservable, GoogleOAuthService, IGoogleOAuthProvider>