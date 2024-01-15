package io.ganguo.incubator;

import com.activeandroid.ActiveAndroid;
import com.squareup.otto.Subscribe;

import io.ganguo.library.BaseApp;
import io.ganguo.library.core.event.OnExitEvent;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;
import io.ganguo.opensdk.OpenSDK;

/**
 * App 上下文环境
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class AppContext extends BaseApp {
    private final static Logger logger = LoggerFactory.getLogger(AppContext.class);

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化环境变量
        AppEnv.init(this);

        // init libs
        OpenSDK.init(this, AppEnv.isStage);
        initDb();
    }

    private void initDb() {
        ActiveAndroid.initialize(this);
    }

    /**
     * 应用退出事件
     *
     * @param event
     */
    @Subscribe
    public void onExitEvent(OnExitEvent event) {
        logger.d("on app exit, event:" + event);

    }

}
