package io.guguo.sidebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;

import com.ganguo.sidebar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : leo
 *     time   : 2019/02/25
 *     desc   : 快速侧滑栏
 * </pre>
 */
public class SideBarView extends View {
    /**
     * 画笔
     */
    private Paint textPaint;
    /**
     * 背景画笔
     */
    private Paint bgPaint;

    /**
     * 选中时文字是背景颜色
     */
    @ColorInt
    private int pressedTextBgColor;
    /**
     * 选中时文字颜色
     */
    @ColorInt
    private int pressedTextColor;
    /**
     * 未选中时文字原生
     */
    @ColorInt
    private int sideTextColor;
    /**
     * 文字item高度
     */
    @Dimension
    private int itemHeight;
    /**
     * 文字大小
     */
    @Dimension
    private int textSize;
    /**
     * 侧边栏菜单内容
     */
    private List<String> letters = new ArrayList<>();
    /**
     * View宽度
     */
    @Dimension
    private int viewWidth;
    /**
     * View高度
     */
    @Dimension
    private int viewHeight;
    /**
     * 绘制起点x轴位置
     */
    @Dimension
    private float itemStartY;
    /**
     * 选中位置
     */
    private int choosePosition = -1;
    /**
     * 触摸回调
     */
    private OnSideBarListener onSideBarListener;

    public SideBarView(Context context) {
        super(context, null);
    }

    public SideBarView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBarView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                viewWidth = measureSize(widthMeasureSpec, 75),
                viewHeight = measureSize(heightMeasureSpec, 75));
        itemStartY = (viewHeight - itemHeight * letters.size()) * 0.5f;
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attributeSet
     */
    private void initAttr(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.SideBarView);
        pressedTextColor = array.getColor(R.styleable.SideBarView_sidePressedTextColor, Color.GRAY);
        pressedTextBgColor = array.getColor(R.styleable.SideBarView_sidePressedTextBgColor, Color.TRANSPARENT);
        sideTextColor = array.getColor(R.styleable.SideBarView_sideTextColor, Color.BLACK);
        itemHeight = array.getDimensionPixelOffset(R.styleable.SideBarView_sideItemHeight, getResources().getDimensionPixelOffset(R.dimen.dp_18));
        textSize = array.getDimensionPixelOffset(R.styleable.SideBarView_sideTextSize, getResources().getDimensionPixelOffset(R.dimen.font_13));
        array.recycle();
    }

    /**
     * 初始化sideBar菜单内容
     */
    private void initPaint() {
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setDither(true);
        textPaint.setAntiAlias(true);
        textPaint.setColor(sideTextColor);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setDither(true);
        bgPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawText(canvas);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void onDrawText(Canvas canvas) {
        for (int i = 0; i < letters.size(); i++) {
            String text = letters.get(i);
            float textWidth = textPaint.measureText(text);
            float drawX = (viewWidth - textWidth) * 0.5f;
            float drawY = (itemHeight * i + itemHeight * 0.5f + (Math.abs(textPaint.ascent()) - textPaint.descent()) / 2 + itemStartY);
            if (i == choosePosition) {
                textPaint.setColor(pressedTextColor);
                bgPaint.setColor(pressedTextBgColor);
                float cx = (float) (drawX + textWidth * 0.5);
                float cy = drawY - (Math.abs(textPaint.ascent()) - textPaint.descent()) / 2;
                float radius = textSize / 2 + textSize * 0.2f;
                canvas.drawCircle(cx, cy, radius, bgPaint);
            } else {
                textPaint.setColor(sideTextColor);
            }
            canvas.drawText(text, drawX, drawY, textPaint);
        }
    }


    @SuppressLint("LongLogTag")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        final int action = event.getAction();
        final int oldChoosePosition = choosePosition;
        final int newChoosePosition = (int) ((y - itemStartY) / itemHeight);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                choosePosition = -1;
                if (onSideBarListener != null) {
                    onSideBarListener.onSideTouchState(this, false);
                }
                invalidate();
                break;
            default:
                if (action == MotionEvent.ACTION_DOWN) {
                    if (onSideBarListener != null) {
                        onSideBarListener.onSideTouchState(this, true);
                    }
                }
                if (oldChoosePosition != newChoosePosition) {
                    if (newChoosePosition >= 0 && newChoosePosition < letters.size()) {
                        choosePosition = newChoosePosition;
                        float currentY = itemStartY + choosePosition * itemHeight - itemHeight / 2 + getY();
                        if (onSideBarListener != null) {
                            onSideBarListener.onSideSelected(this, choosePosition, currentY, letters.get(choosePosition));
                        }
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }


    /**
     * 初始化sideBar菜单内容
     *
     * @param letters
     * @return
     */
    public SideBarView setLetters(List<String> letters) {
        this.letters = letters;
        updateViewHeight();
        postInvalidate();
        return this;
    }


    /**
     * 根据Latters数量动态更新控件高度
     */
    private void updateViewHeight() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = letters.size() * itemHeight + itemHeight;
        setLayoutParams(layoutParams);
    }

    /**
     * 测量尺寸
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private int measureSize(int measureSpec, int defaultSize) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = defaultSize;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }


    /**
     * SideBar 点击回调
     */
    public interface OnSideBarListener {
        void onSideTouchState(SideBarView sideBarView, boolean isTouch);

        void onSideSelected(SideBarView sideBarView, int position, float currentY, String selectedValue);
    }

    /**
     * set OnSideBarListener
     *
     * @param onSideBarListener
     * @return SideBarView
     */
    public SideBarView setOnSideBarListener(OnSideBarListener onSideBarListener) {
        this.onSideBarListener = onSideBarListener;
        return this;
    }

}
