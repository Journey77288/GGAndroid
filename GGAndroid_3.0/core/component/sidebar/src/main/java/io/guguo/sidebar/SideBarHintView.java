package io.guguo.sidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ganguo.sidebar.R;

import java.util.logging.Logger;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : leo
 *     time   : 2019/02/26
 *     desc   : SideBar 提示View
 * </pre>
 */
public class SideBarHintView extends View {
    /**
     * 提示文字颜色
     */
    @ColorInt
    private int hintTextColor;
    /**
     * 提示文字大小
     */
    @Dimension
    private int hintTextSize;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 提示文字内容
     */
    private String hintValue;

    /**
     * X轴绘制起点偏移量
     */
    private int offsetX = 0;

    public SideBarHintView(Context context) {
        this(context, null);
    }

    public SideBarHintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBarHintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initPaint();
        initView();
    }

    /**
     * init View
     */
    private void initView() {
        hideHint();
    }

    /**
     * init Paint
     */
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(hintTextColor);
        paint.setDither(true);
        paint.setTextSize(hintTextSize);
    }


    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attributeSet
     */
    private void initAttr(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.SideBarHintView);
        hintTextColor = array.getColor(R.styleable.SideBarHintView_sideHintTextColor, Color.WHITE);
        hintTextSize = array.getDimensionPixelSize(R.styleable.SideBarHintView_sideHintTextSize, getResources().getDimensionPixelOffset(R.dimen.font_20));
        offsetX = array.getDimensionPixelOffset(R.styleable.SideBarHintView_sideHintTextOffsetX, 0);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(hintValue)) {
            return;
        }
        float textWidth = paint.measureText(hintValue);
        float x = (getWidth() - textWidth) / 2 + offsetX;
        float y = getHeight() / 2 - (paint.ascent() + paint.descent()) / 2;
        canvas.drawText(hintValue, x, y, paint);
    }

    /**
     * 显示提示View
     *
     * @param hintValue
     */
    public void showHint(String hintValue) {
        setVisibility(VISIBLE);
        this.hintValue = hintValue;
        invalidate();
    }

    /**
     * 隐藏提示View
     */
    public void hideHint() {
        setVisibility(GONE);
    }

    /**
     * 测量控件宽度
     *
     * @param widthMeasureSpec
     */
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = getResources().getDimensionPixelOffset(R.dimen.dp_100);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    /**
     * 测量控件高度
     *
     * @param heightMeasureSpec
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = getResources().getDimensionPixelOffset(R.dimen.dp_100);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

}
