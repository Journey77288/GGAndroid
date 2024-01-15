package io.ganguo.rx;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Maybe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Cancellable;
import io.reactivex.subjects.Subject;

/**
 * Created by Roger on 21/07/2017.
 */

public class RxHelper {
    private RxHelper() {
        throw new AssertionError("No instances.");
    }

    public static <T> void checkNull(@Nullable T value, @NonNull String name) {
        if (value == null) {
            throw new NullPointerException(name + " must not be null.");
        }
    }

    public static <T> Maybe<T> createInitialMaybe(@Nullable T initialValue) {
        checkNull(initialValue, "initialValue");
        return Maybe.just(initialValue);
    }

    public static <T> void safeComplete(@NonNull Subject<T> emitter) {
        if (!emitter.hasThrowable() && !emitter.hasComplete()) {
            emitter.onComplete();
        }
    }

    public static void safeDispose(@Nullable Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void safeCancel(@Nullable Cancellable cancellable) {
        if (cancellable == null) {
            return;
        }

        try {
            cancellable.cancel();
        } catch (Exception e) {
            // Ignore the exception.
        }
    }

    public static boolean checkMainThread(Observer<?> observer) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onSubscribe(Disposables.empty());
            observer.onError(new IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().getName()));
            return false;
        }
        return true;
    }
}
