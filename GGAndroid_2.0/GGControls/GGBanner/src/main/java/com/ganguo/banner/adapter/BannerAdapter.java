package com.ganguo.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ganguo.banner.view.BannerViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by leo on 2017/4/28.
 * 轮播栏 - adapter
 */
public class BannerAdapter extends PagerAdapter {
    private List<View> pagerViews;
    protected HashMap<Integer, View> cacheView = new HashMap<>();


    public BannerAdapter(List<View> pagerViews) {
        this.pagerViews = pagerViews;
    }

    /**
     * ViewPager中填充的实际View数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return pagerViews.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        cacheView.put(position, view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        cacheView.remove(position);
    }


    protected View getView(int position) {
        if (position >= pagerViews.size()) {
            return null;
        }
        return pagerViews.get(position);
    }

    public HashMap<Integer, View> getCacheView() {
        return cacheView;
    }

}