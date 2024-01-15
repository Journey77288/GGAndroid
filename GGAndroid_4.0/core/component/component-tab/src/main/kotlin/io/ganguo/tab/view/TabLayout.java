/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.ganguo.tab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.viewpager.widget.ViewPager;

import io.ganguo.tab.callback.InitResources;
import io.ganguo.tab.callback.TabColorizes;
import io.ganguo.tab.callback.TabLayoutMediator;
import io.ganguo.tab.model.TabModel;
import io.ganguo.tab.utils.TabViewHelper;


/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p/>
 * To use the component, simply add it to your view hierarchy. Then in your
 * <p/>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * alternative is via the {@link TabColorizes} interface which provides you complete control over
 * which color is used for any individual id.
 * <p/>
 */
public class TabLayout extends HorizontalScrollView implements InitResources, TabLayoutMediator {

    public TabStrip tabStrip;
    private TabModel tabModel;
    private TabViewHelper tabViewHelper;
    private int scrollState;


    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void bindTabModel(TabModel tabModel) {
        if (tabModel == null) {
            return;
        }
        this.tabModel = tabModel;
        initListener();
        initData();
        initView();
        initTabViewHelper();
    }


    @Override
    public void initView() {
        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);
        tabStrip = new TabStrip(getContext(), tabModel.selectedIndicatorWidth, tabModel.selectedIndicatorHeight, tabModel.marginOffset);
        tabStrip.setSelectedIndicatorVisible(tabModel.mSelectedIndicatorVisible);
        tabStrip.setSelectedIndicatorHeight(tabModel.selectedIndicatorHeight);
        tabStrip.setSelectedIndicatorColors(tabModel.indicatorColors);
        tabStrip.setSelectedIndicatorVisible(tabModel.mSelectedIndicatorVisible);
        tabStrip.setIndicatorWidthWrapContent(tabModel.indicatorWidthWrapContent);
        tabStrip.setSelectedIndicateRadius(tabModel.selectedIndicatorRadius);
        if (getChildCount() > 0) {
            removeAllViews();
        }
        addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public void initListener() {
        addOnScrollListener();
    }

    @Override
    public void initData() {
    }


    private void initTabViewHelper() {
        tabViewHelper = new TabViewHelper
                .Builder(getContext())
                .setDistributeEvenly(tabModel.distributeEvenly)
                .setTabChooseListener(tabModel.tabChooseListener)
                .setTabViews(tabModel.tabViews)
                .setSelectedListener(tabModel.selectedListener)
                .setParentView(this)
                .setTabStrip(tabStrip)
                .build();
    }


    /**
     * 设置滑动监听
     */
    public void addOnScrollListener() {
        getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (tabModel.onTabScrollListener != null) {
                tabModel.onTabScrollListener.onScrollChanged(TabLayout.this);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int tabStripChildCount = tabStrip.getChildCount();
        if ((position < 0) || (position >= tabStripChildCount)) {
            return;
        }
        tabStrip.onViewPagerPageChanged(position, positionOffset);
        View selectedTitle = tabStrip.getChildAt(position);
        int extraOffset = (selectedTitle != null)
                ? (int) (positionOffset * selectedTitle.getWidth())
                : 0;
        tabViewHelper.scrollToTab(position, extraOffset);
    }

    @Override
    public void onPageRemove(int position) {
        if (position >= tabModel.tabViews.size() || position < 0) {
            return;
        }
        View tabView = tabModel.tabViews.get(position);
        tabStrip.removeView(tabView);
        tabModel.removeTabViews(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        scrollState = state;
    }

    @Override
    public void onPageSelected(int position) {
        if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
            tabStrip.onViewPagerPageChanged(position, 0f);
        }
        if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
//            tabViewHelper.scrollToTab(position, 0);
        }
        tabViewHelper.onResetTabView(position);
    }

    /**
     * 滑动到指定Tab栏位置
     *
     * @param position
     */
    public void scrollToTab(int position) {
        tabViewHelper.scrollToTab(position, 0);
    }
}
