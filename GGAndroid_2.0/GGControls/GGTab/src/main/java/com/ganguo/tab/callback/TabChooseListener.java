package com.ganguo.tab.callback;

/**
 * Created by leo on 2017/7/7.
 * tab栏选中回调
 */
public interface TabChooseListener {
    /**
     * 当前点击的tab
     */
    void onChooseTab(int position);

    /**
     * 可以用于判断某些tab，点击后是否允许切换页面
     */
    boolean isSwitchTab(int position);

}
