package io.ganguo.demo.viewmodel.item;

import android.view.View;
import android.widget.ImageView;

import com.ganguo.banner.view.IndicatorView;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemBannerDemoBinding;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.BannerViewModel;
import io.ganguo.viewmodel.common.ImageViewModel;
import io.ganguo.viewmodel.common.IndicatorViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * <p>
 * Banner ViewModel Demo
 * </p>
 * Created by leo on 2018/10/25.
 */
public class ItemBannerDemoVModel extends BaseViewModel<ViewInterface<ItemBannerDemoBinding>> {
    protected String[] imgs = {
            "http://wallpaperspot.net/images/2018/04/06/widescreen-hd-wallpaper-2018-49.jpg",
            "https://i7.wenshen520.com/c/45.jpg",
            "http://img.bj.wezhan.cn/content/sitefiles/2065265/images/9911778_%E6%BE%B3%E5%A4%A7%E5%88%A9%E4%BA%9A%E5%9B%BE%E7%89%8713.jpeg"
    };

    @Override
    public void onViewAttached(View view) {
        BannerViewModel bannerViewModel = createBannerVModel();
        IndicatorViewModel indicatorViewModel = getIndicatorVModel(3);
        ViewModelHelper.bind(getView().getBinding().includeBanner, this, bannerViewModel);
        ViewModelHelper.bind(getView().getBinding().includeIndicator, this, indicatorViewModel);
        BannerViewModel.bindIndicatorView(bannerViewModel, indicatorViewModel.getIndicatorView());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_banner_demo;
    }


    /**
     * function: getBannerVModel
     *
     * @return
     */
    protected BannerViewModel createBannerVModel() {
        return new BannerViewModel
                .Builder(ItemBannerDemoVModel.this)
                .setDurationTime(5000)
                .setAutoPlay(true)
                .setScrollDuration(2000)
                .addItemViewModel(getBannerItemVModel(imgs[0]))
                .addItemViewModel(getBannerItemVModel(imgs[1]))
                .addItemViewModel(getBannerItemVModel(imgs[2]))
                .setBannerHeightRes(R.dimen.dp_200)
                .setScrollPositionListener(position -> Logger.d("onBannerPageSelected:position==" + position))
                .build();
    }

    /**
     * function: Banner item
     *
     * @param url
     * @return
     */
    protected BaseViewModel getBannerItemVModel(String url) {
        return new ImageViewModel
                .Builder()
                .width(ImageViewModel.MATCH_PARENT)
                .height(ImageViewModel.MATCH_PARENT)
                .url(url)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .build();
    }

    /**
     * function: Banner 指示器
     *
     * @return
     */
    protected IndicatorViewModel getIndicatorVModel(int count) {
        int dp5 = ResHelper.getDimensionPixelOffsets(R.dimen.dp_5);
        int dp8 = ResHelper.getDimensionPixelOffsets(R.dimen.dp_8);
        IndicatorView.Builder builder = new IndicatorView.Builder();
        builder.setIndicatorCount(count);
        builder.setIndicatorDrawableRes(R.drawable.selector_indicator);
        builder.setIndicatorHeight(dp5);
        builder.setIndicatorWidth(dp5);
        builder.setIndicatorSpace(dp8);
        return new IndicatorViewModel(builder);
    }

}
