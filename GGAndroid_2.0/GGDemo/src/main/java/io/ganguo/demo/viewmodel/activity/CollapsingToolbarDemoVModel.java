package io.ganguo.demo.viewmodel.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import io.ganguo.demo.R;
import io.ganguo.demo.viewmodel.item.ItemBannerDemoVModel;
import io.ganguo.demo.viewmodel.item.ItemTabVModel;
import io.ganguo.demo.viewmodel.page.TabPageViewModel;
import io.ganguo.utils.util.AppBars;
import io.ganguo.utils.util.Colors;
import io.ganguo.viewmodel.common.CommonViewPagerVModel;
import io.ganguo.viewmodel.common.CollapsingToolbarViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.TabLayoutViewModel;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorCollapsingToolbarBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;

/**
 * <p>
 * CollapsingToolbar 折叠联动效果 Demo
 * </p>
 * Created by leo on 2018/9/14.
 */
public class CollapsingToolbarDemoVModel extends CollapsingToolbarViewModel<ActivityInterface<IncludeCoordinatorCollapsingToolbarBinding>> {
    private CommonViewPagerVModel viewPagerVModel;
    private HeaderViewModel headerViewModel;


    @Override
    public void onViewAttached(View view) {
        hideLoading();
    }


    @Override
    public void initToolbar(CollapsingToolbarLayout toolbarLayout, Toolbar toolbar) {
        AppBars.Translucent(getView().getActivity());
        toolbar.setPadding(0, AppBars.getStatusBarHeight(getContext()), 0, 0);
        ViewModelHelper.bind(toolbar, this, onCreateHeaderVModel());
    }


    /**
     * function：create header ViewModel
     *
     * @return
     */
    protected BaseViewModel onCreateHeaderVModel() {
        return new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel.TitleItemViewModel(getClass().getSimpleName())
                        .fontRes(R.dimen.font_16))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .background(R.color.transparent)
                .build();
    }


    @Override
    public void initAppBarHeader(ViewGroup container) {
        super.initAppBarHeader(container);
        ViewModelHelper.bind(container, this, new ItemBannerDemoVModel());
    }

    /**
     * function: init AppBarContent
     *
     * @param container
     */
    @Override
    public void initAppBarContent(ViewGroup container) {
        super.initAppBarContent(container);
        ViewModelHelper.bind(container, this, getTabViewModel());
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
        int color = Colors.evaluatorViewColor(factor, getColors(R.color.transparent), getColors(R.color.colorPrimary));
        getToolbar().setBackgroundColor(color);
    }

    /**
     * function：create TabLayoutViewModel
     *
     * @return
     */
    protected TabLayoutViewModel getTabViewModel() {
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
