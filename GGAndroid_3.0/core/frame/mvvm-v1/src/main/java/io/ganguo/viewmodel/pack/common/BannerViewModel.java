package io.ganguo.viewmodel.pack.common;

import androidx.databinding.ObservableInt;
import androidx.annotation.DimenRes;

import android.view.View;
import android.view.ViewGroup;

import io.ganguo.banner.Banner;
import io.ganguo.banner.callback.OnBannerCreateViewListener;
import io.ganguo.banner.callback.OnBannerScrollPositionListener;
import io.ganguo.banner.callback.OnRecycleBitmapListener;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.IncludeBannerViewModelBinding;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.ViewModelHelper;


/**
 * Created by leo on 2017/2/24.
 * 广告轮播 - 通用ViewModel
 * 引用开源库 - https://github.com/youth5201314/banner
 */
public class BannerViewModel extends BaseViewModel<ViewInterface<IncludeBannerViewModelBinding>> implements OnBannerCreateViewListener {
    private Builder builder;

    private BannerViewModel(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

    @Override
    public void onViewAttached(View view) {
        getBanner()
                .setBannerAnimation(builder.transformer)
                .setAutoPlayDuration(builder.duration)
                .setOffscreenPageLimit(getOffscreenPageLimit())
                .isAutoPlay(builder.isAutoPlay)
                .setViewPagerIsScroll(builder.isTouchScroll)
                .setScrollDuration(builder.scrollDuration.get())
                .setLoop(builder.isLoop)
                .addBannerScrollPositionListener(builder.mScrollPositionListener)
                .build(builder.itemVModels.size(), this);
    }


    /**
     * 创建Banner View
     *
     * @return
     */
    @Override
    public View onCreateLoopPagerView(int position) {
        return bindItemVModel(position);
    }

    /**
     * 创建All Banner View
     *
     * @return
     */
    @Override
    public List<View> onCreateUnLoopAllPagerView() {
        List<View> pageViews = new ArrayList<>();
        for (BaseViewModel itemVModel : builder.itemVModels) {
            pageViews.add(bindItemVModel(itemVModel));
        }
        return pageViews;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_banner_view_model;
    }


    public int getOffscreenPageLimit() {
        if (builder.offscreenPageLimit == -1) {
            return 0;
        }
        return builder.offscreenPageLimit;
    }

    public int getHeight() {
        if (builder.heightPx > 0) {
            return builder.heightPx;
        }
        if (builder.heightRes == ViewGroup.LayoutParams.WRAP_CONTENT || builder.heightRes == ViewGroup.LayoutParams.MATCH_PARENT) {
            return builder.heightRes;
        }
        return getDimensionPixelOffset(builder.heightRes);
    }

    public int getWidth() {
        if (builder.widthPx > 0) {
            return builder.widthPx;
        }
        if (builder.widthRes == ViewGroup.LayoutParams.WRAP_CONTENT || builder.widthRes == ViewGroup.LayoutParams.MATCH_PARENT) {
            return builder.widthRes;
        }
        return getDimensionPixelOffset(builder.widthRes);
    }


    public static class Builder {
        public BaseViewModel parentViewModel;
        public int duration = 5000;
        public ObservableInt scrollDuration = new ObservableInt(1000);
        public int offscreenPageLimit = -1;
        public boolean isAutoPlay = true;
        public ViewPager.PageTransformer transformer;
        public boolean isTouchScroll = true;
        public OnBannerScrollPositionListener mScrollPositionListener;
        public OnRecycleBitmapListener mOnRecycleBitmapListener;
        @DimenRes
        public int heightRes = ViewGroup.LayoutParams.MATCH_PARENT;
        public int heightPx = -1;
        @DimenRes
        public int widthRes = ViewGroup.LayoutParams.MATCH_PARENT;
        public int widthPx = -2;
        public boolean isLoop = true;//是否可循环
        public List<BaseViewModel> itemVModels = new ArrayList<>();//缓存ViewModel

        public Builder(BaseViewModel parentViewModel) {
            this.parentViewModel = parentViewModel;
        }


        public BannerViewModel build() {
            return new BannerViewModel(this);
        }


        /**
         * 设置轮播时长 默认为5000
         *
         * @param duration
         * @return
         */
        public Builder setDurationTime(int duration) {
            this.duration = duration;
            return this;
        }

        /**
         * 设置广告页预加载页数,跟ViewPager一样
         *
         * @param offscreenPageLimit
         * @return
         */
        public Builder setOffscreenPageLimit(int offscreenPageLimit) {
            this.offscreenPageLimit = offscreenPageLimit;
            return this;
        }


        /**
         * 是否允许自动滑动
         *
         * @param autoPlay
         */
        public Builder setAutoPlay(boolean autoPlay) {
            isAutoPlay = autoPlay;
            return this;
        }


        /**
         * 设置广告页面滑动动画
         *
         * @param transformer
         * @return
         */
        public Builder setBannerTransformer(ViewPager.PageTransformer transformer) {
            this.transformer = transformer;
            return this;
        }

        /**
         * 是否允许手动滑动
         *
         * @param touchScroll
         * @return
         */
        public Builder setTouchScroll(boolean touchScroll) {
            isTouchScroll = touchScroll;
            return this;
        }

        /**
         * 设置ViewPager 滑动平滑过渡时间
         *
         * @param duration
         * @return
         */
        public Builder setScrollDuration(int duration) {
            this.scrollDuration.set(duration);
            return this;
        }


        /**
         * 广告页面滑动监听
         *
         * @param scrollPositionListener
         * @return
         */
        public Builder setScrollPositionListener(OnBannerScrollPositionListener scrollPositionListener) {
            mScrollPositionListener = scrollPositionListener;
            return this;
        }

        /**
         * 设置广告轮播控件高度
         *
         * @param heightRes
         * @return
         */
        public Builder setBannerHeightRes(@DimenRes int heightRes) {
            this.heightRes = heightRes;
            return this;
        }


        /**
         * 设置Banner高度（单位 px）
         *
         * @param heightPx
         */
        public Builder setBannerHeightPx(int heightPx) {
            this.heightPx = heightPx;
            return this;
        }


        /**
         * 设置Banner宽度（
         *
         * @param widthRes
         */
        public Builder setBannerWidthRes(@DimenRes int widthRes) {
            this.widthRes = widthRes;
            return this;
        }


        /**
         * 设置Banner宽度（单位 px）
         *
         * @param widthPx
         * @return
         */
        public Builder setBannerWidthPx(int widthPx) {
            this.widthPx = widthPx;
            return this;
        }

        /**
         * 添加广告轮播ViewModels
         *
         * @param viewModels
         * @return
         */
        public Builder addItemViewModels(List<BaseViewModel> viewModels) {
            this.itemVModels.addAll(viewModels);
            return this;
        }


        /**
         * 添加广告轮播ViewModel
         *
         * @param viewModel
         * @return
         */
        public Builder addItemViewModel(BaseViewModel viewModel) {
            itemVModels.add(viewModel);
            return this;
        }

        /**
         * 设置Banner是否循环
         *
         * @param isCyclic
         * @return
         */
        public Builder setIsCyclic(boolean isCyclic) {
            this.isLoop = isCyclic;
            return this;
        }


        /**
         * 添加回收图片回调接口
         *
         * @param onRecycleBitmapListener
         * @return
         */
        public Builder setOnRecycleBitmapListener(OnRecycleBitmapListener onRecycleBitmapListener) {
            this.mOnRecycleBitmapListener = onRecycleBitmapListener;
            return this;
        }
    }

    /**
     * bindItemVModel
     *
     * @param position
     * @return
     */
    public View bindItemVModel(int position) {
        return bindItemVModel(builder.itemVModels.get(position));
    }

    /**
     * bindItemVModel
     *
     * @return
     */
    public View bindItemVModel(BaseViewModel viewModel) {
        ViewModelHelper.INSTANCE.bind(this.getContext(), this, viewModel);
        return viewModel.getRootView();
    }


    /**
     * 获取Banner
     *
     * @return
     */
    public Banner getBanner() {
        if (!isAttach()) {
            return null;
        }
        return getViewInterface().getBinding().banner;
    }

    /**
     * 回收Banner轮播图 bitmap 避免内存泄露
     */
    public void recycleBannerImageBitmap() {
        if (!isAttach()) {
            return;
        }
        getViewInterface().getBinding().banner.onRecycleBannerBitmap();
    }


    /**
     * 恢复轮播
     */
    public void startAutoPlay() {
        if (!isAttach()) {
            return;
        }
        getBanner().startAutoPlay();
    }

    /**
     * 停止轮播
     */
    public void stopAutoPlay() {
        if (!isAttach()) {
            return;
        }
        getBanner().stopAutoPlay();
    }

    @Override
    public void onResume() {
        startAutoPlay();
        super.onResume();
    }

    @Override
    public void onPause() {
        stopAutoPlay();
        super.onPause();
    }

    @Override
    public void onStop() {
        stopAutoPlay();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        recycleBannerImageBitmap();
        super.onDestroy();
    }
}
