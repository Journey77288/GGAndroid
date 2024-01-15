package io.ganguo.viewmodel.common.base;

import android.databinding.ViewDataBinding;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import io.ganguo.viewmodel.BR;
import io.ganguo.viewmodel.common.EditTextViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * 简单的登陆界面，继承之后进行扩展
 * <p/>
 * Created by hulkyao on 26/6/2016.
 */

public abstract class BaseLoginViewModel<T extends ViewDataBinding> extends BaseViewModel<ViewInterface<T>> {
    private EditTextViewModel accountViewModel;
    private EditTextViewModel passwordViewModel;

    @Override
    public void onViewAttached(View view) {
        accountViewModel = new EditTextViewModel.Builder()
                .hint(getAccountHint())
                .showFloatingLabel(showFloatingLabel())
                .build();

        passwordViewModel = new EditTextViewModel.Builder()
                .hint(getPasswordHint())
                .showFloatingLabel(showFloatingLabel())
                .imeOptions(EditorInfo.IME_ACTION_DONE)
                .inputType(getPasswordVisibleInputType(false))
                .onEditorAction(new EditTextViewModel.OnEditorAction() {
                    @Override
                    public void click(View view) {
                        login(view, getAccount(), getPassword());
                    }
                })
                .build();
    }

    public View.OnClickListener clickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checkParams
                login(view, getAccount(), getPassword());
            }
        };
    }

    /**
     * 控制error message提示（文本框下提示）
     *
     * @param viewModel
     * @param message
     */
    private void showErrorMessage(EditTextViewModel viewModel, String message) {
        if (viewModel == null) {
            return;
        }
        viewModel.getBuilder().errorText(message);
        getView().getBinding().setVariable(BR.data, this);
    }

    /**
     * 显示账号错误提示（文本框下提示）
     */
    public void showAccountErrorMessage(String message) {
        showErrorMessage(accountViewModel, message);
    }

    /**
     * 隐藏账号错误提示（文本框下提示）
     */
    public void hideAccountErrorMessage() {
        showErrorMessage(accountViewModel, EditTextViewModel.HIDE_ERROR_MESSAGE);
    }

    /**
     * 显示密码错误提示（文本框下提示）
     */
    public void showPasswordErrorMessage(String message) {
        showErrorMessage(passwordViewModel, message);
    }

    /**
     * 隐藏密码错误提示（文本框下提示）
     */
    public void hidePasswordErrorMessage() {
        showErrorMessage(passwordViewModel, EditTextViewModel.HIDE_ERROR_MESSAGE);
    }

    /**
     * 获取用户输入的账号
     *
     * @return
     */
    public String getAccount() {
        if (accountViewModel == null) {
            return "";
        }
        return accountViewModel.getContent().get();
    }

    /**
     * 获取用户输入的密码
     *
     * @return
     */
    public String getPassword() {
        if (passwordViewModel == null) {
            return "";
        }
        return passwordViewModel.getContent().get();
    }

    /**
     * 可用于改变密码是否显示
     *
     * @param showPassword
     * @return
     */
    public static int getPasswordVisibleInputType(boolean showPassword) {
        if (showPassword) {
            return InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
        }
        return InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD;
    }

    public EditTextViewModel getAccountViewModel() {
        return accountViewModel;
    }

    public EditTextViewModel getPasswordViewModel() {
        return passwordViewModel;
    }

    public abstract int getItemLayoutId();

    public abstract boolean showFloatingLabel();

    public abstract String getAccountHint();

    public abstract String getPasswordHint();

    public abstract void login(View view, String account, String password);
}
