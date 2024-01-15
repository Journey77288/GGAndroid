package io.ganguo.rx;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicBoolean;

import io.ganguo.rx.utils.RxHelperKt;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static io.ganguo.rx.utils.RxHelperKt.checkNull;
import static io.ganguo.rx.utils.RxHelperKt.createInitialMaybe;


/**
 * Two-way bindable and observable property for Android Data Binding.
 */
public class RxProperty<T> extends Observable<T> implements androidx.databinding.Observable, Disposable {

    /**
     * Mode of the {@link RxProperty}.
     */

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

    private final boolean isDistinctUntilChanged;

    @NonNull
    private final RxPropertyField<T> valueField;
    @NonNull
    private final Subject<T> valueEmitter;
    @NonNull
    private final Disposable sourceDisposable;
    @NonNull
    private final AtomicBoolean isDisposed = new AtomicBoolean(false);
    @Nullable
    private Cancellable cancellable = null;

    /**
     * Creates {@code RxProperty} without an initial value.
     */
    public RxProperty() {
        this(Observable.<T>never(), Maybe.<T>empty(), DEFAULT_MODE);
    }

    public RxProperty(@NonNull T defaultValue) {
        this(Observable.<T>never(), createInitialMaybe(defaultValue), DEFAULT_MODE);
    }

    public RxProperty(@NonNull Observable<T> source) {
        this(source, Maybe.<T>empty(), DEFAULT_MODE);
    }

    public RxProperty(@NonNull Provider<T> provider) {
        this(Provider.ProviderHelper.getSource(provider),
                Provider.ProviderHelper.getInitialMaybe(provider),
                Provider.ProviderHelper.getMode(provider));
    }

    /**
     * Creates {@code RxProperty} from the specified {@link Observable} with the initial value and the
     * specified mode.
     *
     * @param source       a source {@link Observable} of this {@code RxProperty}
     * @param initialMaybe the initial value of this {@code RxProperty}
     * @param mode         mode of this {@code RxProperty}
     */
    protected RxProperty(@NonNull Observable<T> source, @NonNull Maybe<T> initialMaybe,
                         @MODE int mode) {
        checkNull(source, "source");

        T initialValue = initialMaybe.blockingGet();
        valueField = new RxPropertyField<>(this, initialValue);

        // Set modes.
        isDistinctUntilChanged =
                (mode & NONE) != NONE && (mode & DISTINCT_UNTIL_CHANGED) == DISTINCT_UNTIL_CHANGED;
        boolean isRaiseLatestValueOnSubscribe
                = (mode & NONE) != NONE && (mode & RAISE_LATEST_VALUE_ON_SUBSCRIBE) == RAISE_LATEST_VALUE_ON_SUBSCRIBE;

        // Create emitter.
        if (isRaiseLatestValueOnSubscribe) {
            valueEmitter = (initialValue != null ?
                    BehaviorSubject.createDefault(initialValue) :
                    BehaviorSubject.<T>create()).toSerialized();
        } else {
            valueEmitter = PublishSubject.<T>create().toSerialized();
        }

        // Subscribe the source Observable.
        sourceDisposable = source.subscribeWith(new DisposableObserver<T>() {
            @Override
            public void onNext(T value) {
                set(value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                valueEmitter.onError(e);
                RxProperty.this.dispose();
            }

            @Override
            public void onComplete() {
                valueEmitter.onComplete();
                RxProperty.this.dispose();
            }
        });

        // Register RxJava plugins.
        RxJavaPlugins.onAssembly(this);
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        valueEmitter.subscribe(observer);
    }

    /**
     * Gets the latest value of this {@code RxProperty}.
     *
     * @return the latest value stored in this {@code RxProperty}
     */
    @Nullable
    public T get() {
        return valueField.get();
    }

    /**
     * Sets the specified value to this {@code RxProperty}. The change will be notified to both
     * bound view and observers of this {@code RxProperty}.
     *
     * @param value a value to set
     */
    public void set(@NonNull T value) {
        set(value, true);
    }

    /**
     * Sets the specified value to this {@code RxProperty}. The change will be notified to
     * observers of this {@code RxProperty} but not affect the bound view.
     *
     * @param value a value to set
     */
    public void setWithoutViewUpdate(T value) {
        set(value, false);
    }

    private void set(T value, boolean viewUpdate) {
        if (isDisposed()) {
            return;
        }
        if (isDistinctUntilChanged && compare(value, get())) {
            return;
        }
        valueField.set(value, viewUpdate);
    }

    @Override
    public void dispose() {
        if (isDisposed.compareAndSet(false, true)) {
            // Terminate internal subjects.
            RxHelperKt.safeComplete(valueEmitter);

            // Dispose internal disposables.
            RxHelperKt.safeDispose(sourceDisposable);

            // Unbind a view observer.
            RxHelperKt.safeCancel(cancellable);
            cancellable = null;
        }
    }

    @Override
    public boolean isDisposed() {
        return isDisposed.get();
    }

    /**
     * Forcibly notifies the latest value of this {@code RxProperty} to all observers including the
     * bound view. This method ignores {@link #DISTINCT_UNTIL_CHANGED}.
     */
    public void forceNotify() {
        valueField.set(get(), true);
    }

    /**
     * @deprecated This is a magic method for Data Binding. Don't call it in your code. To get the
     * latest value of this property, use {@link RxProperty#get()} instead of this method.
     */
    @Deprecated
    public ObservableField<T> getValue() {
        return valueField;
    }

    /**
     * @deprecated This is a magic method for Data Binding. Don't call it in your code except
     * in {@link BindingAdapter} implementation.
     */
    @Deprecated
    public void setCancellable(@Nullable Cancellable cancellable) {
        RxHelperKt.safeCancel(this.cancellable);
        this.cancellable = cancellable;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        valueField.addOnPropertyChangedCallback(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        valueField.removeOnPropertyChangedCallback(callback);
    }

    /**
     * Reimplementation of {@link ObservableField} for collaborating with {@link RxProperty}.
     *
     * @param <T> a type of value stored in this property.
     */
    private static class RxPropertyField<T> extends ObservableField<T> {
        private final RxProperty<T> parent;
        private T value;

        RxPropertyField(final RxProperty<T> parent, T initialValue) {
            this.parent = parent;
            this.value = initialValue;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public void set(T value) {
            set(value, false);
        }

        void set(T value, boolean viewUpdate) {
            this.value = value;
            if (viewUpdate) {
                notifyChange();
            }
            parent.valueEmitter.onNext(value);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(flag = true, value = {NONE, DISTINCT_UNTIL_CHANGED, RAISE_LATEST_VALUE_ON_SUBSCRIBE})
    public @interface MODE {

    }

    private static <T> boolean compare(T value1, T value2) {
        return (value1 == null && value2 == null) || (value1 != null && value1.equals(value2));
    }
}
