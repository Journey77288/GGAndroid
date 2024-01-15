package io.ganguo.incubator;

import android.content.Context;
import android.util.Log;

import io.ganguo.library.Config;
import io.ganguo.utils.util.Networks;
import io.ganguo.utils.util.Screens;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.Systems;
import io.ganguo.utils.util.log.FileLoggerPrinter;
import io.ganguo.utils.util.log.LogConfig;
import io.ganguo.utils.util.log.Logger;

/**
 * App 环境配置
 * <p/>
 * Created by Tony on 3/4/15.
 */
public class AppEnv {
    /**
     * 开发环境配置信息
     * 根据app/build.gradle生成
     */
    public final static boolean isStage = BuildConfig.isStage;// 测试服务器true, 生产服务器为false
    public final static boolean isDebug = Strings.isEquals("debug", BuildConfig.BUILD_TYPE);// debug包为true, release包为false
    public final static int VERSION_CODE = BuildConfig.VERSION_CODE; //apk 版本号
    public final static String VERSION_NAME = BuildConfig.VERSION_NAME;// apk 版本名称
    public final static String PACKAGE_NAME = BuildConfig.APPLICATION_ID;//包名
    public final static String BASE_URL = BuildConfig.BASE_URL;//server url
    public final static String WECHAT_APP_ID = BuildConfig.WECHAT_APP_ID;
    public final static String WECHAT_APP_SECRET = BuildConfig.WECHAT_APP_SECRET;
    public final static String QQ_APP_ID = BuildConfig.QQ_APP_ID;
    public final static String ALIPAY_APP_KEY = BuildConfig.ALIPAY_APP_KEY;
    public final static String SINA_APP_KEY = BuildConfig.SINA_APP_KEY;
    public final static String SINA_REDIRECT_URL = BuildConfig.SINA_REDIRECT_URL;
    public final static String SINA_SCOPE = BuildConfig.SINA_SCOPE;

    /**
     * init
     */
    public static void init(Context context) {
        // App 数据保存目录
        Config.DATA_PATH = BuildConfig.DATA_PATH;

        // 日志配置
        LogConfig.LOGGER_CLASS = FileLoggerPrinter.class;
        LogConfig.PRIORITY = BuildConfig.LOG_LEVEL;
        LogConfig.FILE_PRIORITY = Log.ERROR;

        // app info
        Logger.i("****** APP_ENVIRONMENT ******");
        Logger.i(" APPLICATION_ID: " + BuildConfig.APPLICATION_ID);
        Logger.i(" isStage: " + isStage);
        Logger.i(" isDebug: " + isDebug);
        Logger.i(" FLAVOR: " + BuildConfig.FLAVOR);
        Logger.i(" BUILD_TYPE: " + BuildConfig.BUILD_TYPE);
        Logger.i(" VERSION_CODE: " + BuildConfig.VERSION_CODE);
        Logger.i(" VERSION_NAME: " + BuildConfig.VERSION_NAME);
        Logger.i(" SCREEN_SIZE: " + Screens.getScreenWidth(context) + "x" + Screens.getScreenHeight(context));
        Logger.i(" DEVICE_PERFORMANCE: " + Systems.getPerformance(context));
        Logger.i(" BASE_URL: " + BASE_URL);
        Logger.i(" DATA_PATH: " + Config.DATA_PATH);
        Logger.i(" LOG_CONSOLE: " + LogConfig.PRIORITY);
        Logger.i(" LOG_FILE: " + LogConfig.FILE_PRIORITY);
        Logger.i(" IP_ADDRESS: " + Networks.getIpAddress(context));
        Logger.i("***************************");
    }

}
