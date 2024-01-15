package io.ganguo.viewmodel.common;

import android.databinding.ObservableField;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.annotation.DrawableRes;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import io.ganguo.viewmodel.R;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.databinding.IncludeEdittextViewModelBinding;

/**
 * 可用于EditText也可用于TextView(enable = false 即可)
 * 具体使用跟EditText无区别
 * Created by hulkyao on 27/6/2016.
 */
public class EditTextViewModel extends BaseViewModel<ViewInterface<IncludeEdittextViewModelBinding>> {
    public static final String HIDE_ERROR_MESSAGE = "";

    private Builder builder;
    private ObservableField<String> content = null;

    public EditTextViewModel(Builder builder) {
        this.builder = builder;
        this.content = new ObservableField<>(builder.content);
    }

    @Override
    public void onViewAttached(View view) {
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_edittext_view_model;
    }

    public Builder getBuilder() {
        return builder;
    }

    public ObservableField<String> getContent() {
        return content;
    }

    public void clear() {
        content.set("");
        builder.content = "";
    }

    public boolean enable() {
        return builder.enable;
    }

    public int maxCountNumber() {
        return builder.maxCharacters;
    }

    public String getHint() {
        return builder.hint;
    }

    public String getErrorMessage() {
        return builder.errorText;
    }

    public int getLeftIcon() {
        if (builder.showLeftIcon &&
                builder.leftIcon != -1) {
            return builder.leftIcon;
        }
        return 0;
    }

    public int getRightIcon() {
        if (builder.showRightIcon &&
                builder.rightIcon != -1) {
            return builder.rightIcon;
        }
        return 0;
    }

    public int getInputType() {
        return builder.inputType;
    }

    public int getImeOptions() {
        return builder.imeOptions;
    }


    public boolean getVisible() {
        return builder.visible;
    }

    public boolean showCounter() {
        return builder.maxCharacters > 0;
    }

    public boolean showFloatingLabel() {
        if (!builder.enable) {
            return false;
        }
        return builder.showFloatingLabel;
    }

    public boolean showLeftIcon() {
        return builder.leftIcon != -1 && builder.showLeftIcon;
    }

    public boolean showRightIcon() {
        return builder.rightIcon != -1 && builder.showRightIcon;
    }

    public boolean showLeftIconInsideEditText() {
        return builder.leftIcon != -1 && builder.showLeftIconInsideEditText;
    }

    public boolean showRightIconInsideEditText() {
        return builder.leftIcon != -1 && builder.showRightIconInsideEditText;
    }

    public View.OnClickListener leftIconClick() {
        if (builder.leftIconClick == null ||
                builder.leftIcon == -1) {
            return null;
        }
        return builder.leftIconClick;
    }

    public View.OnClickListener rightIconClick() {
        if (builder.rightIconClick == null ||
                builder.rightIcon == -1) {
            return null;
        }
        return builder.rightIconClick;
    }

    public TextViewBindingAdapter.AfterTextChanged afterTextChanged() {
        return builder.afterTextChanged;
    }

