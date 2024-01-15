package io.ganguo.viewmodel.pack.common;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.tab.callback.OnTabScrollListener;
import io.ganguo.tab.callback.TabChooseListener;
import io.ganguo.tab.model.TabModel;
import io.ganguo.tab.view.ControlScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;

import io.ganguo.utils.util.Collections;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.ViewModelHelper;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;

/**
 * Created by leo on 16/7/12.
 * tab - 请配合NoScrollViewPager使用
 * NoScrollViewPager -- 继承ViewPager支持设置是否允许手势滑动页面
 * 使用场景：
 * 1、顶部tab
 * 2、底部tab栏，配合isViewPagerSmoothScrollAnimation以及isViewPagerSmoothScroll属性，进行禁用动画及手动滑动切换页面。快速搭建类似微信主界面的页面。
 */
public class TabLayoutViewModel extends BaseViewModel<ViewInterface<ViewDataBinding>> {
    private Builder builder;

    public TabLayoutViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {

    }


    public TabModel getTabModel() {
        return new TabModel()
                .addTabViews(builder.tabItemView)
                .setDistributeEvenly(builder.isDistributeEvenly)
                .setIndicatorColors(builder.indicatorColor)
                .setOnTabScrollListener(builder.mOnTabScrollListener)
                .setPageChangeListener(builder.mViewPagerScrollListener)
                .setControlScrollViewPager(builder.mControlScrollViewPager)
                .setSelectedIndicatorHeight(builder.indicatorVisible ? builder.indicatorHeight : 0)
                .setSelectedIndicatorWidth(builder.indicatorVisible ? builder.indicatorWidth : 0)
                .setSelectedIndicatorVisible(builder.indicatorVisible)
                .setSelectedIndicatorRadius(builder.indicatorRadius)
                .setTabChooseListener(builder.mTabChooseListener)
                .setIndicatorWidthWrapContent(builder.indicatorWidthWrapContent)
                .setViewPagerScrollAnimation(builder.isViewPagerSmoothScrollAnimation)
                .setViewPagerSmoothScroll(builder.isViewPagerSmoothScroll);
    }


    public <V> V getTabItemViewModel(int position) {
        return (V) builder.getTabItemViewModel(position);
    }

    public Builder getBuilder() {
        return builder;
    }

    public int getBackgroundColor() {
        return builder.backGround;
    }


    public int getTabHeight() {
        return builder.tabHeight;
    }
    @Override
    public int getLayoutId() {
        return builder.layoutId;
    }



    public static class Builder {
        private List<View> tabItemView = new ArrayList<>();
        private List<BaseViewModel> vModelList = new ArrayList<>();
        private ControlScrollViewPager mControlScrollViewPager;
        private BaseViewModel parentViewModel;
        //tab栏高度
        @Dimension
        private int tabHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //指示器宽度
        @Dimension
        private int indicatorWidth = 100;
        //指示器高度
        @Dimension
        private int indicatorHeight = 10;
        //指示器颜色
        @ColorInt
        private int[] indicatorColor = {Color.RED};
        //tab栏背景颜色
        private int backGround = Color.TRANSPARENT;
        //是否显示指示器
        private boolean indicatorVisible = true;
        //是否平均分布
        private boolean isDistributeEvenly = true;
        //viewpager切换页面时，是否有动画
        private boolean isViewPagerSmoothScrollAnimation = true;
        //是否允许ViewPager手势滑动
        private boolean isViewPagerSmoothScroll = true;
        private LayoutInflater layoutInflater;
        private TabChooseListener mTabChooseListener;
        private OnTabScrollListener mOnTabScrollListener;
        private int layoutId = R.layout.include_tab_horizontal_layout_view_model;
        private ViewPager.OnPageChangeListener mViewPagerScrollListener;
        //指示器宽度是否是自适应
        private boolean indicatorWidthWrapContent = false;
        //选中指示器圆角半径
        private int indicatorRadius = 0;

        public Builder(BaseViewModel parentViewModel) {
            this.parentViewModel = parentViewModel;
            layoutInflater = LayoutInflater.from(parentViewModel.getContext());
        }


        public TabLayoutViewModel build() {
            return new TabLayoutViewModel(this);
        }


        public Builder setTabHeight(@Dimension int tabHeight) {
            this.tabHeight = tabHeight;
            return this;
        }

        /**
         * 绑定tab按钮view列表
         *
         * @param views
         */
        @Deprecated
        public Builder bindTabCustomView(List<View> views) {
            if (Collections.isNotEmpty(views)) {
                this.tabItemView.clear();
                this.tabItemView.addAll(views);
            }
            return this;
        }


