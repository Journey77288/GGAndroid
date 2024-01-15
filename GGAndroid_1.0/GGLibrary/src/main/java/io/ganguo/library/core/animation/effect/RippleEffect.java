package io.ganguo.library.core.animation.effect;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import io.ganguo.library.util.AndroidUtils;

/**
 * Created by Tony on 3/29/15.
 */
public class RippleEffect implements IEffect {

    public static final int POINT = 0;
    public static final int CENTER = 1;

    private View mView;

    private float mDownX;
    private float mDownY;
    private float mAlphaFactor;
    private float mRadius;
    private float mMaxRadius;
    private float mWidth;
    private float mHeight;

    private int mStyle = POINT;
    private int mRippleColor;
    private boolean mIsAnimating = false;
    private boolean mHover = true;

    private int mDuration;
    private RadialGradient mRadialGradient;
    private Paint mPaint;
    private ObjectAnimator mRadiusAnimator;

    public RippleEffect(View view) {
        mView = view;

        mDuration = 400;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAlpha(100);
        setRippleColor(Color.WHITE, 0.2f);

    }

    public void setRippleColor(int rippleColor, float alphaFactor) {
        mRippleColor = rippleColor;
        mAlphaFactor = alphaFactor;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setHover(boolean enabled) {
        mHover = enabled;
    }

    @Override
    public void setOnClickListener(final View.OnClickListener l) {
        if (l == null || l instanceof OnClickListener) return;
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        l.onClick(v);
                    }
                }, mDuration);
            }
        });
    }

    private interface OnClickListener extends View.OnClickListener {
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        mMaxRadius = (float) Math.sqrt(w * w + h * h);

        mWidth = w;
        mHeight = h;
    }

    private boolean mAnimationIsCancel;
    private Rect mRect;

    @Override
    public void onTouchEvent(final MotionEvent event) {
//        Log.d("onTouchEvent", "############");
//        Log.d("TouchEventAction", String.valueOf(event.getAction()));
//        Log.d("TouchEventActionMasked", String.valueOf(event.getActionMasked()));
//        Log.d("mIsAnimating", String.valueOf(mIsAnimating));
//        Log.d("mAnimationIsCancel", String.valueOf(mAnimationIsCancel));
        if (!mView.isEnabled()) return;
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN && mHover) {
            mRect = new Rect(mView.getLeft(), mView.getTop(), mView.getRight(), mView.getBottom());
            mAnimationIsCancel = false;
            mDownX = event.getX();
            mDownY = event.getY();

            mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", 0, (int) (mHeight / 1.1));
            mRadiusAnimator.setDuration(mDuration);
            mRadiusAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mRadiusAnimator.start();
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE && mHover) {
            mDownX = event.getX();
            mDownY = event.getY();

            // Cancel the ripple animation when moved outside
            if (mAnimationIsCancel = !mRect.contains(
                    mView.getLeft() + (int) event.getX(),
                    mView.getTop() + (int) event.getY())) {
                setRadius(0);
            } else {
                setRadius((int) (mHeight / 1.1));
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP && !mAnimationIsCancel) {
            mDownX = event.getX();
            mDownY = event.getY();

            float targetRadius;
            if (mStyle == CENTER) {
                targetRadius = mWidth / 2;
            } else {
                float tempRadius = (float) Math.sqrt(mDownX * mDownX + mDownY * mDownY);
                targetRadius = Math.max(tempRadius, mMaxRadius);
            }

            if (mIsAnimating) {
                mRadiusAnimator.cancel();
            }
            mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", AndroidUtils.dpToPx(mView.getContext(), 10), targetRadius);
            mRadiusAnimator.setDuration(mDuration);
            mRadiusAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mRadiusAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mIsAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mIsAnimating = false;
                    setRadius(0);
                    // ViewHelper.setAlpha(mView, 1);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    mIsAnimating = false;
                    setRadius(0);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            mRadiusAnimator.start();

            onEffect(event);
        }
        if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
            mView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mIsAnimating) {
                        mRadiusAnimator.cancel();
                    }
                    mIsAnimating = false;
                    setRadius(0);
                }
            }, mDuration + 50);
        }
    }

    protected void onEffect(MotionEvent event) {

    }

    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void setRadius(final float radius) {
        mRadius = radius;
        if (mRadius > 0) {
            if (mStyle == CENTER) {
                mRadialGradient = new RadialGradient(mWidth / 2, mHeight / 2, mRadius,
                        adjustAlpha(mRippleColor, mAlphaFactor), mRippleColor,
                        Shader.TileMode.MIRROR);
                mPaint.setShader(mRadialGradient);
            } else {
                mRadialGradient = new RadialGradient(mDownX, mDownY, mRadius,
                        adjustAlpha(mRippleColor, mAlphaFactor), mRippleColor,
                        Shader.TileMode.MIRROR);
                mPaint.setShader(mRadialGradient);
            }

        }
        mView.invalidate();
    }

//    private Path mPath = new Path();

    @Override
    public void onDraw(final Canvas canvas) {
        if (mView.isInEditMode()) {
            return;
        }

//        canvas.save(Canvas.CLIP_SAVE_FLAG);
//
//        mPath.reset();
//        mPath.addCircle(mDownX, mDownY, mRadius, Path.Direction.CW);
//
        // 4.0 UnsupportedOperationException
//        canvas.clipPath(mPath);
//        canvas.restore();

        if (mStyle == CENTER) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        } else {
            canvas.drawCircle(mDownX, mDownY, mRadius, mPaint);
        }
    }

    @Override
    public void setStyle(int style) {
        mStyle = style;
    }
}
