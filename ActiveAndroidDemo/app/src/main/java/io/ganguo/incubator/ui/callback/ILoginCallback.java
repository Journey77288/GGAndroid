package io.ganguo.incubator.ui.callback;

import io.ganguo.incubator.entity.User;

/**
 * Created by Tony on 10/7/15.
 */
public interface ILoginCallback {

    void loginSuccess(User user);
    void accountError();

}
