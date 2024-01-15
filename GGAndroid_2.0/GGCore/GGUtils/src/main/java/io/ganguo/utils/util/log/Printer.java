package io.ganguo.utils.util.log;

/**
 * Created by hulkyao on 28/12/2016.
 */

public interface Printer {
    Printer tag(String tag);

    Printer extra(boolean showExtra, int method);

    void d(String message, Object... args);

    void d(Object object);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void e(Throwable throwable, Object message);

    void e(Object object);

    void w(String message, Object... args);

    void w(Object object);

    void i(String message, Object... args);

    void i(Object object);

    void v(String message, Object... args);

    void v(Object object);

}
