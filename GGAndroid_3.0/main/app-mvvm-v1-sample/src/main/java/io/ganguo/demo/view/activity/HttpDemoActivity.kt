package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent


import io.ganguo.demo.view.activity.base.GGHFSRVModelActivity
import io.ganguo.demo.viewmodel.activity.HttpDemoVModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * Http请求Demo
 * Created by leo on 2018/7/30.
 */
class HttpDemoActivity : GGHFSRVModelActivity<IncludeHfSwipeRecyclerBinding, HttpDemoVModel>() {

    override fun createViewModel(): HttpDemoVModel {
        return HttpDemoVModel()
    }

    override fun onViewAttached(viewModel: HttpDemoVModel) {
    }

    companion object {

        fun intentFor(context: Context): Intent {
            //数据装箱
            return Intent(context, HttpDemoActivity::class.java)
        }
    }


}