        /**
         * 直接以ViewModel的方式，添加tab栏按钮
         *
         * @param v
         */
        public Builder appendViewModel(BaseViewModel v) {
            this.vModelList.add(v);
            this.tabItemView.add(createTabView(v));
            return this;
        }

        /**
         * layoutId
         *
         * @param layoutId
         */
        public Builder layoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        /**
         * 直接以View的方式，添加tab栏按钮
         *
         * @param view
         */
        public Builder appendTabView(View view) {
            tabItemView.add(view);
            return this;
        }

        /**
         * 将TabLayout与ControlScrollViewPager绑定
         *
         * @param mControlScrollViewPager
         */
        public Builder bindControlScrollViewPager(ControlScrollViewPager mControlScrollViewPager) {
            this.mControlScrollViewPager = mControlScrollViewPager;
            return this;
        }

        /**
         * 是否显示指示器
         *
         * @param isVisible
         */
        public Builder indicatorVisible(boolean isVisible) {
            this.indicatorVisible = isVisible;
            return this;
        }

        /**
         * tab栏内容是否平均分布
         *
         * @param isDistributeEvenly
         */
        public Builder distributeEvenly(boolean isDistributeEvenly) {
            this.isDistributeEvenly = isDistributeEvenly;
            return this;
        }

        /**
         * 指示器宽度
         *
         * @param width
         */
        public Builder indicatorWidth(int width) {
            this.indicatorWidth = width;
            return this;
        }

        /**
         * 指示器高度
         *
         * @param height
         */
        public Builder indicatorHeight(int height) {
            this.indicatorHeight = height;
            return this;
        }

        /**
         * 指示器颜色
         *
         * @param color array
         */
        public Builder indicatorColor(@ColorInt int... color) {
            this.indicatorColor = color;
            return this;
        }

        /**
         * 指示器圆角大小
         *
         * @param indicatorRadius
         */
        public Builder indicatorRadius(int indicatorRadius) {
            this.indicatorRadius = indicatorRadius;
            return this;
        }

        /**
         * tab栏颜色
         *
         * @param color
         */
        public Builder backgroundColor(@ColorInt int color) {
            this.backGround = color;
            return this;
        }

        /**
         * ViewPager滑动监听回调
         *
         * @param viewPagerScrollListener
         */
        public Builder setViewPagerScrollListener(ViewPager.OnPageChangeListener viewPagerScrollListener) {
            this.mViewPagerScrollListener = viewPagerScrollListener;
            return this;
        }

        /**
         * ScrollView滑动监听回调
         *
         * @param onTabScrollListener
         */
        public Builder setOnTabScrollListener(OnTabScrollListener onTabScrollListener) {
            this.mOnTabScrollListener = onTabScrollListener;
            return this;
        }

        /**
         * 设置NoScrollViewPager滑动时，是否有动画
         *
         * @param isViewPagerSmoothScrollAnimation
         */
        public Builder viewPagerSmoothScrollAnimation(boolean isViewPagerSmoothScrollAnimation) {
            this.isViewPagerSmoothScrollAnimation = isViewPagerSmoothScrollAnimation;
            return this;
        }


        /**
         * 设置tab栏按钮切换监听
         *
         * @param tabChooseListener
         */
        public Builder setTabChooseListener(TabChooseListener tabChooseListener) {
            this.mTabChooseListener = tabChooseListener;
            return this;
        }

        /**
         * 设置NoScrollViewPager是否允许手动滑动（false时，只能通过tab栏按钮切换页面）
         *
         * @param isViewPagerSmoothScroll
         */
        public Builder viewPagerSmoothScroll(boolean isViewPagerSmoothScroll) {
            this.isViewPagerSmoothScroll = isViewPagerSmoothScroll;
            return this;
        }

        /**
         * tabViewModel
         *
         * @param position
         */
        public BaseViewModel getTabItemViewModel(int position) {
            if (Collections.isEmpty(vModelList) || position >= vModelList.size()) {
                return null;
            }
            return vModelList.get(position);
        }

        /**
         * 指示器宽度是否根据tab item宽度自适应
         *
         * @param indicatorWidthWrapContent
         */
        public Builder indicatorWidthWrapContent(boolean indicatorWidthWrapContent) {
            this.indicatorWidthWrapContent = indicatorWidthWrapContent;
            return this;
        }

        /**
         * 创建Tab item View
         *
         * @param v
         */
        private View createTabView(BaseViewModel v) {
            ViewDataBinding b = DataBindingUtil.inflate(getLayoutInflater(), v.getLayoutId(), null, false);
            ViewModelHelper.INSTANCE.bind(b, parentViewModel, v);
            return b.getRoot();
        }


        public LayoutInflater getLayoutInflater() {
            return layoutInflater;
        }

    }
}
