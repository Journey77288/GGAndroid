package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import io.ganguo.library.core.animation.effect.IEffect;
import io.ganguo.library.core.animation.effect.RippleEffect;

/**
 * Created by Tony on 3/28/15.
 */
public class ImageView extends android.widget.ImageView {

    private IEffect mEffect = new RippleEffect(this);

    public ImageView(Context context) {
        this(context, null);
    }

    public ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        mEffect.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        mEffect.onTouchEvent(event);

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mEffect.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);

        mEffect.setOnClickListener(l);
    }
}
