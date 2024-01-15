package io.ganguo.tab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by leo on 2017/11/3.
 * 垂直的TabStrip 实现
 */
public class TabVerticalStrip extends TabStrip {
    TabVerticalStrip(Context context, float mSelectedIndicatoWidth, float mSelectedIndicatorheight) {
        super(context, mSelectedIndicatoWidth, mSelectedIndicatorheight);
    }

    TabVerticalStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    protected void onDrawVerTicalStripIndicator(Canvas canvas) {
    }


}
