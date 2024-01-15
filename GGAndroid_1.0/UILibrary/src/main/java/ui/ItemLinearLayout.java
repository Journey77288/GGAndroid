package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import io.ganguo.library.core.animation.effect.RippleEffect;

/**
 * Created by Tony on 4/1/15.
 */
public class ItemLinearLayout extends LinearLayout {
    private final RippleEffect mEffect = new RippleEffect(this) {
        @Override
        protected void onEffect(MotionEvent event) {
            // 回调 OnItemClickListener
            AdapterView adapterView = (AdapterView) getParent();
            int position = adapterView.getPositionForView(ItemLinearLayout.this);
            long id = adapterView.getItemIdAtPosition(position);
            adapterView.performItemClick(ItemLinearLayout.this, position, id);
        }
    };

    public ItemLinearLayout(Context context) {
        this(context, null);
    }

    public ItemLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mEffect.setRippleColor(Color.parseColor("#BDBDBD"), 0.2f);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        mEffect.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        mEffect.onTouchEvent(event);

        return true;
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
