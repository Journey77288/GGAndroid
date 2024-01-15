package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import java.util.Arrays;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityTabLayoutDemoBinding;
import io.ganguo.demo.viewmodel.page.TabPageViewModel;
import io.ganguo.demo.viewmodel.item.ItemTabVModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.FragmentActivityInterface;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.CommonViewPagerVModel;
import io.ganguo.viewmodel.common.TabLayoutViewModel;

/**
 * update by leo on 16/7/11.
 * TabLayoutDemo - viewmodel
 */
public class TabHorizontalLayoutDemoVModel extends BaseViewModel<FragmentActivityInterface<ActivityTabLayoutDemoBinding>> {
    private CommonViewPagerVModel viewPagerVModel;


    @Override
    public void onViewAttached(View view) {
        //bind ViewPage
        ViewModelHelper.bind(getView().getBinding().includeViewpager, this, getViewPagerVModel());
        //bind tab
        TabLayoutViewModel tabViewModel = createTabViewModel();
        ViewModelHelper.bind(getView().getBinding().includeTablayout, this, tabViewModel);
        ItemTabVModel tabIconViewModel = tabViewModel.getTabItemViewModel(0);
        //get tab item viewModel
        Logger.e("tabViewModel:title:" + tabIconViewModel.getTitle());
    }


    /**
     * function：create TabLayoutViewModel
     *
     * @return
     */
    protected TabLayoutViewModel createTabViewModel() {
        TabLayoutViewModel tabViewVModel = new TabLayoutViewModel.Builder(this)
                .indicatorColor(getColors(R.color.colorAccent), getColors(R.color.colorAccent))
                .indicatorWidth(getDimensionPixelOffsets(R.dimen.dp_30))
                .indicatorHeight(getDimensionPixelOffsets(R.dimen.tab_height))
                .setIndicatorRadius(getDimensionPixelOffsets(R.dimen.dp_2))
                .distributeEvenly(false)
                .indicatorVisible(true)
                .setIndicatorWidthWrapContent(true)
                .setViewPagerSmoothScroll(true)
                .setViewPagerSmoothScrollAnimation(true)
                .setBackgroundColor(getColors(R.color.gray))
                .setLayoutId(R.layout.include_tab_horizontal_layout_view_model)
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab1测试长度"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab2"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab3"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab4"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab5"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab6"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab7"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab8"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab9"))
                .appendViewModel(new ItemTabVModel(R.drawable.selector_tab_icon, "tab10"))
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
                            new TabPageViewModel(),
                            new TabPageViewModel(),
                            new TabPageViewModel(),
                            new TabPageViewModel(),
                            new TabPageViewModel(),
                            new TabPageViewModel(),
                            new TabPageViewModel(),
                            new TabPageViewModel()
                    )
            );

        }
        return viewPagerVModel;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_tab_layout_demo;
    }
}
