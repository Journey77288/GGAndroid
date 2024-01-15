package io.ganguo.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Fragment - 基类
 * </pre>
 */
abstract class BaseFragment : Fragment(), InitResources {
    var fragmentTitle: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // init resources
        initView()
        initListener()
        initData()
    }

}