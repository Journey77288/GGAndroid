package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import io.ganguo.library.core.animation.effect.RippleEffect;

/**
 * Created by Tony on 3/28/15.
 */
public class Button extends android.widget.Button {

    private RippleEffect mEffect = new RippleEffect(this);

    public Button(Context context) {
        this(context, null);
    }

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.button);
        mEffect.setStyle(a.getInt(R.styleable.button_effectType, 0));
        mEffect.setDuration(a.getInt(R.styleable.button_duration, 400));
        a.recycle();
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
