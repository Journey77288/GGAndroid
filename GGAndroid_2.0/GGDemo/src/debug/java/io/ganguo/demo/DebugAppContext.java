package io.ganguo.demo;

import com.facebook.stetho.Stetho;

import io.ganguo.demo.http.API;

/**
 * 以下代码仅在 Debug Build Type 下运行，不影响 Release 环境
 * 可在此初始化 LeakCanary, Stetho 等仅在 Debug 运行的库
 * Created by Roger on 14/12/2016.
 */

public class DebugAppContext extends AppContext {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(AppContext.get());
        API.init(this);
    }

    public static DebugAppContext get() {
        return AppContext.me();
    }
}
