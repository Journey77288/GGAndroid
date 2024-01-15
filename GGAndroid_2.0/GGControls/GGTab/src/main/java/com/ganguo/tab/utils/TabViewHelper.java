package com.ganguo.tab.utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ganguo.tab.bean.RetentionConstants;
import com.ganguo.tab.callback.InternalPagerListener;
import com.ganguo.tab.callback.OnChangeTabStateListener;
import com.ganguo.tab.callback.TabChooseListener;
import com.ganguo.tab.view.ControlScrollViewPager;
import com.ganguo.tab.view.TabStrip;

import java.util.List;

/**
 * Created by leo on 2017/11/2.
 * TabViewHelper TabLayout辅助工具类，方便代码复用
 */
public class TabViewHelper implements View.OnClickListener, OnChangeTabStateListener {
    private static final int TITLE_OFFSET_DIPS = 24;
    private int mTitleOffset;
    private Builder mBuilder;

    public TabViewHelper(Builder builder) {
        mBuilder = builder;
        mTitleOffset = (int) (TITLE_OFFSET_DIPS * mBuilder.mParentView.getResources().getDisplayMetrics().density);
        initTab();
    }

    @Override
    public void onScrollToTab(int position, int positionOffset) {
        scrollToTab(position, positionOffset);
    }

    @Override
    public void onResetView(int position) {
        onResetTabView(position);
    }


    public static class Builder {
        //tab父ViewGroup
        public View mParentView;
        //context上下文
        public Context mContext;
        //TabStrip 滑动指示器
        public TabStrip mTabStrip;
        //存储Tab View对象
        public List<View> mTabViews;
        //对应的ViewPager
        public ControlScrollViewPager mViewPager;
        //tab栏父容器宽度 铺满屏幕/根据内容自适应
        public boolean mDistributeEvenly;
        //tab栏选中回调
        public TabChooseListener mTabChooseListener;
        //viewPager是否有滑动动画
        public boolean isPagerScrollAnimation;
        //viewPager是否允许滑动
        public boolean isViewPagerScroll;
        //viewPager滑动回调接口
        public ViewPager.OnPageChangeListener mPageChangeListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setParentView(View parentView) {
            this.mParentView = parentView;
            return this;
        }

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }


        public Builder setTabStrip(TabStrip tabStrip) {
            this.mTabStrip = tabStrip;
            return this;
        }


        public Builder setTabViews(List<View> tabViews) {
            this.mTabViews = tabViews;
            return this;
        }

        public Builder setViewPager(ControlScrollViewPager viewPager) {
            this.mViewPager = viewPager;
            return this;
        }

        public Builder setDistributeEvenly(boolean distributeEvenly) {
            this.mDistributeEvenly = distributeEvenly;
            return this;
        }

        public Builder setTabChooseListener(TabChooseListener tabChooseListener) {
            this.mTabChooseListener = tabChooseListener;
            return this;
        }

        public Builder setPagerScrollAnimation(boolean pagerScrollAnimation) {
            this.isPagerScrollAnimation = pagerScrollAnimation;
            return this;
        }

        public Builder setViewPagerScroll(boolean viewPagerScroll) {
            this.isViewPagerScroll = viewPagerScroll;
            return this;
        }

        public Builder setPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
            this.mPageChangeListener = pageChangeListener;
            return this;
        }

        public TabViewHelper build() {
            return new TabViewHelper(this);
        }
    }

    /**
     * Tab栏初始化操作
     */
    public void initTab() {
        if (mBuilder.mTabStrip.getChildCount() != 0) {
            mBuilder.mTabStrip.removeAllViews();
        }
        mBuilder.mViewPager.setViewPagerSmoothScroll(mBuilder.isViewPagerScroll);
        mBuilder.mViewPager
                .addOnPageChangeListener(
                        new InternalPagerListener(mBuilder.mTabStrip)
                                .setLayoutType(RetentionConstants.LAYOUT_HORIZONTAL)
                                .setPageChangeListener(mBuilder.mPageChangeListener)
                                .setOnChangeTabStateListener(this));
        initTabStrip();
    }


    /**
     * 配置Tab栏参数
     */
    public void initTabStrip() {
        PagerAdapter adapter = mBuilder.mViewPager.getAdapter();
        if (adapter.getCount() == 0 || mBuilder.mTabViews.size() == 0) {
            return;
        }
        if (adapter.getCount() != mBuilder.mTabViews.size()) {
            throw new IndexOutOfBoundsException("ViewPager is count unequal to tab count");
        }
        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = mBuilder.mTabViews.get(i);
            initTabAttribute(tabView, i);
        }
    }


    /**
     * 设置tabView相关属性
     *
     * @param view
     * @param i
     */
    public void initTabAttribute(View view, int i) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        //是否平均分布于屏幕
        if (mBuilder.mDistributeEvenly) {
            lp.width = 0;
            lp.weight = 1;
        }
        view.setLayoutParams(lp);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        mBuilder.mTabStrip.addView(view);
        if (i == mBuilder.mViewPager.getCurrentItem()) {
            view.setSelected(true);
        }
        view.setOnClickListener(this);
    }


    /**
     * 滑动tab栏
     *
     * @param positionOffset
     * @param position
     */
    public void scrollToTab(int position, int positionOffset) {
        TabStrip tabStrip = mBuilder.mTabStrip;
        int tabChildCount = tabStrip.getChildCount();
        if (tabChildCount == 0 || position < 0 || position >= tabChildCount) {
            return;
        }
        View selectedChild = tabStrip.getChildAt(position);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;
            if (position > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffset;
            }
            mBuilder.mParentView.scrollTo(targetScrollX, 0);
        }
    }


    /**
     * 设置单个tab栏选中状态
     *
     * @param view
     * @param isChoose
     */
    public void setTabViewChooseState(View view, boolean isChoose) {
        view.setSelected(isChoose);
        ViewGroup viewGroup;
        if (!(view instanceof ViewGroup)) {
            return;
        }
        viewGroup = (ViewGroup) view;
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            viewGroup.getChildAt(j).setSelected(isChoose);
        }
    }


    /**
     * 重置tab栏选中状态
     *
     * @param position
     */
    public void onResetTabView(int position) {
        TabStrip tabStrip = mBuilder.mTabStrip;
        if (tabStrip == null) {
            return;
        }
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View view = tabStrip.getChildAt(i);
            setTabViewChooseState(view, position == i);
        }
    }


    /**
     * tab栏点击事件
     */
    @Override
    public void onClick(View v) {
        TabStrip tabStrip = mBuilder.mTabStrip;
        boolean isAnimation = mBuilder.isPagerScrollAnimation;
        ControlScrollViewPager viewPager = mBuilder.mViewPager;
        TabChooseListener chooseListener = mBuilder.mTabChooseListener;
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            if (v != tabStrip.getChildAt(i)) {
                continue;
            }
            if (chooseListener == null) {
                viewPager.setCurrentItem(i, isAnimation);
                return;
            }
            chooseListener.onChooseTab(i);
            if (!chooseListener.isSwitchTab(i)) {
                return;
            }
            viewPager.setCurrentItem(i, isAnimation);
            return;
        }
    }
}
