package io.ganguo.demo.viewmodel.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ganguo.banner.BannerConfig;

import io.ganguo.demo.R;
import io.ganguo.demo.viewmodel.item.ItemBannerDemoVModel;
import io.ganguo.viewmodel.common.ImageViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.BannerViewModel;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;

/**
 * Created by leo on 2017/4/28.
 * 广告轮播 - demo
 */
public class BannerDemoVModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {

    /**
     * function: add ViewModel
     *
     * @param view
     */
    @Override
    public void onViewAttached(View view) {
        getAdapter().add(new ItemBannerDemoVModel());
        getAdapter().notifyDataSetChanged();
        toggleEmptyView();
    }


}
