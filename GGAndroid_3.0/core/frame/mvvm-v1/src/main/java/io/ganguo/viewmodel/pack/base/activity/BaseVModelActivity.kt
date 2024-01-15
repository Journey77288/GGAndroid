package io.ganguo.viewmodel.pack.base.activity

import android.graphics.Color
import android.os.Build
import androidx.databinding.ViewDataBinding

import android.os.Bundle

import io.ganguo.utils.util.AppBars
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.view.ViewModelActivity

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : Activity基类，可能出现通用属性配置的Activity，都直接继承BaseVModelActivity，方便统一添加配置。例如：状态栏设置，语言配置等
</pre> *
 */
abstract class BaseVModelActivity<T : ViewDataBinding, B : BaseViewModel<*>> : ViewModelActivity<T, B>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppBarConfig()
    }


    /**
     * 配置StatusBar / NavigationBar 属性
     */
    open fun initAppBarConfig() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AppBars.statusBarColor(this, Color.LTGRAY)
        } else {
            //设置状态栏主题为亮色（状态栏字体为黑色）
            AppBars.statusBarLightStyle(this)
        }
    }

}
