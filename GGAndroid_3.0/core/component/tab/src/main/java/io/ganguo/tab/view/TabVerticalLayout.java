package io.ganguo.tab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import io.ganguo.tab.callback.InitResources;

/**
 * Created by leo on 2017/11/3.
 * 垂直Tab栏布局
 */
public class TabVerticalLayout extends ScrollView implements InitResources {
    public TabVerticalLayout(Context context) {
        this(context, null);
    }

    public TabVerticalLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabVerticalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView() {
        setHorizontalScrollBarEnabled(false);
        setFillViewport(false);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
