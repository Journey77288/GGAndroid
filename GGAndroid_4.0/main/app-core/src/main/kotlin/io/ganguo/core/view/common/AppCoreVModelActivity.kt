package io.ganguo.core.view.common

import android.graphics.Color
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.BarUtils
import io.ganguo.mvvm.view.ViewModelActivity
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.resources.R
import io.ganguo.utils.setStatusBarColor

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Base Core Activity
 * </pre>
 * @since 基类， App内Activity默认继承该类，方便后期添加一些通用配置
 */
abstract class AppCoreVModelActivity<B : ViewDataBinding, V : ViewModel<*>> : ViewModelActivity<B, V>() {
    /**
     * override onCreate
     * @param savedInstanceState Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingAppBar()
    }

    /**
     * 设置AppBar样式
     */
    protected open fun settingAppBar() {
        setStatusBarColor(this, getColor(R.color.colorPrimary))
        BarUtils.setNavBarColor(this, Color.WHITE)
        BarUtils.setNavBarLightMode(this, true)
        BarUtils.setStatusBarLightMode(this, false)
    }

}

