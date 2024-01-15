package io.ganguo.utils.util.log;

import android.content.Context;
import android.util.Log;

import java.util.Locale;

import io.ganguo.utils.util.Strings;

/**
 * Created by hulkyao on 28/12/2016.
 */

public class LoggerPrinter implements Printer {
    /**
     * 默认初始值
     */
    private static final int DEFAULT_CURRENT_CLASS_POSITION = 2;
    private static final int DEFAULT_CURRENT_METHOD_POSITION = 2;

    private static final int DEFAULT_METHOD_COUNT = -1;
    private static final boolean DEFAULT_SHOW_EXTRA = false;
    private static final String DEFAULT_TAG = "default_tag";

    /**
     * logcat上面的区分标示
     */
    private static final String THREAD_TITLE = "Thread : ";
    private static final String DIVIDER = "********************************************************************************";

    /**
     * 本地的配置参数
     */
    private ThreadLocal<Boolean> showExtraLocal = new ThreadLocal<Boolean>();
    private ThreadLocal<String> tagLocal = new ThreadLocal<String>();
    private ThreadLocal<Integer> methodLocal = new ThreadLocal<Integer>();

    private Context mContext = null;

    /**
     * 构造器，初始化本地数据
     */
    public LoggerPrinter(Context context) {
        mContext = context;

        showExtraLocal.set(DEFAULT_SHOW_EXTRA);
        tagLocal.set(DEFAULT_TAG);
        methodLocal.set(DEFAULT_METHOD_COUNT);
    }

    @Override
    public Printer tag(String tag) {
        tagLocal.set(tag);
        return this;
    }

    @Override
    public Printer extra(boolean showExtra, int method) {
        showExtraLocal.set(showExtra);
        methodLocal.set(method);
        return this;
    }

    @Override
    public void d(String message, Object... args) {
        String msg = String.format(Locale.getDefault(), message, args);
        println(Log.DEBUG, msg, null);
    }

    @Override
    public void d(Object object) {
        println(Log.DEBUG, object, null);
    }

    @Override
    public void e(String message, Object... args) {
        String msg = String.format(Locale.getDefault(), message, args);
        println(Log.ERROR, msg, null);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        String msg = String.format(Locale.getDefault(), message, args);
        println(Log.ERROR, msg, throwable);
    }

    @Override
    public void e(Throwable throwable, Object message) {
        println(Log.ERROR, message, throwable);
    }

    @Override
    public void e(Object object) {
        println(Log.ERROR, object, null);
    }

    @Override
    public void w(String message, Object... args) {
        String msg = String.format(Locale.getDefault(), message, args);
        println(Log.WARN, msg, null);
    }

    @Override
    public void w(Object object) {
        println(Log.WARN, object, null);
    }

    @Override
    public void i(String message, Object... args) {
        String msg = String.format(Locale.getDefault(), message, args);
        println(Log.INFO, msg, null);
    }

    @Override
    public void i(Object object) {
        println(Log.INFO, object, null);
    }

    @Override
    public void v(String message, Object... args) {
        String msg = String.format(Locale.getDefault(), message, args);
        println(Log.VERBOSE, msg, null);
    }

    @Override
    public void v(Object object) {
        println(Log.VERBOSE, object, null);
    }

