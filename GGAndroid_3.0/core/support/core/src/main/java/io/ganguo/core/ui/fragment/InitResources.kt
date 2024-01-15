package io.ganguo.core.ui.fragment

/**
 * 初始化资源
 *
 *
 * Created by Tony on 9/30/15.
 */
interface InitResources {
    val layoutResourceId: Int
    fun initView()
    fun initListener()
    fun initData()
}