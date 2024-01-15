package io.ganguo.tab.callback;

import android.view.View;

import io.ganguo.tab.bean.RetentionConstants;
import io.ganguo.tab.view.TabStrip;

import androidx.viewpager.widget.ViewPager;

import io.ganguo.tab.bean.RetentionConstants;
import io.ganguo.tab.view.TabStrip;


/**
 * Created by leo on 2017/10/13.
 * Tab栏滑动回调
 */
public class InternalPagerListener implements ViewPager.OnPageChangeListener {
    private int layoutType = RetentionConstants.LAYOUT_HORIZONTAL;
    private ViewPager.OnPageChangeListener mPageChangeListener;
    private OnChangeTabStateListener mOnChangeTabStateListener;
    private TabStrip mTabStrip;

    public InternalPagerListener(TabStrip tabStrip) {
        mTabStrip = tabStrip;
    }

    public void setTabStrip(TabStrip tabStrip) {
        mTabStrip = tabStrip;
    }

    public InternalPagerListener setLayoutType(int layoutType) {
        this.layoutType = layoutType;
        return this;
    }

    public InternalPagerListener setPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        mPageChangeListener = pageChangeListener;
        return this;
    }

    public InternalPagerListener setOnChangeTabStateListener(OnChangeTabStateListener onChangeTabStateListener) {
        mOnChangeTabStateListener = onChangeTabStateListener;
        return this;
    }

    public InternalPagerListener setScrollState(int scrollState) {
        mScrollState = scrollState;
        return this;
    }

    private int mScrollState;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int tabStripChildCount = mTabStrip.getChildCount();
        if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
            return;
        }
        mTabStrip.onViewPagerPageChanged(position, positionOffset);
        //水平类型的tablayout才需要设置位置
        if (layoutType == RetentionConstants.LAYOUT_HORIZONTAL && mOnChangeTabStateListener != null) {
            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            mOnChangeTabStateListener.onScrollToTab(position, extraOffset);
        }
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrolled(position, positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mTabStrip.onViewPagerPageChanged(position, 0f);
        }
        if (mOnChangeTabStateListener != null && mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mOnChangeTabStateListener.onScrollToTab(position, 0);
        }
        if (mOnChangeTabStateListener != null) {
            mOnChangeTabStateListener.onResetView(position);
        }
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageSelected(position);
        }
    }

}
