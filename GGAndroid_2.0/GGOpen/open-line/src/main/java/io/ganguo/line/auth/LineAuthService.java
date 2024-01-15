package io.ganguo.line.auth;

import android.app.Activity;
import android.content.Intent;

import com.linecorp.linesdk.auth.LineLoginApi;

import io.ganguo.line.ILineAuthCallback;
import io.ganguo.line.LineManager;
import io.ganguo.open.sdk.IService;
import io.ganguo.utils.util.Strings;

/**
 * Line登录
 * Created by leo on 2018/11/7.
 */
public class LineAuthService implements IService {
    private Activity activity;
    private ILineAuthCallback callback;
    private String lineChannelId;

    private LineAuthService() {
    }


    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static LineAuthService get() {
        return LineAuthService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static LineAuthService HOLDER = new LineAuthService();
    }

    /**
     * function：get call back
     *
     * @return
     */
    public ILineAuthCallback getCallback() {
        return callback;
    }


    /**
     * function：set app channel Id
     *
     * @return
     */
    public LineAuthService setLineChannelId(String lineChannelId) {
        this.lineChannelId = lineChannelId;
        return this;
    }

    /**
     * function：处理认证参数
     *
     * @param callback
     * @param activity
     */
    public LineAuthService applyLineAuthData(Activity activity, ILineAuthCallback callback) {
        this.activity = activity;
        this.callback = callback;
        return this;
    }

    /**
     * function：set call back
     *
     * @return
     */
    public LineAuthService setCallback(ILineAuthCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * function：登录应用
     *
     * @return
     */
    @Override
    public boolean apply() {
        if (Strings.isEmpty(lineChannelId)) {
            throw new RuntimeException("please is LineSdk init to AppContext!!!");
        }
        Intent intent = LineLoginApi.getLoginIntent(activity, lineChannelId);
        activity.startActivityForResult(intent, LineManager.LINE_LOGIN_REQUEST);
        return false;
    }

    /**
     * function：资源释放
     */
    @Override
    public void release() {
        setCallback(null);
    }
}
