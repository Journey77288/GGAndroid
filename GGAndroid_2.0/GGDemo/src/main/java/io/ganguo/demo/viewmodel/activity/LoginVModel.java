package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.IncludeSimpleLoginViewModelBinding;
import io.ganguo.utils.common.UIHelper;
import io.ganguo.utils.util.Strings;
import io.ganguo.viewmodel.common.base.BaseLoginViewModel;

/**
 * 登录界面
 * <p/>
 * Created by hulkyao on 26/6/2016.
 */

public class LoginVModel extends BaseLoginViewModel<IncludeSimpleLoginViewModelBinding> {
    @Override
    public void onViewAttached(View view) {
        super.onViewAttached(view);
        // TODO: 28/6/2016  可以添加自定义部分
        // TODO: 如:修改密码部分inputType
        // getPasswordViewModel().getBuilder().inputType(BaseLoginViewModel.getPasswordInputType(true));
        // getView().getBinding().setData(this);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_simple_login_view_model;
    }

    @Override
    public String getAccountHint() {
        return "账号";
    }

    @Override
    public String getPasswordHint() {
        return "密码";
    }

    @Override
    public boolean showFloatingLabel() {
        return true;
    }

    @Override
    public void login(View view, String account, String password) {
        // TODO: 28/6/2016 添加错误判断，错误提示，网络请求
        if (Strings.isEmpty(account)) {
            showAccountErrorMessage("account is error!!!");
            return;
        }
        if (Strings.isEmpty(password)) {
            showPasswordErrorMessage("password is error!!!");
            return;
        }

        hideAccountErrorMessage();
        hidePasswordErrorMessage();
        UIHelper.snackBar(view, " login success ");
    }
}
