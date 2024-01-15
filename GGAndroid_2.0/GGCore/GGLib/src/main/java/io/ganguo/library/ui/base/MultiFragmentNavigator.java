package io.ganguo.library.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import io.ganguo.utils.util.Strings;

/**
 * 显示多个Fragment在一个容器内(如：ViewPager)
 * <p>
 * Created by hulkyao on 18/8/2017.
 */

public class MultiFragmentNavigator implements FragmentNavigator {
    private FragmentManager mFragmentManager; // fragment manager
    private boolean showTransition = false; // 是否启动fragment切换动画

    public MultiFragmentNavigator(FragmentManager mFragmentManager) {
        this(mFragmentManager, null);
    }

    public MultiFragmentNavigator(FragmentManager mFragmentManager, Bundle savedInstanceState) {
        this.mFragmentManager = mFragmentManager;

        restoreFragment(savedInstanceState);
    }

    /**
     * 保存fragment状态
     *
     * @param savedInstanceState
     */
    @Override
    public void saveFragmentState(Bundle savedInstanceState) {

    }

    /**
     * 恢复fragment状态
     *
     * @param savedInstanceState
     */
    @Override
    public void restoreFragment(Bundle savedInstanceState) {

    }

    /**
     * 初始化fragment,但不进行显示
     *
     * @param fragment
     * @param tag
     */
    @Override
    public void addFragment(Fragment fragment, int containerId, final String tag) {
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
    public void showFragment(final Fragment fragment, int containerId, final String tag) {
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
        if (fragment.isAdded()) {
            if (fragment.isDetached()) {
                transaction.attach(fragment);
            } else {
                transaction.show(fragment);
            }
        } else {
            transaction.add(containerId, fragment, tag);
        }
        transaction.commitAllowingStateLoss();
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
     * 移除fragment
     *
     * @param tag
     */
    @Override
    public void detachFragment(String tag) {
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
     * 是否设置Fragment切换动画
     */
    @Override
    public void setShowTransition(boolean showTransition) {
        this.showTransition = showTransition;
    }

    /**
     * 判断是否启动动画
     *
     * @return boolean
     */
    public boolean isShowTransition() {
        return showTransition;
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
     * @return Fragment
     */
    @Override
    public <S extends Fragment> S findFragmentByTag(String tag) {
        return (S) mFragmentManager.findFragmentByTag(tag);
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
}
