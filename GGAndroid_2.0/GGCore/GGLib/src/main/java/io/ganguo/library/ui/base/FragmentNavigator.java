package io.ganguo.library.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * 控制fragment的显示、隐藏
 * <p>
 * Created by hulkyao on 11/7/2016.
 */
public interface FragmentNavigator {
    /**
     * 初始化fragment,但不进行显示
     *
     * @param fragment
     * @param tag
     */
    void addFragment(Fragment fragment, int containerId, final String tag);

    /**
     * 显示Fragment到容器中
     *
     * @param fragment
     * @param containerId
     * @param tag
     */
    void showFragment(final Fragment fragment, int containerId, final String tag);

    /**
     * 隐藏 fragment
     *
     * @param fragment
     */
    void hideFragment(Fragment fragment);

    /**
     * 隐藏 fragment
     *
     * @param tag
     */
    void hideFragment(String tag);

    /**
     * 移除fragment
     *
     * @param fragment
     */
    void detachFragment(Fragment fragment);

    /**
     * 移除fragment
     *
     * @param tag
     */
    void detachFragment(String tag);

    /**
     * 是否设置Fragment切换动画
     */
    void setShowTransition(boolean showTransition);

    /**
     * 获取fragment manager实例
     *
     * @return FragmentManager
     */
    FragmentManager getFragmentManager();

    /**
     * 通过tag查找到对应fragment
     *
     * @return Fragment
     */
    <S extends Fragment> S findFragmentByTag(String tag);


    /**
     * 保存fragment状态
     *
     * @param savedInstanceState
     */
    void saveFragmentState(Bundle savedInstanceState);

    /**
     * 恢复fragment状态
     *
     * @param savedInstanceState
     */
    void restoreFragment(Bundle savedInstanceState);
}
