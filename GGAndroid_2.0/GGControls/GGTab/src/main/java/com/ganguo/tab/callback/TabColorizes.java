package com.ganguo.tab.callback;

import android.support.annotation.ColorInt;

/**
 * Created by leo on 2017/7/7.
 * 获取IndicatorColor
 */
public interface TabColorizes {
    /**
     * @return return the color of the indicator used when {@code id} is selectedWeek.
     */
    int getIndicatorColor(@ColorInt int position);
}
