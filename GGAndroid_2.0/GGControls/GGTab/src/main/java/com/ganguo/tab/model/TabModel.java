package com.ganguo.tab.model;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ganguo.tab.callback.OnTabScrollListener;
import com.ganguo.tab.callback.TabChooseListener;
import com.ganguo.tab.view.ControlScrollViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.utils.util.Collections;

/**
 * Created by leo on 2017/11/3.
 * Model - 用于存放数据，便于TabVerticalLayout与TabHorizontalLayout 参数字段代码复用
 */
public class TabModel implements Serializable {
    public TabChooseListener mTabChooseListener;
    public ViewPager.OnPageChangeListener mPageChangeListener;

    public boolean mDistributeEvenly;
    public boolean mSelectedIndicatorVisible = true;
    public boolean isViewPagerScrollAnimation = true;
    public float mSelectedIndicatorWidth;
    public float mSelectedIndicatorHeight;

    public ControlScrollViewPager mControlScrollViewPager;
    public OnTabScrollListener mOnTabScrollListener;
    public List<View> tabViews = new ArrayList<>();
    public boolean isViewPagerSmoothScroll = true;
    public boolean indicatorWidthWrapContent = false;//指示器宽度是否是自适应
    public float mSelectedIndicatorRadius;
    @ColorInt
    public int[] indicatorColors = {Color.RED};


    public TabModel setIndicatorColors(int... indicatorColors) {
        this.indicatorColors = indicatorColors;
        return this;
    }

    /**
     * 指示器圆角半径
     *
     * @param selectedIndicatorRadius
     * @return
     */
    public TabModel setSelectedIndicatorRadius(float selectedIndicatorRadius) {
        this.mSelectedIndicatorRadius = selectedIndicatorRadius;
        return this;
    }


    /**
     * tab栏点击回调
     *
     * @param tabChooseListener
     */
    public TabModel setTabChooseListener(TabChooseListener tabChooseListener) {
        mTabChooseListener = tabChooseListener;
        return this;
    }


    /**
     * ViewPager页面滑动监听
     *
     * @param pageChangeListener
     */
    public TabModel setPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        this.mPageChangeListener = pageChangeListener;
        return this;
    }


    /**
     * 设置指示器是否平均分布
     *
     * @param distributeEvenly
     */
    public TabModel setDistributeEvenly(boolean distributeEvenly) {
        this.mDistributeEvenly = distributeEvenly;
        return this;
    }

    /**
     * 设置指示器是否显示
     *
     * @param selectedIndicatorVisible
     */
    public TabModel setSelectedIndicatorVisible(boolean selectedIndicatorVisible) {
        this.mSelectedIndicatorVisible = selectedIndicatorVisible;
        return this;
    }


    /**
     * 设置ViewPager是否有滑动动画
     *
     * @param viewPagerScrollAnimation
     */
    public TabModel setViewPagerScrollAnimation(boolean viewPagerScrollAnimation) {
        this.isViewPagerScrollAnimation = viewPagerScrollAnimation;
        return this;
    }


    /**
     * 设置指示器宽度
     *
     * @param selectedIndicatorWidth
     */
    public TabModel setSelectedIndicatorWidth(float selectedIndicatorWidth) {
        this.mSelectedIndicatorWidth = selectedIndicatorWidth;
        return this;
    }


    /**
     * 指示器高度
     *
     * @param selectedIndicatorHeight
     */
    public TabModel setSelectedIndicatorHeight(float selectedIndicatorHeight) {
        this.mSelectedIndicatorHeight = selectedIndicatorHeight;
        return this;
    }


    /**
     * set ViewPager
     *
     * @param controlScrollViewPager
     */
    public TabModel setControlScrollViewPager(ControlScrollViewPager controlScrollViewPager) {
        this.mControlScrollViewPager = controlScrollViewPager;
        return this;
    }

    /**
     * 设置滑动监听回调
     *
     * @param onTabScrollListener
     */
    public TabModel setOnTabScrollListener(OnTabScrollListener onTabScrollListener) {
        this.mOnTabScrollListener = onTabScrollListener;
        return this;
    }

    /**
     * 添加TabView
     *
     * @param tabViews
     */
    public TabModel addTabViews(List<View> tabViews) {
        if (Collections.isNotEmpty(tabViews)) {
            this.tabViews.clear();
            this.tabViews.addAll(tabViews);
        }
        return this;
    }


    /**
     * ViewPager是否允许滑动
     *
     * @param viewPagerSmoothScroll
     */
    public TabModel setViewPagerSmoothScroll(boolean viewPagerSmoothScroll) {
        this.isViewPagerSmoothScroll = viewPagerSmoothScroll;
        return this;
    }


    /**
     * 指示器是否根据tab栏宽度自适应
     *
     * @param indicatorWidthWrapContent
     */
    public TabModel setIndicatorWidthWrapContent(boolean indicatorWidthWrapContent) {
        this.indicatorWidthWrapContent = indicatorWidthWrapContent;
        return this;
    }
}
