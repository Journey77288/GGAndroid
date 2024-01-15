package io.ganguo.app.ui.dialog


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 初始化资源
 * </pre>
 */
interface InitResources {
    fun beforeInitView()
    fun initView()
    fun initListener()
    fun initData()
}
