package io.ganguo.scanner.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.R.attr.width;

/**
 * 扫描线 - View
 * Created by Bert on 2017/9/22.
 */
public class LineView extends View {
    private Paint paint;//声明画笔
    private int lineColor;
    private Shader mShader;

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    protected void initPaint() {
        paint = new Paint();//创建一个画笔
        paint.setStyle(Paint.Style.FILL);//设置非填充
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);//笔宽5像素
        paint.setAntiAlias(true);//锯齿不显示
    }

    public void setLineColor(int color) {
        lineColor = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String line_colors = String.valueOf(Integer.toHexString(lineColor));
        line_colors = line_colors.substring(line_colors.length() - 6, line_colors.length() - 0);
        mShader = new LinearGradient(0, 0, getMeasuredWidth(), 0, new int[]{Color.parseColor("#00" + line_colors), lineColor, Color.parseColor("#00" + line_colors),}, null,
                Shader.TileMode.CLAMP);
        paint.setShader(mShader);
        canvas.drawLine(0, 0, width, 0, paint);
    }


}
