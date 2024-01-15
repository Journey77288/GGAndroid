package io.ganguo.app.core.lifecycle

import androidx.lifecycle.Lifecycle
import io.ganguo.lifecycle.observer.BaseLifecycleObserver

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/12
 *     desc   : 观察ApkInfoActivity页面生命周期
 * </pre>
 * @property [Lifecycle] 直接传进来，用于做removeObserver操作，避免页面onDestroy()因做资源释放代码过多，代码爆炸的情况
 * @property [refreshFunc] 订阅更新时，数据刷新回调方法接口
 */
class ApkInfoPageObserver(var lifecycle: Lifecycle, var refreshFunc: (Any) -> Unit) : BaseLifecycleObserver {

    /**
     * Enter Create
     */
    override fun onLifecycleCreate() {
        super.onLifecycleCreate()
        //做一些代码初始化操作，例如有时候某个页面需要订阅多个RxBus，则可以分别写多个RxBus对应的Observer，在此处订阅。
        //订阅更新时，通过传入的[refreshFunc]接口来刷新页面数据
        //使用场景：一般是通过lifecycle来实现页面代码的解耦操作
    }


    /**
     * Enter Destroy
     */
    override fun onLifecycleDestroy() {
        super.onLifecycleDestroy()
        //移除Observer，释放资源
        lifecycle.removeObserver(this)
        //Todo: 做一些资源释放操作，例如Disposable释放等
    }


}