package io.ganguo.library.core.animation.effect;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tony on 3/29/15.
 */
public interface IEffect {
    public void setOnClickListener(View.OnClickListener l);

    public void onSizeChanged(int w, int h, int oldw, int oldh);

    public void onTouchEvent(final MotionEvent event);

    public void onDraw(final Canvas canvas);

    public void setStyle(int style);
}
