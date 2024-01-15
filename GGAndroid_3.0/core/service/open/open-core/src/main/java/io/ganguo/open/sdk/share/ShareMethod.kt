package io.ganguo.open.sdk.share

import android.app.Activity
import io.ganguo.factory.method.Method
import io.ganguo.open.sdk.entity.OpenResult
import io.reactivex.Observable
import java.lang.ref.WeakReference


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 分享
 * </pre>
 * @property [Param] 分享参数
 * @property [Service]] 分享服务接口实现
 * @property [ResultObservable] 分享数据返回类型
 * @property [Service]服务创建类
 */
abstract class ShareMethod<Param, Result : OpenResult<*>, ResultObservable, Service : ShareService<Result, ResultObservable>, Provider : ShareProvider<Service>>
    : Method<Result, ResultObservable, Service, Provider>() {

    /**
     * 分享
     *
     * @param activity Activity引用
     * @param shareParam 分享数据
     */
    open fun share(activity: Activity, shareParam: Param): ResultObservable {
        return newShareProvider(activity, shareParam).let {
            asService(activity, it).share()
        }
    }

    /**
     * create Provider
     * @param activity Activity
     * @param shareParam Param?
     * @return Provider
     */
    protected abstract fun newShareProvider(activity: Activity, shareParam: Param): Provider

}