package io.ganguo.viewmodel.common;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.ganguo.tab.view.ControlScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.utils.util.Collections;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.R;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.adapter.ViewModelPagerAdapter;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.databinding.IncludeResuseViewPagerBinding;

/**
 * <p>
 * 通用ViewPager ViewModel
 * </p>
 * Created by leo on 2018/7/11 下午8:46
 */
public class CommonViewPagerVModel extends BaseViewModel<ViewInterface<IncludeResuseViewPagerBinding>> {
    private ViewModelPagerAdapter mAdapter;
    private List<BaseViewModel> mViewModels = new ArrayList<>();
    private int mOffScreenPageLimit = 3;
    private ViewPager.OnPageChangeListener changeListener;
    private boolean mViewPagerSmoothScroll = true;
    private boolean mIsClipChildren = true;
    private boolean mIsClipPadding = true;
    private int mPageMargin = 0;
    private int mPaddingLeft = 0;
    private int mPaddingRight = 0;
    private int mPaddingTop = 0;
    private int mPaddingBottom = 0;
    private ViewPager.PageTransformer mPageTransformer;


    public CommonViewPagerVModel(List<? extends BaseViewModel> viewModels) {
        mViewModels.clear();
        mViewModels.addAll(viewModels);
    }

    public CommonViewPagerVModel setPageTransformer(ViewPager.PageTransformer mPageTransformer) {
        this.mPageTransformer = mPageTransformer;
        return this;
    }

    public CommonViewPagerVModel setOffscreenPageLimit(int offscreenPageLimit) {
        this.mOffScreenPageLimit = offscreenPageLimit;
        return this;
    }

    public CommonViewPagerVModel setViewPagerSmoothScroll(boolean viewPagerSmoothScroll) {
        this.mViewPagerSmoothScroll = viewPagerSmoothScroll;
        return this;
    }

    public CommonViewPagerVModel setPageChangeListener(ViewPager.OnPageChangeListener changeListener) {
        this.changeListener = changeListener;
        return this;
    }

    public CommonViewPagerVModel setPageMargin(int pageMargin) {
        this.mPageMargin = pageMargin;
        return this;
    }

    public CommonViewPagerVModel setClipChildren(boolean isClipChildren) {
        this.mIsClipChildren = isClipChildren;
        return this;
    }

    public CommonViewPagerVModel setClipPadding(boolean isClipPadding) {
        this.mIsClipPadding = isClipPadding;
        return this;
    }

    public CommonViewPagerVModel setPaddingLeft(int mPaddingLeft) {
        this.mPaddingLeft = mPaddingLeft;
        return this;
    }

    public CommonViewPagerVModel setPaddingRight(int mPaddingRight) {
        this.mPaddingRight = mPaddingRight;
        return this;
    }

    public CommonViewPagerVModel setPaddingTop(int mPaddingTop) {
        this.mPaddingTop = mPaddingTop;
        return this;
    }

    public CommonViewPagerVModel setPaddingBottom(int mPaddingBottom) {
        this.mPaddingBottom = mPaddingBottom;
        return this;
    }

    public CommonViewPagerVModel setViewModels(List<BaseViewModel> mViewModels) {
        this.mViewModels = mViewModels;
        return this;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_resuse_view_pager;
    }

    @Override
    public void onViewAttached(View view) {
        initViewPager();
    }

    /**
     * function：init viewPager
     */
    protected void initViewPager() {
        if (Collections.isEmpty(mViewModels)) {
            return;
        }
        getAdapter().setPageViewModels(mViewModels);
        getViewPager().setOffscreenPageLimit(mOffScreenPageLimit > mViewModels.size() ? mViewModels.size() : mOffScreenPageLimit);
        getViewPager().setViewPagerSmoothScroll(mViewPagerSmoothScroll);
        getViewPager().setAdapter(getAdapter());
        getViewPager().setPageMargin(mPageMargin);
        getViewPager().setClipChildren(mIsClipChildren);
        getViewPager().setClipToPadding(mIsClipPadding);
        getViewPager().setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        getViewPager().setPageTransformer(false, mPageTransformer);
        if (changeListener != null) {
            getViewPager().addOnPageChangeListener(changeListener);
        }
    }


    /**
     * function：Adapter
     *
     * @return
     */
    public ViewModelPagerAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ViewModelPagerAdapter(this);
        }
        return mAdapter;
    }

    /**
     * function：ViewPager
     *
     * @return
     */
    public ControlScrollViewPager getViewPager() {
        if (!isAttach()) {
            Logger.e("viewPager Not added to the container");
            return null;
        }
        return getView().getBinding().vpContent;
    }

    /**
     * function：切换页面
     *
     * @param page
     */
    public void setCurrentItem(int page) {
        setCurrentItem(page, false);
    }

    /**
     * function：切换页面
     *
     * @param page
     * @param smoothScroll
     */
    public void setCurrentItem(int page, boolean smoothScroll) {
        if (!isAttach()) {
            return;
        }
        if (getViewPager() == null) {
            return;
        }
        getViewPager().setCurrentItem(page, smoothScroll);
    }
}
