package com.ganguo.banner.callback;

import android.view.View;

import java.util.List;

/**
 * 通过接口来实现外部View的创建
 * Created by leo on 2018/7/6 下午8:04
 */
public interface OnBannerCreateViewListener {
    /**
     *
     *
     * @return
     */
    View onCreateLoopPagerView(int position);

    /**
     * 无线循环Banner，直接通过外部接口函数，创建所有View
     * @see
     * @return
     */
    List<View> onCreateUnLoopAllPagerView();
}
