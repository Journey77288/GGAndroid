package io.ganguo.utils.util.log;

import android.util.Log;

/**
 * 日志配置文件
 * <p/>
 * Created by Tony on 4/4/15.
 */
public class LogConfig {

    /**
     * 日志实现类，默认为常规Console输出日志，或者文件日志实现类等等
     */
    public static Class<? extends LoggerPrinter> LOGGER_CLASS = LoggerPrinter.class;

    /**
     * 最低日记输出级别
     */
    public static int PRIORITY = Log.VERBOSE;

    /**
     * 保存文件日志的级别
     */
    public static int FILE_PRIORITY = Log.ERROR;

    /**
     * 打印栈信息的最低级别
     */
    public static int STACK_PRIORITY = Log.DEBUG;

    /**
     * 单个日志文件大小 200K
     */
    public static int FILE_MAX_LENGTH = 204800;

    /**
     * 日志长度
     */
    public static int MAX_LENGTH = -1;

    /**
     * Json 打印时缩进
     */
    public static int JSON_INDENT = 4;

    /**
     * TAG 前缀，方便区别系统日志
     */
    public static String TAG_PREFIX = "GLog";

    /**
     * 数据目录
     */
    public static String DATA_PATH = "GGDemo";

    /**
     * 日志文件目录名称
     */
    public static String APP_LOG_PATH = "logs";

    /**
     * 打印栈的个数
     */
    public static int STACK_OFFSET = 2;

    /**
     * 是否打印栈信息
     */
    public static boolean PRINT_STACK_INFO = false;
}
