package io.ganguo.vmodel.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.utils.util.Collections;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * update by leo on 2018/07/07.
 * PagerAdapter - 支持DataBinding及ViewModel
 */
public class ViewModelPagerAdapter extends PagerAdapter {
    private List<BaseViewModel> pageViewModels = new ArrayList<>();
    private BaseViewModel viewModelParent;

    public ViewModelPagerAdapter(@NonNull BaseViewModel viewModelParent) {
        this.viewModelParent = viewModelParent;
    }

    public ViewModelPagerAdapter(@NonNull BaseViewModel parent, List<? extends BaseViewModel> items) {
        this.pageViewModels = (List<BaseViewModel>) items;
        this.viewModelParent = parent;
    }

    @Override
    public int getCount() {
        return Collections.isEmpty(pageViewModels) ? 0 : pageViewModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseViewModel viewModel = getPageViewModels().get(position);
        ViewModelHelper.bind(container, viewModelParent, viewModel);
        return viewModel.getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        pageViewModels.get(position).onDestroy();
    }

    public List<BaseViewModel> getPageViewModels() {
        return pageViewModels;
    }

    public ViewModelPagerAdapter setPageViewModels(List<BaseViewModel> pageViewModels) {
        this.pageViewModels = pageViewModels;
        return this;
    }

    /**
     * function：add ViewModel
     *
     * @param pageVModel
     */
    public void addViewModel(BaseViewModel pageVModel) {
        pageViewModels.add(pageVModel);
    }

    /**
     * function：get ViewModel
     *
     * @param position
     * @return BaseViewModel
     */
    public BaseViewModel getPageViewModel(int position) {
        if (position >= getCount()) {
            return null;
        }
        return pageViewModels.get(position);
    }

}
