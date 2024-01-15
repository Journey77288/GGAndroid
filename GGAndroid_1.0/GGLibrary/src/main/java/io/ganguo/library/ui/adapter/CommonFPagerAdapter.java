package io.ganguo.library.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.library.ui.extend.BasePagerFragment;

/**
 * 能用 PagerAdapter
 * <p/>
 * Created by Tony on 11/3/14.
 */
public class CommonFPagerAdapter extends FragmentStatePagerAdapter implements IListAdapter<BasePagerFragment> {
    protected List<BasePagerFragment> mFragmentList;

    public CommonFPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setList(List<BasePagerFragment> list) {
        mFragmentList = list;
    }

    @Override
    public List<BasePagerFragment> getList() {
        return mFragmentList;
    }

    @Override
    public void addAll(List<BasePagerFragment> list) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>(list.size());
        }
        mFragmentList.addAll(list);
    }

    @Override
    public void add(BasePagerFragment item) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        mFragmentList.add(item);
    }

    @Override
    public BasePagerFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public boolean contains(BasePagerFragment item) {
        return mFragmentList.contains(item);
    }

    @Override
    public void remove(BasePagerFragment item) {
        if (mFragmentList != null) {
            mFragmentList.remove(item);
        }
    }

    @Override
    public void remove(int position) {
        if (mFragmentList != null) {
            mFragmentList.remove(position);
        }
    }

    @Override
    public void clear() {
        if (mFragmentList != null) {
            mFragmentList.clear();
        }
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getTitle();
    }

}
