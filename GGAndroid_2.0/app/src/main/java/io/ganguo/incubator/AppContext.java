package io.ganguo.incubator;


import com.squareup.leakcanary.LeakCanary;

import io.ganguo.incubator.http.API;
import io.ganguo.library.BaseApp;
import io.ganguo.library.core.image.PhotoPicassoLoader;
import io.ganguo.qq.QQManager;
import io.ganguo.sina.SinaManager;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.common.ScreenAdaptors;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.wechat.WeChatManager;

/**
 * App 上下文环境
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class AppContext extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        ToastHelper.init(this);
        //初始化环境变量
        ScreenAdaptors.init(this);
        ResHelper.init(this);
        Logger.init(this);
        AppEnv.init(this);
        API.init(this);
        PhotoPicassoLoader.init(AppEnv.isDebug);
        initRxBus();
        initGGOpen();
    }


    /**
     * 初始化一些全局的RxBus事件
     */
    private void initRxBus() {
    }


    public static AppContext get() {
        return AppContext.me();
    }


    /**
     * 初始第三方SDK
     */
    private void initGGOpen() {
        WeChatManager.init(this, AppEnv.WECHAT_APP_ID);
        QQManager.init(this, AppEnv.QQ_APP_ID);
        SinaManager.init(this, AppEnv.SINA_APP_KEY, AppEnv.SINA_REDIRECT_URL, AppEnv.SINA_SCOPE);
    }


}
