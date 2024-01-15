package io.ganguo.library;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import java.util.Locale;

import io.ganguo.library.core.cache.CacheManager;
import io.ganguo.library.core.http.HttpFactory;
import io.ganguo.library.core.image.GFresco;
import io.ganguo.library.core.image.GImageLoader;
import io.ganguo.library.util.AndroidUtils;
import io.ganguo.library.util.log.GLog;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * App上下文环境
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public class BaseContext extends Application {

    private final Logger logger = LoggerFactory.getLogger(BaseContext.class);
    private Locale language = Locale.getDefault();
    protected static BaseContext instance = null;

    public BaseContext() {
        instance = this;
    }

    /**
     * 获取Application
     *
     * @return instance
     */
    public static <T extends BaseContext> T getInstance() {
        return (T) instance;
    }

    /**
     * 应用启动
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Config.register(this);
        HttpFactory.register(this);
        CacheManager.register(this);

        GFresco.getInstance().init(this);
        GImageLoader.getInstance().init(this);

        logger.i("application started.");

        printAppInfo();
    }

    /**
     * 应用退出
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        logger.i("onTerminate.");

        onExit();
    }

    /**
     * 应用内存不足
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();

        logger.i("onLowMemory.");
    }

    /**
     * 应用退出，由AppManager回调
     */
    public void onExit() {
        logger.i("onExit.");
    }

    /**
     * 获取当前语言环境
     *
     * @return language
     */
    public Locale getLanguage() {
        return language;
    }

    /**
     * 设置语言环境
     *
     * @param locale
     */
    public void setLanguage(Locale locale) {
        this.language = locale;

        // 设置res的语言环境
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());

        logger.i(locale);
    }

    /**
     * 打印app信息
     */
    private void printAppInfo() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            PackageInfo packageInfo = AndroidUtils.getPackageInfo(this);
            GLog.i("AppInfo", "****** AppInfo ******");
            GLog.i("AppInfo", " PackageName: " + packageInfo.packageName);
            GLog.i("AppInfo", " VersionCode: " + packageInfo.versionCode);
            GLog.i("AppInfo", " VersionName: " + packageInfo.versionName);

            Bundle bundle = appInfo.metaData;
            if (bundle != null) {
                if (bundle.containsKey("UMENG_CHANNEL")) {
                    GLog.i("AppInfo", " UmengChannel: " + bundle.getString("UMENG_CHANNEL"));
                }
            }
            GLog.i("AppInfo", "*********************");
        } catch (Exception e) {
            GLog.w("AppInfo", "printAppInfo error", e);
        }
    }
}
