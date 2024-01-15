package io.ganguo.core.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import io.ganguo.core.helper.activity.ActivityHelper

/**
 * FragmentActivity - 基类
 * Created by hulk on 10/20/14.
 */
@Suppress("LeakingThis")
abstract class BaseActivity : FragmentActivity(), InitResources {

    init {
        ActivityHelper.bindLifecycle(this)
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInitView()
        initView()
        initListener()
        initData()
    }


    /**
     * Binding lifecycleObserver
     * @param observer LifecycleObserver
     */
    fun bindLifecycle(observer: LifecycleObserver) {
        lifecycle.addObserver(observer)
    }
}