    /**
     * 按级别输出日志到Console中
     *
     * @param priority
     * @param msg
     * @param tr
     * @return
     */
    protected void println(int priority, Object msg, Throwable tr) {
        final Thread thread = Thread.currentThread();
        final String threadName = thread.getName();
        final StackTraceElement[] elements = thread.getStackTrace();

//        // test info log
//        for (StackTraceElement element : elements) {
//            Log.i("xxxx", "className: " + element.getClassName() + " getMethodName:" + element.getMethodName());
//        }

        boolean globalShowExtra = LogConfig.PRINT_STACK_INFO && priority >= LogConfig.STACK_PRIORITY;
        boolean showExtraMessage = getLocalShowExtra() || globalShowExtra;

        int currentPosition = getCurrentClassPosition(elements);
        int currentMethodPosition = showExtraMessage ? DEFAULT_CURRENT_METHOD_POSITION : DEFAULT_CURRENT_METHOD_POSITION + 1;
        int methodStackOffset = currentPosition + currentMethodPosition;

        String tagTitle = LogConfig.TAG_PREFIX + "#";
        String globalTag = getSimpleClassName(elements[methodStackOffset].getClassName()) + "." + elements[methodStackOffset].getMethodName()+ "() ";
        String localTag = getLocalTag();
        String tag = Strings.isEquals(localTag, DEFAULT_TAG) ? tagTitle + globalTag : tagTitle + localTag;

        if (showExtraMessage) {
            printDivider(tag, priority);
        }

        if (showExtraMessage) {
            printHeader(threadName, elements, methodStackOffset, tag, priority, tr);
        }

        printContent(tag, priority, msg, tr);

        if (showExtraMessage) {
            printDivider(tag, priority);
        }
    }

    private int getCurrentClassPosition(StackTraceElement[] elements) {
        for (int temp = 0; temp < elements.length; temp++) {
            StackTraceElement element = elements[temp];
            if (element.getClassName().equals(LoggerPrinter.class.getName())) {
                return temp;
            }
        }
        return DEFAULT_CURRENT_CLASS_POSITION;
    }

    private void printHeader(String threadName, StackTraceElement[] elements, int currentPosition, String tag, int priority, Throwable tr) {
        printContent(tag, priority, THREAD_TITLE + threadName, tr);

        String blank = "";
        final int local = getLocalMethod();
        final int method = local == -1 ? LogConfig.STACK_OFFSET : local;
//        Log.d("printHeader", "local: " + local);
//        Log.d("printHeader", "method: " + method);

        for (int i = method - 1; i >= 0; i--) {
            StackTraceElement element = elements[currentPosition + i];

            StringBuilder builder = new StringBuilder();
            builder.append(blank)
                    .append(getSimpleClassName(element.getClassName()))
                    .append(".")
                    .append(element.getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(element.getFileName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")");

            blank += "  ";

//            Log.d("printHeader", "builder: " + builder);
            printContent(tag, priority, builder, tr);
        }
    }

    private static void printContent(String tag, int priority, Object msg, Throwable tr) {
        switch (priority) {
            case Log.VERBOSE:
                GLog.v(tag, msg, tr);
                break;
            case Log.DEBUG:
                GLog.d(tag, msg, tr);
                break;
            case Log.INFO:
                GLog.i(tag, msg, tr);
                break;
            case Log.WARN:
                GLog.w(tag, msg, tr);
                break;
            case Log.ERROR:
                GLog.e(tag, msg, tr);
                break;
        }
    }

    private static void printDivider(String tag, int priority) {
        printContent(tag, priority, DIVIDER, null);
    }

    private static String getSimpleClassName(String className) {
        int lastIndex = className.lastIndexOf(".");
        return className.substring(lastIndex + 1);
    }

    private String getLocalTag() {
        String tag = tagLocal.get() == null ? DEFAULT_TAG : tagLocal.get();
        if (!Strings.isEquals(DEFAULT_TAG, tag)) {
            tagLocal.set(DEFAULT_TAG);
            return tag;
        }
        return DEFAULT_TAG;
    }

    private int getLocalMethod() {
        int method = methodLocal.get() == null ? DEFAULT_METHOD_COUNT : methodLocal.get();
        if (method != DEFAULT_METHOD_COUNT) {
            methodLocal.set(DEFAULT_METHOD_COUNT);
            return method;
        }
        return DEFAULT_METHOD_COUNT;
    }

    private boolean getLocalShowExtra() {
        boolean showExtra = showExtraLocal.get() == null ? false : showExtraLocal.get();
        if (showExtra) {
            showExtraLocal.set(DEFAULT_SHOW_EXTRA);
            return true;
        }
        return false;
    }

    public Context getContext() {
        return mContext;
    }
}
