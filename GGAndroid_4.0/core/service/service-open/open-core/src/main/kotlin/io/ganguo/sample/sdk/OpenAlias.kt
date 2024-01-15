package io.ganguo.sample.sdk

import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sample.sdk.share.ShareMethod
import io.ganguo.sample.sdk.share.ShareService
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : 类型别名
 * </pre>
 * @see [ShareResult] 通用分享结果
 * @see [ShareResultObservable] 通用分享Observable类型
 * @see [AShareService] 分享数据回调数据发送类
 * @see [AShareMethod] 分享抽象基类
 */
typealias ShareResult = OpenResult<Any?>

typealias ShareResultObservable = Observable<ShareResult>

typealias AShareService = ShareService<ShareResult, ShareResultObservable>

typealias AShareMethod<Param, ResultObservable, Service, Provider> = ShareMethod<Param, ShareResult, ResultObservable, Service, Provider>

