package io.ganguo.utils.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.ColorRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import io.ganguo.utils.R;
import io.ganguo.utils.bean.Globals;
import io.ganguo.utils.util.log.Logger;


/**
 * View 处理工具
 * <p>
 * Created by Tony on 9/30/15.
 */
public class Views {

    private Views() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 设置背景
     *
     * @param view
     * @param drawable
     */
    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable drawable) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * Set max text length for textview
     *
     * @param textView
     * @param maxLength
     */
    public static void setMaxLength(TextView textView, int maxLength) {
        if (textView == null) {
            return;
        }
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        textView.setFilters(filters);
    }


    /**
     * 根据tag找到对应的view
     *
     * @param viewGroup
     * @param tag
     * @return
     */
    public static List<View> findViewByTag(ViewGroup viewGroup, Object tag) {
        List<View> views = new LinkedList<>();
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(findViewByTag((ViewGroup) child, tag));
            } else {
                if (Beans.isEquals(child.getTag(), tag)) {
                    views.add(child);
                }
            }
        }
        return views;
    }

    /**
     * 记住view显示状态
     *
     * @param view
     */
    public static void saveVisible(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                //还原所有view
                saveVisible(viewGroup.getChildAt(i));
            }
        } else {
            view.setTag(R.string.tag_view_visible, view.getVisibility());
        }
    }

    /**
     * 复原view的显示
     *
     * @param view
     */
    @SuppressWarnings("ResourceType")
    public static void restoreVisible(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                //还原所有view
                View child = viewGroup.getChildAt(i);
                Object visible = child.getTag(R.string.tag_view_visible);
                if (visible != null) {
                    child.setVisibility((int) visible);
                }
                if (child instanceof ViewGroup) {
                    restoreVisible(child);
                }
            }
        } else {
            Object visible = view.getTag(R.string.tag_view_visible);
            if (visible != null) {
                view.setVisibility((int) visible);
            }
        }
    }

    /**
     * 获取TextView, EditText等控件的文本内容
     *
     * @param tv
     * @return
     */
    public static String getText(TextView tv) {
        return tv.getText().toString();
    }

    /**
     * 清空TextView 文本
     *
     * @param tv
     */
    public static void clearText(TextView tv) {
        tv.setText(null);
    }

    /**
     * 设置可见View.VISIBLE
     *
     * @param views
     */
    public static void visible(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置不可见View.INVISIBLE
     *
     * @param views
     */
    public static void inVisible(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.INVISIBLE) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置不可见View.GONE
     */
    public static void gone(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取textview的真实行高
     * get Font height http://sd4886656.iteye.com/blog/1200890
     *
     * @param view
     * @return
     */
    public static int getFontHeight(TextView view) {
        Paint paint = new Paint();
        paint.setTextSize(view.getTextSize());
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (Math.ceil(fm.descent - fm.ascent));
    }

    /**
     * 必须在activity setContentView,或者Fragment onViewCreated 后面调用,
     *
     * @param view     必须是CoordinatorLayout的子view
     * @param behavior
     */
    public static void setBehavior(View view, CoordinatorLayout.Behavior<View> behavior) {
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.setBehavior(behavior);
        view.requestLayout();
    }


    /**
     * 必须在activity setContentView,或者Fragment onViewCreated 后面调用,
     *
     * @param view  必须是AppBarLayout的子view,
     * @param flags AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
     */
    public static void setScrollFlags(View view, int flags) {
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) view.getLayoutParams();
        params.setScrollFlags(flags);
    }


    /**
     * 移动光标到EditText末尾
     *
     * @param editText
     */
    public static void setSelectionEnd(EditText editText) {
        String text = editText.getText().toString();
        editText.setSelection(Strings.isNotEmpty(text) ? text.length() : 0);
    }

    /**
     * 测量View在屏幕中显示的位置
     *
     * @param view
     */
    public static Rect getViewGlobalRect(View view) {
        Rect rect = new Rect();
        if (view != null) {
            view.getGlobalVisibleRect(rect);
        }
        return rect;
    }

    /**
     * 测量View在屏幕的的尺寸信息
     *
     * @param view
     */
    public static Rect getViewLocalRect(View view) {
        Rect rect = new Rect();
        if (view != null) {
            view.getLocalVisibleRect(rect);
        }
        return rect;
    }


    /**
     * 测量View宽度
     *
     * @param view
     * @return
     */
    public static int onMeasureWidth(View view) {
        int width = 0;
        if (view != null) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            width = view.getMeasuredWidth();
        }
        return width;
    }

    /**
     * 测量View高度
     *
     * @param view
     * @return
     */
    public static int onMeasureHeight(View view) {
        int height = 0;
        if (view != null) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            height = view.getMeasuredHeight();
        }
        return height;
    }


    /**
     * function: cancel RecyclerView scroll
     *
     * @param recyclerView
     */
    public static void onCancelRecyclerViewScroll(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        recyclerView.dispatchTouchEvent(
                MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_CANCEL, 0, 0, 0));
    }

}
