package ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

import io.ganguo.library.core.drawable.MaterialProgressDrawable;

/**
 * Created by Tony on 4/29/15.
 */
public class LoadingView extends ImageView {

    private MaterialProgressDrawable mProgress;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mProgress = new MaterialProgressDrawable(getContext(), this);
        mProgress.setAlpha(255);
        mProgress.setBackgroundColor(Color.TRANSPARENT);
        mProgress.setColorSchemeColors(
                getResources().getColor(R.color.loading_color_one),
                getResources().getColor(R.color.loading_color_two),
                getResources().getColor(R.color.loading_color_three)
        );
        setImageDrawable(mProgress);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mProgress.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mProgress.stop();
    }
}