    public TextView.OnEditorActionListener getActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == getImeOptions()) {
                    if (builder.onEditorAction != null) {
                        builder.onEditorAction.click(textView);
                        handled = true;
                    }
                }
                return handled;
            }
        };
    }

    public interface OnEditorAction {
        void click(View view);
    }

    public static class Builder {
        private String content = "";
        private String hint = "";
        private String errorText = "";

        private int maxCharacters = 0;
        private int inputType = InputType.TYPE_CLASS_TEXT;
        private int imeOptions = EditorInfo.IME_ACTION_NEXT;

        @DrawableRes
        private int leftIcon = -1;
        @DrawableRes
        private int rightIcon = -1;

        private boolean visible = true;
        private boolean enable = true;
        private boolean showFloatingLabel = true; // showFloatingLabel = false可以关掉动画效果
        private boolean showLeftIcon = false;
        private boolean showRightIcon = false;
        private boolean showLeftIconInsideEditText = false;
        private boolean showRightIconInsideEditText = false;

        private View.OnClickListener leftIconClick = null;
        private View.OnClickListener rightIconClick = null;
        private TextViewBindingAdapter.AfterTextChanged afterTextChanged = null;
        private OnEditorAction onEditorAction = null;

        public Builder() {

        }

        public EditTextViewModel build() {
            return new EditTextViewModel(this);
        }

        public EditTextViewModel.Builder content(String content) {
            this.content = content;
            return this;
        }

        public EditTextViewModel.Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public EditTextViewModel.Builder errorText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        public EditTextViewModel.Builder maxCharacters(int maxCharacters) {
            this.maxCharacters = maxCharacters;
            return this;
        }

        public EditTextViewModel.Builder inputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        public EditTextViewModel.Builder leftIcon(@DrawableRes int leftIcon) {
            this.leftIcon = leftIcon;
            return this;
        }

        public EditTextViewModel.Builder rightIcon(@DrawableRes int rightIcon) {
            this.rightIcon = rightIcon;
            return this;
        }

        public EditTextViewModel.Builder enable(boolean enable) {
            this.enable = enable;
            return this;
        }

        public EditTextViewModel.Builder showLeftIcon(boolean showLeftIcon) {
            this.showLeftIcon = showLeftIcon;
            return this;
        }

        public EditTextViewModel.Builder showRightIcon(boolean showRightIcon) {
            this.showRightIcon = showRightIcon;
            return this;
        }

        public EditTextViewModel.Builder showFloatingLabel(boolean showFloatingLabel) {
            this.showFloatingLabel = showFloatingLabel;
            return this;
        }

        public EditTextViewModel.Builder showLeftIconInsideEditText(boolean showLeftIconInsideEditText) {
            this.showLeftIconInsideEditText = showLeftIconInsideEditText;
            return this;
        }

        public EditTextViewModel.Builder showRightIconInsideEditText(boolean showRightIconInsideEditText) {
            this.showRightIconInsideEditText = showRightIconInsideEditText;
            return this;
        }


        public EditTextViewModel.Builder leftIconClick(View.OnClickListener leftIconClick) {
            this.leftIconClick = leftIconClick;
            return this;
        }

        public EditTextViewModel.Builder rightIconClick(View.OnClickListener rightIconClick) {
            this.rightIconClick = rightIconClick;
            return this;
        }

        public EditTextViewModel.Builder imeOptions(int imeOptions) {
            this.imeOptions = imeOptions;
            return this;
        }

        public EditTextViewModel.Builder afterTextChanged(TextViewBindingAdapter.AfterTextChanged afterTextChanged) {
            this.afterTextChanged = afterTextChanged;
            return this;
        }

        public EditTextViewModel.Builder onEditorAction(OnEditorAction onEditorAction) {
            this.onEditorAction = onEditorAction;
            return this;
        }

        public EditTextViewModel.Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "content='" + content + '\'' +
                    ", hint='" + hint + '\'' +
                    ", errorText='" + errorText + '\'' +
                    ", maxCharacters=" + maxCharacters +
                    ", inputType=" + inputType +
                    ", imeOptions=" + imeOptions +
                    ", leftIcon=" + leftIcon +
                    ", rightIcon=" + rightIcon +
                    ", visible=" + visible +
                    ", enable=" + enable +
                    ", showFloatingLabel=" + showFloatingLabel +
                    ", showLeftIcon=" + showLeftIcon +
                    ", showRightIcon=" + showRightIcon +
                    ", showLeftIconInsideEditText=" + showLeftIconInsideEditText +
                    ", showRightIconInsideEditText=" + showRightIconInsideEditText +
                    ", leftIconClick=" + leftIconClick +
                    ", rightIconClick=" + rightIconClick +
                    ", afterTextChanged=" + afterTextChanged +
                    ", onEditorAction=" + onEditorAction +
                    '}';
        }
    }
}
