package io.ganguo.demo.view.activity

import io.ganguo.demo.R
import io.ganguo.demo.view.activity.base.GGHFRVModelActivity
import io.ganguo.demo.viewmodel.activity.MainVModel
import io.ganguo.log.core.Logger
import io.ganguo.utils.util.Apps
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * Main Activity
 */
class MainActivity : GGHFRVModelActivity<IncludeHfRecyclerBinding, MainVModel>() {

    override fun createViewModel(): MainVModel {
        return MainVModel()
    }


    override fun getBackDrawableRes(): Int {
        return 0
    }

    override fun onBackPressed() {
        Apps.exitByDoublePressed(this)
    }


}
