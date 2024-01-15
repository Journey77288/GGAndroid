package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.HFSRDemoVModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HFSRecyclerViewModel;

/**
 * Header Footer SwipeRefresh Recycler Demo
 * <p>
 * init一个继承{@link HFSRecyclerViewModel}的ViewModel,
 * 即可创建一个带Header Footer SwipeRefresh RecyclerView 的Activity
 * init一个继承{@link HFRecyclerViewModel}的ViewModel,
 * 即可创建一个带Header Footer RecyclerView 的Activity
 * </p>
 * Created by Roger on 6/22/16.
 */
public class HFSRDemoActivity extends GGVModelActivity {
    public static Intent intentFor(Context context) {
        Intent intent = new Intent(context, HFSRDemoActivity.class);
        return intent;
    }

    @Override
    public BaseViewModel createViewModel() {
        return new HFSRDemoVModel(); //带下拉刷新
        //return new HFRDemoVModel(); // 不带下拉刷新
        //return new SimpleViewModel(R.layout.include_test_empty); // 任意ViewModel
    }

    @Override
    public void onViewAttached(BaseViewModel viewModel) {
        Logger.d("onViewAttached: " + getViewModel().isAttach());
    }
}
