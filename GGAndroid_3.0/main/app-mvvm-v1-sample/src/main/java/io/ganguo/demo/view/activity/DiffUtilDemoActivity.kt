package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.DiffUtilDemoViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * DiffUtil 刷新数据工具类 Demo
 * Created by leo on 2018/8/27.
 */
class DiffUtilDemoActivity : GGVModelActivity<IncludeHfSwipeRecyclerBinding, DiffUtilDemoViewModel>() {

    override fun createViewModel(): DiffUtilDemoViewModel {
        return DiffUtilDemoViewModel()
    }


    override fun onViewAttached(viewModel: DiffUtilDemoViewModel) {
    }

    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, DiffUtilDemoActivity::class.java)
        }
    }

}
