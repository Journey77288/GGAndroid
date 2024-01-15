package io.ganguo.tab.model;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.ColorInt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.tab.callback.OnTabScrollListener;
import io.ganguo.tab.callback.OnTabChooseListener;
import io.ganguo.tab.callback.OnTabSelectedChangeListener;


/**
 * Created by leo on 2017/11/3.
 * Model - 用于存放数据，便于TabVerticalLayout与TabHorizontalLayout 参数字段代码复用
 */
public class TabModel implements Serializable {
    public OnTabChooseListener tabChooseListener;

    public boolean distributeEvenly;
    public boolean mSelectedIndicatorVisible = true;
    public boolean isViewPagerScrollAnimation = true;
    public float selectedIndicatorWidth;
    public float selectedIndicatorHeight;

    public OnTabScrollListener onTabScrollListener;
    public List<View> tabViews = new ArrayList<>();
    public boolean indicatorWidthWrapContent = false;//指示器宽度是否是自适应
    public float selectedIndicatorRadius;
    @ColorInt
    public int[] indicatorColors = {Color.RED};
    public OnTabSelectedChangeListener selectedListener;
    public int marginOffset = 0;


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
        this.selectedIndicatorRadius = selectedIndicatorRadius;
        return this;
    }


    /**
     * tab栏点击回调
     *
     * @param tabChooseListener
     */
    public TabModel setTabChooseListener(OnTabChooseListener tabChooseListener) {
        this.tabChooseListener = tabChooseListener;
        return this;
    }


    /**
     * 设置指示器是否平均分布
     *
     * @param distributeEvenly
     */
    public TabModel setDistributeEvenly(boolean distributeEvenly) {
        this.distributeEvenly = distributeEvenly;
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
        this.selectedIndicatorWidth = selectedIndicatorWidth;
        return this;
    }


    /**
     * 指示器高度
     *
     * @param selectedIndicatorHeight
     */
    public TabModel setSelectedIndicatorHeight(float selectedIndicatorHeight) {
        this.selectedIndicatorHeight = selectedIndicatorHeight;
        return this;
    }


    /**
     * 设置滑动监听回调
     *
     * @param onTabScrollListener
     */
    public TabModel setOnTabScrollListener(OnTabScrollListener onTabScrollListener) {
        this.onTabScrollListener = onTabScrollListener;
        return this;
    }

    /**
     * 添加TabView
     *
     * @param tabViews
     */
    public TabModel addTabViews(List<View> tabViews) {
        if (!tabViews.isEmpty()) {
            this.tabViews.clear();
            this.tabViews.addAll(tabViews);
        }
        return this;
    }

    public TabModel setMarginOffset(int marginOffset) {
        this.marginOffset = marginOffset;
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


    /**
     * tab栏选中状态回调
     *
     * @param selectedListener
     * @return
     */
    public TabModel setSelectedListener(OnTabSelectedChangeListener selectedListener) {
        this.selectedListener = selectedListener;
        return this;
    }

    /**
     * 删除TabView
     *
     * @param position 删除Tab位置
     * @return
     */
    public void removeTabViews(int position) {
        if (!tabViews.isEmpty() && tabViews.size() >= position) {
            this.tabViews.remove(position);
        }
    }

    /**
     * 添加单个TabView
     *
     * @param tabView 添加Tab的View
     */
    public void addTabView(View tabView) {
        this.tabViews.add(tabView);
    }

    /**
     * 指定位置添加单个TabView
     *
     * @param index 增加Tab位置
     * @param tabView 添加Tab的View
     * @return
     */
    public void addTabView(int index, View tabView) {
        this.tabViews.add(index, tabView);
    }
}
