package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.DatabaseKotlinDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * ObjectBox  - 数据库 Demo
 * Created by leo on 2018/11/10.
 */
class DatabaseKotlinDemoActivity : GGVModelActivity<IncludeHfSwipeRecyclerBinding, DatabaseKotlinDemoVModel>() {

    override fun createViewModel(): DatabaseKotlinDemoVModel {
        return DatabaseKotlinDemoVModel()
    }

    override fun onViewAttached(viewModel: DatabaseKotlinDemoVModel) {
    }

    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, DatabaseKotlinDemoActivity::class.java)
        }
    }


}
