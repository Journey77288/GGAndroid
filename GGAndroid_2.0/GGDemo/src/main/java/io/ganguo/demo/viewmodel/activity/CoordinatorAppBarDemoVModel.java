package io.ganguo.demo.viewmodel.activity;

import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.ViewGroup;


import java.util.Arrays;

import io.ganguo.demo.R;
import io.ganguo.demo.viewmodel.item.ItemBannerDemoVModel;
import io.ganguo.demo.viewmodel.item.ItemTabVModel;
import io.ganguo.demo.viewmodel.page.TabPageViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.util.Colors;
import io.ganguo.viewmodel.common.CommonViewPagerVModel;
import io.ganguo.viewmodel.common.CoordinatorAppBarVModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.TabLayoutViewModel;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorLayoutAppBarBinding;

/**
 * <p>
 * 1、CoordinatorAppBarVModel Demo
 * 2、通过CoordinatorLayout和AppBarLayout可以实现原生折叠/计算颜色渐变效果
 * <p/>
 * Created by leo on 2018/7/13.
 */
public class CoordinatorAppBarDemoVModel extends CoordinatorAppBarVModel<ActivityInterface<IncludeCoordinatorLayoutAppBarBinding>> {
    private CommonViewPagerVModel viewPagerVModel;
    private ItemBannerDemoVModel bannerViewModel;

    @Override
    public void onViewAttached(View view) {
        hideLoading();
    }


    @Override
    public void initAppBarHeader(ViewGroup container) {
        super.initAppBarHeader(container);
        ViewModelHelper.bind(container, this, bannerViewModel = new ItemBannerDemoVModel());
    }

    /**
     * function: init AppBarContent
     *
     * @param container
     */
    @Override
    public void initAppBarContent(ViewGroup container) {
        super.initAppBarContent(container);
        ViewModelHelper.bind(container, this, onCreateTabViewModel());
    }

    /**
     * function: init Content
     *
     * @param container
     */
    @Override
    public void initContent(ViewGroup container) {
        super.initContent(container);
        ViewModelHelper.bind(container, this, getViewPagerVModel());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        //可根据滑动的距离，在此处计算动态设置一些View颜色/透明度
        float factor = Math.abs((float) verticalOffset / appBarLayout.getTotalScrollRange());
        int color = Colors.evaluatorViewColor(factor, getColors(R.color.transparent), getColors(R.color.blue_dark));
        bannerViewModel.getRootView().setBackgroundColor(color);
    }


    /**
     * function：create TabLayoutViewModel
     *
     * @return
     */
    protected TabLayoutViewModel onCreateTabViewModel() {
        TabLayoutViewModel tabViewVModel = new TabLayoutViewModel.Builder(this)
                .indicatorColor(getColors(R.color.colorAccent), getColors(R.color.colorAccent))
                .indicatorWidth(getDimensionPixelOffsets(R.dimen.dp_30))
                .indicatorHeight(getDimensionPixelOffsets(R.dimen.tab_height))
                .distributeEvenly(true)
                .indicatorVisible(true)
                .setIndicatorWidthWrapContent(false)
                .setViewPagerSmoothScroll(true)
                .setViewPagerSmoothScrollAnimation(true)
                .setBackgroundColor(getColors(R.color.gray))
                .setLayoutId(R.layout.include_tab_horizontal_layout_view_model)
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab1"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab2"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab3"))
                .bindControlScrollViewPager(getViewPagerVModel().getViewPager())
                .build();
        return tabViewVModel;
    }


    /**
     * function：create CommonViewPagerVModel
     *
     * @return
     */
    public CommonViewPagerVModel getViewPagerVModel() {
        if (viewPagerVModel == null) {
            viewPagerVModel = new CommonViewPagerVModel(
                    Arrays.<BaseViewModel>asList(
                            new TabPageViewModel(),
                            new TabPageViewModel(),
                            new TabPageViewModel()
                    )
            );

        }
        return viewPagerVModel;
    }
}
