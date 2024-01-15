package io.ganguo.viewmodel.pack.common;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.lang.ref.WeakReference;

import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.utils.helper.ResHelper;
import io.ganguo.utils.util.Strings;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.ItemHeaderBinding;
import io.ganguo.viewmodel.core.BaseViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Header Item BaseViewModel
 * Created by Roger on 6/8/16.
 */
public class HeaderItemViewModel extends BaseViewModel<ViewInterface<ItemHeaderBinding>> {
    private ObservableBoolean visible = new ObservableBoolean(true);
    private ObservableField<String> text = new ObservableField<>("");
    private ObservableBoolean isAnimateLayoutChanges = new ObservableBoolean(false);
    private ObservableBoolean clickable = new ObservableBoolean(true);
    private ObservableBoolean isImageVisible = new ObservableBoolean(true);
    private ObservableBoolean isTextVisible = new ObservableBoolean(true);
    private ObservableInt padding = new ObservableInt(0);
    private ObservableInt fontSize = new ObservableInt(10);
    private ObservableInt textColor = new ObservableInt(Color.WHITE);
    private ObservableInt textStyle = new ObservableInt(Typeface.NORMAL);
    private ObservableField<Drawable> drawable = new ObservableField<>();
    private ObservableInt textPadding = new ObservableInt(0);
    private View.OnClickListener clickCommand = onClick();

    private Function1<View, Unit> action;

    public HeaderItemViewModel() {

    }

    public HeaderItemViewModel textStyle(int style) {
        this.textStyle.set(style);
        return this;
    }


    public HeaderItemViewModel text(String text) {
        this.text.set(Strings.nullToEmpty(text));
        return this;
    }


    public HeaderItemViewModel drawableRes(@DrawableRes int drawableRes) {
        this.drawable.set(ResHelper.getDrawable(drawableRes));
        return this;
    }


    public HeaderItemViewModel action(Function1<View, Unit> action) {
        this.action = action;
        return this;
    }

    public HeaderItemViewModel textColorRes(@ColorRes int colorRes) {
        this.textColor.set(ResHelper.getColor(colorRes));
        return this;
    }

    public HeaderItemViewModel textColor(@ColorInt int color) {
        this.textColor.set(color);
        return this;
    }


    public HeaderItemViewModel fontSize(@Dimension int fontSize) {
        this.fontSize.set(fontSize);
        return this;
    }


    public HeaderItemViewModel fontRes(@DimenRes int fontRes) {
        this.fontSize.set(ResHelper.getDimensionPixelSize(fontRes));
        return this;
    }


    public HeaderItemViewModel paddingRes(@DimenRes int paddingRes) {
        this.padding.set(ResHelper.getDimensionPixelOffsets(paddingRes));
        return this;
    }

    public HeaderItemViewModel padding(int padding) {
        this.padding.set(padding);
        return this;
    }


    public HeaderItemViewModel setImageVisible(boolean isVisibleImage) {
        this.isImageVisible.set(isVisibleImage);
        return this;
    }

    public HeaderItemViewModel isAnimateLayoutChanges(boolean isAnimateLayoutChanges) {
        this.isAnimateLayoutChanges.set(isAnimateLayoutChanges);
        return this;
    }


    public HeaderItemViewModel clickable(boolean isClickable) {
        this.clickable.set(isClickable);
        return this;
    }

    public HeaderItemViewModel setTextPadding(int textPadding) {
        this.textPadding.set(textPadding);
        return this;
    }

    @Override
    public void onViewAttached(View view) {
    }


    private View.OnClickListener onClick() {
        return v -> {
            if (action != null) {
                action.invoke(v);
            }
        };
    }

    public ObservableField<String> getText() {
        return text;
    }

    public ObservableBoolean getIsAnimateLayoutChanges() {
        return isAnimateLayoutChanges;
    }

    public ObservableBoolean getClickable() {
        return clickable;
    }

    public ObservableBoolean getIsImageVisible() {
        return isImageVisible;
    }

    public ObservableBoolean getIsTextVisible() {
        return isTextVisible;
    }

    public ObservableInt getPadding() {
        return padding;
    }

    public ObservableInt getFontSize() {
        return fontSize;
    }

    public ObservableInt getTextColor() {
        return textColor;
    }

    public ObservableInt getTextStyle() {
        return textStyle;
    }

    public ObservableField<Drawable> getDrawable() {
        return drawable;
    }

    public ObservableInt getTextPadding() {
        return textPadding;
    }

    public View.OnClickListener getClickCommand() {
        return clickCommand;
    }

    public HeaderItemViewModel setVisible(boolean visible) {
        this.visible.set(visible);
        return this;
    }

    public ObservableBoolean getVisible() {
        return visible;
    }


    public Function1<View, Unit> getAction() {
        return action;
    }

    /**
     * 默认返回样式Item
     */
    public static class BackItemViewModel extends HeaderItemViewModel {
        private WeakReference<Activity> activity;

        public BackItemViewModel(Activity activity) {
            super();
            this.activity = new WeakReference<>(activity);
            initStyle();
        }

        private void initStyle() {
            text("");
            textColorRes(R.color.color_333333);
            paddingRes(R.dimen.dp_19);
            fontRes(R.dimen.font_15);
            action(view -> {
                if (activity.get() != null) {
                    activity.get().finish();
                    activity.clear();
                }
                return Unit.INSTANCE;
            });
        }
    }


    /**
     * 默认Title样式Item
     */
    public static class TitleItemViewModel extends HeaderItemViewModel {

        public TitleItemViewModel(String title) {
            super();
            text(Strings.nullToEmpty(title));
            textColorRes(R.color.white);
            clickable(false);
            fontRes(R.dimen.font_17);
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.item_header;
    }

}
