package io.ganguo.app.ui.activity


/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : 资源初始化
 * </pre>
 */
interface InitResources {
    fun beforeInitView()
    fun initView()
    fun initListener()
    fun initData()
}
