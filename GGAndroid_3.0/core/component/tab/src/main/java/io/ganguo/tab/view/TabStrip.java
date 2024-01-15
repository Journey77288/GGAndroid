/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.ganguo.tab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import io.ganguo.tab.callback.TabColorizes;

import androidx.annotation.ColorInt;

public class TabStrip extends LinearLayout {
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;
    private static final int SELECTED_INDICATOR_PADDING = 78;

    private final int mBottomBorderThickness;
    private final Paint mBottomBorderPaint;

    private float mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;
    private float mSelectedIndicatorPadding = 0;

    private final int mDefaultBottomBorderColor;

    private int mSelectedPosition;
    private float mSelectionOffset;
    private float mSelectedIndicateWidth;
    private float mSelectedIndicatorHeight;
    private TabColorizes mCustomTabColorizer;
    private final SimpleTabColorizes mDefaultTabColorizer;
    private boolean isSelectedIndicatorVisible = true;
    private float density;
    private boolean indicatorWidthWrapContent;//是否是自适应宽度
    private float mSelectedIndicatorRadius = 0;


    public TabStrip(Context context, float mSelectedIndicatoWidth, float mSelectedIndicatorheight) {
        this(context, null);
        this.mSelectedIndicateWidth = mSelectedIndicatoWidth;
        this.mSelectedIndicatorHeight = mSelectedIndicatorheight;
    }

    public TabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        density = getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, true);
        final int themeForegroundColor = outValue.data;

        mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor,
                DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

        mDefaultTabColorizer = new SimpleTabColorizes();
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);

        mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        mBottomBorderPaint = new Paint();
        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPadding = (int) (SELECTED_INDICATOR_PADDING * density);
        mSelectedIndicatorPaint = new Paint();
    }


    @Override
    public void addView(View child) {
        super.addView(child);
        if (mSelectedIndicateWidth <= 0) {
            mSelectedIndicatorPadding = (int) (SELECTED_INDICATOR_PADDING * density) / getChildCount();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isSelectedIndicatorVisible) {
            return;
        }
        final int childCount = getChildCount();
        if (childCount <= 0) {
            return;
        }
        onDrawHorizontalIndicator(canvas);
    }

    public void setSelectedIndicateRadius(float selectedIndicateRadius) {
        this.mSelectedIndicatorRadius = selectedIndicateRadius;
        invalidate();
    }


    /**
     * 绘制水平方向的Tab栏指示器
     *
     * @param canvas
     */
    protected void onDrawHorizontalIndicator(Canvas canvas) {
        final int height = getHeight();
        View selectedTitle = getChildAt(mSelectedPosition);
        final TabColorizes colorizes = getTabColorizes();
        float indicatorThickness = getIndicatorThickness();
        int left = selectedTitle.getLeft();
        int right = selectedTitle.getRight();
        int color = colorizes.getIndicatorColor(mSelectedPosition);
        if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
            int nextColor = colorizes.getIndicatorColor(mSelectedPosition + 1);
            if (color != nextColor) {
                color = blendColors(nextColor, color, mSelectionOffset);
            }
            View nextTitle = getChildAt(mSelectedPosition + 1);
            left = (int) (mSelectionOffset * nextTitle.getLeft() +
                    (1.0f - mSelectionOffset) * left);
            right = (int) (mSelectionOffset * nextTitle.getRight() +
                    (1.0f - mSelectionOffset) * right);
        }
        if (!indicatorWidthWrapContent) {
            float indicatorWidth = getIndicatorWidth(selectedTitle.getWidth());
            mSelectedIndicatorPadding = (right - left - indicatorWidth) / 2;
        } else {
            mSelectedIndicatorPadding = (selectedTitle.getPaddingLeft() + selectedTitle.getPaddingRight()) / 2;
        }


        // 绘制选中下划线
        mSelectedIndicatorPaint.setColor(color);
        RectF rectF = new RectF();
        rectF.left = left + mSelectedIndicatorPadding;
        rectF.top = height - indicatorThickness;
        rectF.right = right - mSelectedIndicatorPadding;
        rectF.bottom = height;
        canvas.drawRoundRect(rectF, mSelectedIndicatorRadius, mSelectedIndicatorRadius, mSelectedIndicatorPaint);
        // Thin underline along the entire bottom edge
        canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
    }

    /**
     * 获取tab栏指示器宽度
     *
     * @param width
     * @return
     */
    public float getIndicatorWidth(float width) {
        float indicateWidth = mSelectedIndicateWidth;
        if (indicateWidth > width) {
            indicateWidth = width;
        }
        return indicateWidth;
    }

    /**
     * 获取tab栏指示器高度
     *
     * @return
     */
    public float getIndicatorThickness() {
        float indicatorThickness = mSelectedIndicatorThickness;
        if (indicatorThickness > 0) {
            indicatorThickness = mSelectedIndicatorHeight;
        }
        return indicatorThickness;
    }

    /**
     * 获取tab栏指示器颜色参数
     *
     * @return
     */
    public TabColorizes getTabColorizes() {
        TabColorizes colorizes = mCustomTabColorizer != null
                ? mCustomTabColorizer
                : mDefaultTabColorizer;
        return colorizes;
    }


    public void setCustomTabColorizer(TabColorizes customTabColorizer) {
        mCustomTabColorizer = customTabColorizer;
        invalidate();
    }

    public void setSelectedIndicateWidth(float mSelectedIndicatoWidth) {
        this.mSelectedIndicateWidth = mSelectedIndicatoWidth;
        invalidate();
    }

    public void setSelectedIndicatorHeight(float mSelectedIndicatorheight) {
        this.mSelectedIndicatorHeight = mSelectedIndicatorheight;
        invalidate();
    }

    public void setSelectedIndicatorVisible(boolean selectedIndicatorVisible) {
        isSelectedIndicatorVisible = selectedIndicatorVisible;
        invalidate();
    }

    public void setSelectedIndicatorColors(@ColorInt int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    public void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        invalidate();
    }


    /**
     * Set the alpha value of the {@code color} to be the given {@code alpha} value.
     */
    public static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     *              0.0 will return {@code color2}.
     */
    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static class SimpleTabColorizes implements TabColorizes {
        private int[] mIndicatorColors;

        @Override
        public final int getIndicatorColor(int position) {
            return mIndicatorColors[position % mIndicatorColors.length];
        }

        void setIndicatorColors(@ColorInt int... colors) {
            mIndicatorColors = colors;
        }
    }

    public TabStrip setIndicatorWidthWrapContent(boolean indicatorWidthWrapContent) {
        this.indicatorWidthWrapContent = indicatorWidthWrapContent;
        return this;
    }
}
