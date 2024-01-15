package io.ganguo.banner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ganguo.banner.R;

import java.util.ArrayList;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * IndicatorView 指示器，一般配合ViewPager用
 * Created by leo on 2018/10/23.
 */
public class IndicatorView extends LinearLayout {
    private Builder builder;
    //当前所在位置
    private int currentPosition = 0;
    //缓存Views
    private ArrayList<ImageView> indicatorViews = new ArrayList<>();


    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initIndicatorView();
    }

    /**
     * function:初始化AttributeSet参数
     *
     * @param context
     * @param attrs
     */
    protected void initTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        builder = new Builder();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        builder.indicatorWidth = array.getDimensionPixelOffset(R.styleable.Indicator_indicator_width, 10);
        builder.indicatorHeight = array.getDimensionPixelOffset(R.styleable.Indicator_indicator_height, 10);
        builder.indicatorSpace = array.getDimensionPixelOffset(R.styleable.Indicator_indicator_space, 5);
        builder.indicatorDrawableRes = array.getInt(R.styleable.Indicator_indicator_drawable_res, R.drawable.selector_indicator);
        builder.indicatorCount = array.getInt(R.styleable.Indicator_indicator_count, 0);
        array.recycle();
    }


    /**
     * function:初始化IndicatorView 配置
     */
    protected void initIndicatorView() {
        if (builder.indicatorCount == 0) {
            return;
        }
        indicatorViews.clear();
        removeAllViews();
        currentPosition = 0;
        for (int position = 0; position < builder.indicatorCount; position++) {
            ImageView imageView = createIndicatorImgView();
            if (position == currentPosition) {
                imageView.setSelected(true);
            } else {
                imageView.setSelected(false);
            }
            addView(imageView);
            indicatorViews.add(imageView);
        }
    }


    /**
     * function: 创建Indicator ImageView
     *
     * @return
     */
    protected final ImageView createIndicatorImgView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(builder.indicatorDrawableRes);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(builder.indicatorWidth, builder.indicatorHeight);
        params.leftMargin = builder.indicatorSpace / 2;
        params.rightMargin = builder.indicatorSpace / 2;
        imageView.setLayoutParams(params);
        return imageView;
    }


    /**
     * function: Builder 参数
     *
     * @param builder
     * @return
     */
    public IndicatorView setBuilder(Builder builder) {
        this.builder = builder;
        return this;
    }

    /**
     * function: 设置指示器显示位置
     *
     * @param currentPosition
     * @return
     */
    public IndicatorView setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        changeIndicatorPosition(currentPosition);
        return this;
    }


    /**
     * function: 改变指示器位置
     *
     * @param currentPosition
     */
    protected void changeIndicatorPosition(int currentPosition) {
        if (currentPosition >= indicatorViews.size()) {
            return;
        }
        ImageView currentIndicatorView = indicatorViews.get(currentPosition);
        for (ImageView indicatorView : indicatorViews) {
            indicatorView.setSelected(false);
            if (indicatorView != currentIndicatorView) {
                indicatorView.requestLayout();
            }
        }
        currentIndicatorView.setSelected(true);
        currentIndicatorView.requestLayout();
    }


    public static class Builder {
        @Dimension
        private int indicatorWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        @Dimension
        private int indicatorHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        @Dimension
        private int indicatorSpace;
        private int indicatorCount;
        @DrawableRes
        private int indicatorDrawableRes = R.drawable.selector_indicator;

        public Builder setIndicatorWidth(@Dimension int indicatorWidth) {
            this.indicatorWidth = indicatorWidth;
            return this;
        }

        public Builder setIndicatorHeight(@Dimension int indicatorHeight) {
            this.indicatorHeight = indicatorHeight;
            return this;
        }

        public Builder setIndicatorSpace(@Dimension int indicatorSpace) {
            this.indicatorSpace = indicatorSpace;
            return this;
        }

        public Builder setIndicatorCount(@Dimension int indicatorCount) {
            this.indicatorCount = indicatorCount;
            return this;
        }

        public Builder setIndicatorDrawableRes(@DrawableRes int indicatorDrawableRes) {
            this.indicatorDrawableRes = indicatorDrawableRes;
            return this;
        }

        public void build(IndicatorView indicatorView) {
            if (indicatorView == null) {
                return;
            }
            indicatorView.setBuilder(this);
            indicatorView.initIndicatorView();
        }
    }
}
