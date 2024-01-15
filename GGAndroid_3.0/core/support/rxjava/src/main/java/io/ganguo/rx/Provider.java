package io.ganguo.rx;


import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.ganguo.rx.utils.RxHelperKt;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import static io.ganguo.rx.utils.RxHelperKt.checkNull;

/**
 * Created by Roger on 28/07/2017.
 */

public class Provider<T> {
    private Observable<T> source;
    private T initialValue;
    private int mode = DEFAULT_MODE;

    /**
     * All mode is off.
     */
    public static final int NONE = 1;
    /**
     * If next value is same as current, not set and not notify.
     */
    public static final int DISTINCT_UNTIL_CHANGED = 1 << 1;
    /**
     * Sends notification on the instance created and subscribed.
     */
    public static final int RAISE_LATEST_VALUE_ON_SUBSCRIBE = 1 << 2;

    /**
     * Default Mode
     */
    public static final int DEFAULT_MODE = DISTINCT_UNTIL_CHANGED | RAISE_LATEST_VALUE_ON_SUBSCRIBE;

    public Provider<T> source(@NonNull Observable<T> source) {
        checkNull(source, "source");
        this.source = source;
        return this;
    }

    public Provider<T> defaultValue(@NonNull T initialValue) {
        checkNull(initialValue, "initialValue");
        this.initialValue = initialValue;
        return this;
    }

    public Provider<T> mode(@MODE int mode) {
        this.mode = mode;
        return this;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(flag = true, value = {NONE, DISTINCT_UNTIL_CHANGED, RAISE_LATEST_VALUE_ON_SUBSCRIBE})
    public @interface MODE {

    }

    static class ProviderHelper {
        static <T> Observable<T> getSource(@Nullable Provider<T> provider) {
            RxHelperKt.checkNull(provider, "provider");
            final Observable<T> source = provider.source;
            return source == null ? Observable.<T>never() : source;
        }

        static <T> T getDefaultValue(@Nullable Provider<T> provider) {
            RxHelperKt.checkNull(provider, "provider");
            return provider.initialValue;
        }

        static <T> Maybe<T> getInitialMaybe(@Nullable Provider<T> provider) {
            RxHelperKt.checkNull(provider, "provider");
            final T initialValue = provider.initialValue;
            return initialValue == null ? Maybe.<T>empty() : Maybe.just(provider.initialValue);
        }

        @MODE
        static <T> int getMode(@Nullable Provider<T> provider) {
            RxHelperKt.checkNull(provider, "provider");
            return provider.mode;
        }
    }
}
