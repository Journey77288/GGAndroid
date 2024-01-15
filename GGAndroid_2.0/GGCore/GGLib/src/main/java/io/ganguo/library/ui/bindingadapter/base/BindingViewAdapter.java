package io.ganguo.library.ui.bindingadapter.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 基类： BindingAdapter
 * <p>
 * 1、说明：该类下，一般存放View通用属性的Binding函数
 * 2、binding属性命名规范：android:bind_属性名称。(加bind_前缀，避免和控件原有属性搞混)
 * 3、binding函数命名规范：以onBind开头即可
 * </p>
 * Created by leo on 2018/5/30.
 */
public class BindingViewAdapter {

    /**
     * function:xml绑定设置View 宽度
     *
     * @param view
     * @param bindHeight 高度px
     */
    @BindingAdapter("android:bind_view_height")
    public static void onBindViewHeight(View view, @Dimension int bindHeight) {
        int viewHeight;
        if (bindHeight > 0) {
            viewHeight = bindHeight;
        } else if (bindHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
            viewHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            viewHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = viewHeight;
        view.setLayoutParams(params);
    }

    /**
     * function:xml绑定设置View 宽度
     *
     * @param view
     * @param bindWidth 宽度px
     */
    @BindingAdapter("android:bind_view_width")
    public static void onBindViewWidth(View view, @Dimension int bindWidth) {
        int viewWidth;
        if (bindWidth > 0) {
            viewWidth = bindWidth;
        } else if (bindWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
            viewWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            viewWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = viewWidth;
        view.setLayoutParams(params);
    }


    /**
     * function:xml绑定设置View背景颜色
     *
     * @param view
     * @param colorRes Drawable资源Id
     */
    @BindingAdapter("android:bind_bg_res")
    public static void onBindBackgroundRes(View view, @DrawableRes int colorRes) {
        view.setBackgroundResource(colorRes);
    }


    /**
     * function:xml绑定设置View背景颜色
     *
     * @param view
     * @param color
     */
    @BindingAdapter("android:bind_bg_color")
    public static void onBindBackgroundColor(View view, @ColorInt int color) {
        view.setBackgroundColor(color);
    }

    /**
     * function:xml绑定设置View显示状态
     *
     * @param view
     * @param visibility
     */
    @BindingAdapter("android:bind_visibility")
    public static void onBindVisibility(View view, int visibility) {
        switch (visibility) {
            case View.VISIBLE:
                view.setVisibility(View.VISIBLE);
                break;
            case View.INVISIBLE:
                view.setVisibility(View.INVISIBLE);
                break;
            case View.GONE:
                view.setVisibility(View.GONE);
                break;
            default:
                view.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * function:xml绑定设置View显示状态
     *
     * @param view
     * @param visible
     */
    @BindingAdapter("android:bind_visibility_boolean")
    public static void onBindVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    /**
     * function:xml绑定点击拨打电话号码事件
     *
     * @param view
     * @param phoneNumber
     */
    @BindingAdapter(value = {"android:bind_call_click"})
    public static void onBindCallClick(View view, final String phoneNumber) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber.trim()));
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     * function:xml绑定设置View Margin
     *
     * @param view
     * @param margin
     */
    @BindingAdapter(value = {"android:bind_margin"}, requireAll = false)
    public static void onBindMargin(View view, int margin) {
        if (view == null) {
            return;
        }
        onBindMargin(view, margin, margin, margin, margin);
    }


    /**
     * function:xml绑定设置View Margin
     *
     * @param view
     * @param paddingBottom
     * @param paddingLeft
     * @param paddingRight
     * @param paddingTop
     */
    @BindingAdapter(value = {"android:bind_padding_left", "android:bind_padding_top", "android:bind_padding_right", "android:bind_padding_bottom"}, requireAll = false)
    public static void onBindPadding(View view, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        if (view == null) {
            return;
        }
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * function:xml绑定View居中属性
     *
     * @param view
     * @param gravity
     */
    @BindingAdapter(value = {"android:bind_view_gravity"}, requireAll = false)
    public static void onBindViewGravity(View view, int gravity) {
        if (view == null) {
            return;
        }
        if (view instanceof TextView) {
            ((TextView) view).setGravity(gravity);
        } else if (view instanceof LinearLayout) {
            ((LinearLayout) view).setGravity(gravity);
        }
    }


    /**
     * function:xml绑定设置View Margin
     *
     * @param view
     * @param marginBottom
     * @param marginLeft
     * @param marginRight
     * @param marginTop
     */
    @BindingAdapter(value = {"android:bind_margin_left", "android:bind_margin_top", "android:bind_margin_right", "android:bind_margin_bottom"}, requireAll = false)
    public static void onBindMargin(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        if (view == null) {
            return;
        }
        ViewGroup.MarginLayoutParams layoutParams;
        if (view.getLayoutParams() == null) {
            layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        } else if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        } else if (view.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
            layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        } else if (view.getLayoutParams() instanceof GridLayoutManager.LayoutParams) {
            layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        } else if (view.getLayoutParams() instanceof RecyclerView.LayoutParams) {
            layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        } else {
            layoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        }
        setViewMargins(view, layoutParams, marginLeft, marginTop, marginRight, marginBottom);
    }

    /**
     * function:xml绑定设置View Margin
     *
     * @param view
     * @param layoutParams
     * @param marginBottom
     * @param marginLeft
     * @param marginRight
     * @param marginTop
     */
    public static void setViewMargins(View view, ViewGroup.MarginLayoutParams layoutParams, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        if (marginLeft != layoutParams.leftMargin ||
                marginTop != layoutParams.topMargin ||
                marginRight != layoutParams.rightMargin ||
                marginBottom != layoutParams.bottomMargin) {
            layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
            view.setLayoutParams(layoutParams);
        }
    }
}
