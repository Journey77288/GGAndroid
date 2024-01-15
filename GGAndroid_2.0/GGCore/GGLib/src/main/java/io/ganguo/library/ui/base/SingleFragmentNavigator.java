package io.ganguo.library.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.log.Logger;

/**
 * 每次只显示一个Fragment在一个容器内
 * 当手机旋转屏幕或者重新启动时不会重复创建Fragment
 * <p>
 * Created by hulkyao on 18/8/2017.
 */

public class SingleFragmentNavigator implements FragmentNavigator {
    private static final String EXTRA_CURRENT_POSITION = "extra_current_position"; // current position params
    private static final String EXTRA_TAGS = "extra_tags"; // tags params

    private FragmentManager mFragmentManager; // fragment manager
    private boolean showTransition = false; // 是否启动fragment切换动画
    private String currentFragmentTag = null; // 当前 fragment 的 tag
    private ArrayList<String> tags = new ArrayList<>(); // cache fragment tags

    public SingleFragmentNavigator(FragmentManager mFragmentManager) {
        this(mFragmentManager, null);
    }

    public SingleFragmentNavigator(FragmentManager mFragmentManager, Bundle savedInstanceState) {
        this.mFragmentManager = mFragmentManager;

        restoreFragment(savedInstanceState);
    }

    /**
     * 隐藏 fragment
     *
     * @param fragment
     */
    @Override
    public void hideFragment(Fragment fragment) {
        if (!fragmentValidate(fragment)) {
            return;
        }
        if (!fragmentManagerValidate()) {
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏 fragment
     *
     * @param tag
     */
    @Override
    public void hideFragment(String tag) {
        if (!tagValidate(tag)) {
            return;
        }
        if (!fragmentManagerValidate()) {
            return;
        }
        if (!fragmentAdded(tag)) {
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(mFragmentManager.findFragmentByTag(tag));
        transaction.commitAllowingStateLoss();
    }

    /**
     * 是否设置Fragment切换动画
     */
    @Override
    public void setShowTransition(boolean showTransition) {
        this.showTransition = showTransition;
    }

    /**
     * 获取fragment manager实例
     *
     * @return FragmentManager
     */
    @Override
    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    /**
     * 通过tag查找到对应fragment
     *
     * @param tag
     * @return Fragment
     */
    @Override
    public <S extends Fragment> S findFragmentByTag(String tag) {
        return (S) mFragmentManager.findFragmentByTag(tag);
    }

    /**
     * 恢复fragment的状态
     *
     * @param savedInstanceState
     */
    @Override
    public void restoreFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        tags = savedInstanceState.getStringArrayList(EXTRA_TAGS);
        currentFragmentTag = savedInstanceState.getString(EXTRA_CURRENT_POSITION);

        if (!tagsValidate(tags)) {
            return;
        }
        if (!tagValidate(currentFragmentTag)) {
            return;
        }

        Fragment currentFragment = mFragmentManager.findFragmentByTag(currentFragmentTag);
        if (!fragmentValidate(currentFragment)) {
            return;
        }
        Logger.d("run restore");

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (String tag : tags) {
            Fragment fragment = mFragmentManager.findFragmentByTag(tag);
            hideFragment(fragment);
        }

        showFragmentAdded(currentFragment, currentFragmentTag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 保存fragment的状态
     *
     * @param savedInstanceState
     */
    @Override
    public void saveFragmentState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        if (!tagValidate(currentFragmentTag)) {
            return;
        }
        if (!tagsValidate(tags)) {
            return;
        }
        savedInstanceState.putString(EXTRA_CURRENT_POSITION, currentFragmentTag);
        savedInstanceState.putStringArrayList(EXTRA_TAGS, tags);
    }

    /**
     * 初始化fragment,但不进行显示
     *
     * @param fragment
     * @param containerId
     * @param tag
     */
    @Override
    public void addFragment(Fragment fragment, int containerId, String tag) {
        if (!fragmentValidate(fragment)) {
            return;
        }
        if (!tagValidate(tag)) {
            return;
        }
        if (!fragmentManagerValidate()) {
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(containerId, fragment, tag);
        }
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示Fragment到容器中
     *
     * @param fragment
     * @param containerId
     * @param tag
     */
    @Override
    public void showFragment(Fragment fragment, int containerId, String tag) {
        if (!fragmentValidate(fragment)) {
            return;
        }
        if (!tagValidate(tag)) {
            return;
        }
        if (!fragmentManagerValidate()) {
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (isShowTransition()) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        if (tagValidate(currentFragmentTag)) {
            hideFragment(mFragmentManager.findFragmentByTag(currentFragmentTag));
        }
        if (fragment.isAdded()) {
            if (fragment.isDetached()) {
                transaction.attach(fragment);
            } else {
                transaction.show(fragment);
            }
        } else {
            transaction.add(containerId, fragment, tag);
        }
        tags.add(tag);
        currentFragmentTag = tag;
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示已添加至容器的fragment
     *
     * @param fragment
     * @param tag
     */
    private void showFragmentAdded(Fragment fragment, String tag) {
        if (fragment.getView() == null || fragment.getView().getParent() == null) {
            return;
        }
        int containerId = ((ViewGroup) fragment.getView().getParent()).getId();
        if (containerId < 0) {
            return;
        }

        showFragment(fragment, containerId, tag);
    }

    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentByTag(currentFragmentTag);
    }

    /**
     * 移除fragment
     *
     * @param fragment
     */
    @Override
    public void detachFragment(Fragment fragment) {
        if (!fragmentValidate(fragment)) {
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.detach(fragment);
        transaction.remove(fragment);
        transaction.commitNowAllowingStateLoss();
    }

    /**
     * 移除fragment
     *
     * @param tag
     */
    @Override
    public void detachFragment(String tag) {
        if (!tagValidate(tag)) {
            return;
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (!fragmentValidate(fragment)) {
            return;
        }
        if (!fragmentAdded(tag)) {
            return;
        }

        detachFragment(fragment);
    }

    /**
     * 判断是否启动动画
     *
     * @return boolean
     */
    public boolean isShowTransition() {
        return showTransition;
    }

    private boolean fragmentValidate(Fragment fragment) {
        return fragment != null;
    }

    private boolean tagValidate(String tag) {
        return Strings.isNotEmpty(tag);
    }

    private boolean fragmentManagerValidate() {
        return mFragmentManager != null;
    }

    private boolean fragmentAdded(String tag) {
        return findFragmentByTag(tag) != null;
    }

    private boolean tagsValidate(List<String> tags) {
        return tags != null && tags.size() != 0;
    }
}
