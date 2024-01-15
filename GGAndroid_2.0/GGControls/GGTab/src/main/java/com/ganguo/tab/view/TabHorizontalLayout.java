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

package com.ganguo.tab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import com.ganguo.tab.callback.InitResources;
import com.ganguo.tab.callback.TabColorizes;
import com.ganguo.tab.model.TabModel;
import com.ganguo.tab.utils.TabViewHelper;

import io.ganguo.utils.util.log.Logger;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p/>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * <p/>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * alternative is via the {@link TabColorizes} interface which provides you complete control over
 * which color is used for any individual id.
 * <p/>
 * 水平TabLayout
 */
public class TabHorizontalLayout extends HorizontalScrollView implements InitResources {

    private TabStrip mTabStrip;
    private TabModel mTabModel;
    private TabViewHelper mTabViewHelper;


    public TabHorizontalLayout(Context context) {
        this(context, null);
    }

    public TabHorizontalLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabHorizontalLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void bindTabModel(TabModel tabModel) {
        if (tabModel == null) {
            Logger.e(getClass().getSimpleName() + "  TabModel is Cannot be null");
            return;
        }
        this.mTabModel = tabModel;
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
        mTabStrip = new TabStrip(getContext(), mTabModel.mSelectedIndicatorWidth, mTabModel.mSelectedIndicatorHeight);
        mTabStrip.setSelectedIndicatorVisible(mTabModel.mSelectedIndicatorVisible);
        mTabStrip.setSelectedIndicatorHeight(mTabModel.mSelectedIndicatorHeight);
        mTabStrip.setSelectedIndicatorColors(mTabModel.indicatorColors);
        mTabStrip.setSelectedIndicatorVisible(mTabModel.mSelectedIndicatorVisible);
        mTabStrip.setIndicatorWidthWrapContent(mTabModel.indicatorWidthWrapContent);
        mTabStrip.setSelectedIndicateRadius(mTabModel.mSelectedIndicatorRadius);
        if (getChildCount() > 0) {
            removeAllViews();
        }
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public void initListener() {
        addOnScrollListener();
    }

    @Override
    public void initData() {
    }


    private void initTabViewHelper() {
        mTabViewHelper = new TabViewHelper
                .Builder(getContext())
                .setViewPager(mTabModel.mControlScrollViewPager)
                .setPageChangeListener(mTabModel.mPageChangeListener)
                .setDistributeEvenly(mTabModel.mDistributeEvenly)
                .setViewPagerScroll(mTabModel.isViewPagerSmoothScroll)
                .setPagerScrollAnimation(mTabModel.isViewPagerScrollAnimation)
                .setTabChooseListener(mTabModel.mTabChooseListener)
                .setTabViews(mTabModel.tabViews)
                .setParentView(this)
                .setTabStrip(mTabStrip)
                .build();
    }


    /**
     * 设置滑动监听
     */
    public void addOnScrollListener() {
        getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (mTabModel.mOnTabScrollListener != null) {
                mTabModel.mOnTabScrollListener.onScrollChanged(TabHorizontalLayout.this);
            }
        });
    }

}
