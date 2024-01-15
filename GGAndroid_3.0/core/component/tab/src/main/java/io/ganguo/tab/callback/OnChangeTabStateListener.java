package io.ganguo.tab.callback;

/**
 * Created by leo on 2017/10/13.
 * 处理tab栏滑动时状态变更
 */
public interface OnChangeTabStateListener {
    void onScrollToTab(int position, int positionOffset);

    void onResetView(int position);
}
