package com.ganguo.tab.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by leo on 16/7/31.
 * 可设置是否可以滑动的ViewPager
 */
public class ControlScrollViewPager extends ViewPager {

    private boolean scroll = true;

    public ControlScrollViewPager(Context context) {
        super(context);
    }

    public ControlScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPagerSmoothScroll(boolean scroll) {
        this.scroll = scroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!scroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!scroll)
            return false;
        else {
            return super.onInterceptTouchEvent(arg0);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}
