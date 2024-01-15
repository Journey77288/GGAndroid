package io.ganguo.scanner.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * function:CornerView
 * <p>
 * 扫码框 - 折角View
 * <p/>
 */
public class CornerView extends View {
    private Paint paint;//声明画笔
    private static final String TAG = "CornerView";
    private int width = 0;
    private int height = 0;
    private int cornerColor;
    private int cornerWidth;
    private int cornerGravity = 1;

    public CornerView(Context context) {
        super(context, null);
    }

    public CornerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * function: init Paint
     */
    protected void initPaint() {
        paint = new Paint();//创建一个画笔
        paint.setStyle(Paint.Style.FILL);//设置非填充
        paint.setAntiAlias(true);//锯齿不显示
    }

    /**
     * function: set paint color
     *
     * @param cornerColor
     * @return
     */
    public CornerView setCornerColor(@ColorInt int cornerColor) {
        this.cornerColor = cornerColor;
        this.paint.setColor(cornerColor);
        invalidate();
        return this;
    }

    /**
     * function: set paint StrokeWidth
     *
     * @param cornerWidth
     * @return
     */
    public CornerView setCornerLineWidth(@Dimension int cornerWidth) {
        this.cornerWidth = cornerWidth;
        this.paint.setStrokeWidth(cornerWidth);
        invalidate();
        return this;
    }

    /**
     * function: set view Gravity
     *
     * @param cornerGravity
     * @return
     */
    public CornerView setCornerGravity(@CornerGravity int cornerGravity) {
        this.cornerGravity = cornerGravity;
        invalidate();
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(cornerWidth);//笔宽5像素
        paint.setColor(cornerColor);//设置为红笔
        switch (cornerGravity) {
            case LEFT_TOP:
                canvas.drawLine(0, 0, width, 0, paint);
                canvas.drawLine(0, 0, 0, height, paint);
                break;
            case LEFT_BOTTOM:
                canvas.drawLine(0, 0, 0, height, paint);
                canvas.drawLine(0, height, width, height, paint);
                break;
            case RIGHT_TOP:
                canvas.drawLine(0, 0, width, 0, paint);
                canvas.drawLine(width, 0, width, height, paint);
                break;
            case RIGHT_BOTTOM:
                canvas.drawLine(width, 0, width, height, paint);
                canvas.drawLine(0, height, width, height, paint);
                break;
        }
    }

    public static final int LEFT_TOP = 0;
    public static final int LEFT_BOTTOM = 1;
    public static final int RIGHT_TOP = 2;
    public static final int RIGHT_BOTTOM = 3;

    /**
     * function: CornerView 所在位置
     * <p>
     * 根据位置绘制折角方向
     * </p>
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {LEFT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM, RIGHT_TOP})
    public @interface CornerGravity {

    }


}
