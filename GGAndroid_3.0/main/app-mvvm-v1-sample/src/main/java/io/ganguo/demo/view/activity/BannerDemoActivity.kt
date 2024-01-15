package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.view.activity.base.GGHFRVModelActivity
import io.ganguo.demo.viewmodel.activity.BannerDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * Created by leo on 2017/4/28.
 * Banner - demo
 */
class BannerDemoActivity : GGHFRVModelActivity<IncludeHfRecyclerBinding, BannerDemoVModel>() {

    override fun createViewModel(): BannerDemoVModel {
        return BannerDemoVModel()
    }


    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, BannerDemoActivity::class.java)
        }
    }
}
