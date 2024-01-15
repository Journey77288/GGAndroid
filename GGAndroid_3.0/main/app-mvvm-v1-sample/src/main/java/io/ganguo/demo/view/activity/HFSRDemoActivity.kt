package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.view.activity.base.GGHFSRVModelActivity
import io.ganguo.demo.viewmodel.activity.HFSRDemoVModel
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * Header Footer SwipeRefresh Recycler Demo
 *
 *
 * init一个继承[HFSRecyclerViewModel]的ViewModel,
 * 即可创建一个带Header Footer SwipeRefresh RecyclerView 的Activity
 * init一个继承[HFRecyclerViewModel]的ViewModel,
 * 即可创建一个带Header Footer RecyclerView 的Activity
 *
 * Created by Roger on 6/22/16.
 */
class HFSRDemoActivity : GGHFSRVModelActivity<IncludeHfSwipeRecyclerBinding, HFSRDemoVModel>() {

    override fun createViewModel(): HFSRDemoVModel {
        return HFSRDemoVModel() //带下拉刷新
        //return new HFRDemoVModel(); // 不带下拉刷新
        //return new SimpleViewModel(R.layout.include_test_empty); // 任意ViewModel
    }

    override fun onViewAttached(viewModel: HFSRDemoVModel) {
    }


    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, HFSRDemoActivity::class.java)
        }
    }

}
