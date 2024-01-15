package io.ganguo.demo.database.helper.base;

import android.app.Application;

import io.ganguo.demo.database.model.MyObjectBox;
import io.ganguo.utils.util.Strings;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by leo on 2017/10/16.
 * 数据库处理工具类 - 基类
 */
public class BaseBoxHelper {
    private static String boxDataBaseName;
    private static Application application;
    private static BoxStore boxStore = null;
    private static boolean isAppDebug = true;

    /**
     * function: init param
     *
     * @param isDebug
     * @param context      上下文，一般为Application
     * @param dataBaseName 数据库名称。如果需要动态切换数据库，则需要调用@see {@link  #unRegisterBoxHelper()} ->{@link #init(Application, String, boolean)}  ->{@link #registerBoxHelper()}}
     */
    public static void init(Application context, String dataBaseName, boolean isDebug) {
        unRegisterBoxHelper();
        application = context;
        boxDataBaseName = dataBaseName;
        isAppDebug = isDebug;
        boxStore = registerBoxHelper();
        if (isAppDebug) {
            new AndroidObjectBrowser(boxStore).start(application);
        }
    }

    /**
     * 对ObjectBox数据库进行注册
     *
     * @return
     */
    public static BoxStore registerBoxHelper() {
        if (application == null || Strings.isEmpty(boxDataBaseName)) {
            throw new RuntimeException("Call init BoxHelper at Application! ");
        }
        return MyObjectBox
                .builder()
                .androidContext(application)
                .name(boxDataBaseName)
                .build();
    }

    /**
     * 取消数据库注册
     *
     * @return
     */
    public static void unRegisterBoxHelper() {
        if (boxStore == null) {
            return;
        }
        if (!helper().isClosed()) {
            helper().close();
        }
        boxStore = null;
    }


    /**
     * 获取数据库操作实例
     *
     * @return
     */
    public static BoxStore helper() {
        if (boxStore == null) {
            boxStore = registerBoxHelper();
            if (isAppDebug) {
                new AndroidObjectBrowser(boxStore).start(application);
            }
        }
        return boxStore;
    }

    /**
     * 获取数据库实例操作工具类
     *
     * @param clazz
     * @return
     */
    public static <T> Box<T> box(Class<T> clazz) {
        Box<T> box = helper().boxFor(clazz);
        return box;
    }

}
