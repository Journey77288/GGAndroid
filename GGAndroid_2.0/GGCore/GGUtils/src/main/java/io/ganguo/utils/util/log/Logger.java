package io.ganguo.utils.util.log;

import android.content.Context;

import java.lang.reflect.Constructor;

/**
 * 常规日志，输出到Console中
 * <p/>
 * Created by Tony on 4/4/15.
 */
public class Logger {
    private static Printer printer = null;

    public static void init(Context context) {
        try {
            Constructor<? extends LoggerPrinter> constructor
                    = LogConfig.LOGGER_CLASS.getDeclaredConstructor(new Class[]{Context.class});

            printer = constructor.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Printer tag(String tag) {
        return printer.tag(tag);
    }

    public static Printer extra(boolean showExtra, int method) {
        return printer.extra(showExtra, method);
    }

    public static void v(Object msg) {
        printer.v(msg);
    }

    public static void v(String format, Object... objects) {
        printer.v(format, objects);
    }

    public static void d(Object msg) {
        printer.d(msg);
    }

    public static void d(String format, Object... objects) {
        printer.d(format, objects);
    }

    public static void i(Object msg) {
        printer.i(msg);
    }

    public static void i(String format, Object... objects) {
        printer.i(format, objects);
    }

    public static void w(Object msg) {
        printer.w(msg);
    }

    public static void w(String format, Object... objects) {
        printer.w(format, objects);
    }

    public static void e(Object msg) {
        printer.e(msg);
    }

    public static void e(Object msg, Throwable tr) {
        printer.e(tr, msg);
    }

    public static void e(String format, Object... objects) {
        printer.e(format, objects);
    }
}
