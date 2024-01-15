package io.ganguo.viewmodel.common;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.lang.ref.WeakReference;

import io.ganguo.rx.RxActions;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.Strings;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.ItemHeaderBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

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
    private Consumer<View> consumer;

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


    public HeaderItemViewModel action(Consumer<View> consumer) {
        this.consumer = consumer;
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
            try {
                if (consumer != null) {
                    consumer.accept(v);
                }
            } catch (Exception e) {
                e.printStackTrace();
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


    public Consumer<View> getConsumer() {
        return consumer;
    }

    /**
     * 默认返回样式Item
     */
    public static class BackItemViewModel extends HeaderItemViewModel {
        private WeakReference<Activity> activity;

        public BackItemViewModel(Activity activity) {
            super();
            this.activity = new WeakReference<Activity>(activity);
            initStyle();
        }

        private void initStyle() {
            text("");
            textColorRes(R.color.white);
            paddingRes(R.dimen.dp_10);
            action(Functions.actionConsumer(RxActions.finishActivity(activity.get())));
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
    public void onDestroy() {
        consumer = null;
        super.onDestroy();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_header;
    }

}
