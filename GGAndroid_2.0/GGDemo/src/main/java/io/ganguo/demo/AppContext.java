package io.ganguo.demo;

import com.squareup.leakcanary.LeakCanary;

import io.ganguo.demo.database.helper.BoxHelper;
import io.ganguo.demo.http.API;
import io.ganguo.library.BaseApp;
import io.ganguo.library.core.image.PhotoPicassoLoader;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.common.ScreenAdaptors;
import io.ganguo.utils.util.log.Logger;

/**
 * GGDemo AppContext
 * Created by leo on 2016/12/21.
 */
public class AppContext extends BaseApp {
    public static String DATA_BASE_NAME = "GGAndroid_Object_box";

    public static AppContext get() {
        return AppContext.me();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //初始化环境变量
        ScreenAdaptors.init(this);
        ResHelper.init(this);
        Logger.init(this);
        API.init(this);
        PhotoPicassoLoader.init(true);
        BoxHelper.init(this, DATA_BASE_NAME, true);
    }


}
