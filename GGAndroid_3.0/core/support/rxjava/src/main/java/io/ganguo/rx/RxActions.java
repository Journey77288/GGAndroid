package io.ganguo.rx;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Locale;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


/**
 * Created by Roger on 6/27/16.
 */
public class RxActions {
    private static final String TAG = "RxActions";

    public static Consumer<Throwable> printThrowable() {
        return printThrowable("");
    }

    public static Consumer<Throwable> printThrowable(final String prefix) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Log.e(TAG, nullToEmpty(prefix) + "_onError: " + throwable);
                throwable.printStackTrace();
            }
        };
    }

    public static <T> Consumer<T> printVariable() {
        return printVariable("");
    }

    public static <T> Consumer<T> printVariable(final String prefix) {
        return new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                if (t != null) {
                    Log.d(TAG, String.format(Locale.US, "%s_variable: %s",
                            nullToEmpty(prefix), t.toString()));
                } else {
                    Log.d(TAG, nullToEmpty(prefix) + "_variable is null");
                }
            }
        };
    }

    public static Action startActivity(final Activity activity, final Intent intent) {
        return new Action() {
            @Override
            public void run() {
                WeakReference<Activity> wrf = new WeakReference<>(activity);
                if (wrf.get() != null) {
                    wrf.get().startActivity(intent);
                }
            }
        };
    }

    public static Action startActivityForResult(final Activity activity, final Intent intent, final int requestCode) {
        return new Action() {
            @Override
            public void run() {
                WeakReference<Activity> wrf = new WeakReference<>(activity);
                if (wrf.get() != null && intent != null) {
                    wrf.get().startActivityForResult(intent, requestCode);
                }
            }
        };
    }

    public static Function1<View, Unit> finishActivity(final Activity activity) {
        return new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                WeakReference<Activity> wrf = new WeakReference<>(activity);
                if (wrf.get() != null) {
                    wrf.get().finish();
                }
                return null;
            }
        };
    }

    public static <T> Consumer<T> finishActivityAction1(final Activity activity) {
        return new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                WeakReference<Activity> wrf = new WeakReference<>(activity);
                if (wrf.get() != null) {
                    wrf.get().finish();
                }
            }
        };
    }

    public static Action onActivityBackPressedAction0(final Activity activity) {
        return new Action() {
            @Override
            public void run() {
                WeakReference<Activity> wrf = new WeakReference<Activity>(activity);
                if (wrf.get() != null) {
                    wrf.get().onBackPressed();
                }
            }
        };
    }

    public static <T> Consumer<T> onActivityBackPressedAction1(final Activity activity) {
        return new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                WeakReference<Activity> wrf = new WeakReference<>(activity);
                if (wrf.get() != null) {
                    wrf.get().onBackPressed();
                }
            }
        };
    }

    private static String nullToEmpty(String str) {
        return (str == null) ? "" : str;
    }
}
