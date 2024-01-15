package io.ganguo.line;

import com.linecorp.linesdk.auth.LineLoginResult;

/**
 * Created by leo on 2018/11/7.
 */
public interface ILineAuthCallback {

    void onAuthSuccess(LineLoginResult result);

    void onAuthCancel();

    void onAuthFailed(String errorMsg);
}
