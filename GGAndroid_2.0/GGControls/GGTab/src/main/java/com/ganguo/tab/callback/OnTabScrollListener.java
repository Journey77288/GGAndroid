package com.ganguo.tab.callback;

import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by leo on 2017/9/1.
 */
public interface OnTabScrollListener {
    void onScrollChanged(ScrollView scrollView);
    void onScrollChanged(HorizontalScrollView scrollView);
}
