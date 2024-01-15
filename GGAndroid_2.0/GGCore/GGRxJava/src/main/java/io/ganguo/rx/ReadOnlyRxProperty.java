package io.ganguo.rx;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicBoolean;

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

/**
 * One-way bindable and observable property for Android Data Binding.
 */
public class ReadOnlyRxProperty<T> extends Observable<T> implements android.databinding.Observable, Disposable {

  /**
   * Mode of the {@link ReadOnlyRxProperty}.
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
  private final ReadOnlyRxPropertyField<T> valueField;
  @NonNull
  private final Subject<T> valueEmitter;
  @NonNull
  private final Disposable sourceDisposable;
  @NonNull
  private final AtomicBoolean isDisposed = new AtomicBoolean(false);
  @Nullable
  private Cancellable cancellable = null;

  /**
   * Creates {@code ReadOnlyRxProperty} without an initial value.
   */
  public ReadOnlyRxProperty() {
    this(Observable.<T>never(), Maybe.<T>empty(), DEFAULT_MODE);
  }

  public ReadOnlyRxProperty(@NonNull T defaultValue) {
    this(Observable.<T>never(), Maybe.just(defaultValue), DEFAULT_MODE);
  }

  public ReadOnlyRxProperty(@NonNull Observable<T> source) {
    this(source, Maybe.<T>empty(), DEFAULT_MODE);
  }

  public ReadOnlyRxProperty(@NonNull Provider<T> provider) {
    this(Provider.ProviderHelper.<T>getSource(provider),
            Provider.ProviderHelper.<T>getInitialMaybe(provider),
            Provider.ProviderHelper.<T>getMode(provider));
  }

  /**
   * Creates {@code ReadOnlyRxProperty} from the specified {@link Observable} with the initial value and the
   * specified mode.
   *
   * @param source       a source {@link Observable} of this {@code ReadOnlyRxProperty}
   * @param initialMaybe the initial value of this {@code ReadOnlyRxProperty}
   * @param mode         mode of this {@code ReadOnlyRxProperty}
   */
  protected ReadOnlyRxProperty(@NonNull Observable<T> source, @NonNull Maybe<T> initialMaybe,
                               @MODE int mode) {
    RxHelper.checkNull(source, "source must not null");

    T initialValue = initialMaybe.blockingGet();
    valueField = new ReadOnlyRxPropertyField<>(this, initialValue);

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
        ReadOnlyRxProperty.this.dispose();
      }

      @Override
      public void onComplete() {
        valueEmitter.onComplete();
        ReadOnlyRxProperty.this.dispose();
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
   * Gets the latest value of this {@code ReadOnlyRxProperty}.
   *
   * @return the latest value stored in this {@code ReadOnlyRxProperty}
   */
  @Nullable
  public T get() {
    return valueField.get();
  }

  /**
   * Sets the specified value to this {@code ReadOnlyRxProperty}. The change will be notified to both
   * bound view and observers of this {@code ReadOnlyRxProperty}.
   *
   * @param value a value to set
   */
  private void set(@NonNull T value) {
    if (isDisposed()) {
      return;
    }
    if (isDistinctUntilChanged && compare(value, get())) {
      return;
    }
    valueField.setValue(value);
  }

  @Override
  public void dispose() {
    if (isDisposed.compareAndSet(false, true)) {
      // Terminate internal subjects.
      RxHelper.safeComplete(valueEmitter);

      // Dispose internal disposables.
      RxHelper.safeDispose(sourceDisposable);

      // Unbind a view observer.
      RxHelper.safeCancel(cancellable);
      cancellable = null;
    }
  }

  @Override
  public boolean isDisposed() {
    return isDisposed.get();
  }

  /**
   * Forcibly notifies the latest value of this {@code ReadOnlyRxProperty} to all observers including the
   * bound view. This method ignores {@link #DISTINCT_UNTIL_CHANGED}.
   */
  public void forceNotify() {
    valueField.setValue(get());
  }

  /**
   * @deprecated This is a magic method for Data Binding. Don't call it in your code. To get the
   * latest value of this property, use {@link ReadOnlyRxProperty#get()} instead of this method.
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
    RxHelper.safeCancel(this.cancellable);
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
   * Reimplementation of {@link ObservableField} for collaborating with {@link ReadOnlyRxProperty}.
   *
   * @param <T> a type of value stored in this property.
   */
  private static class ReadOnlyRxPropertyField<T> extends ObservableField<T> {
    private final ReadOnlyRxProperty<T> parent;
    private T value;

    ReadOnlyRxPropertyField(final ReadOnlyRxProperty<T> parent, T initialValue) {
      this.parent = parent;
      this.value = initialValue;
    }

    @Override
    public T get() {
      return value;
    }

    @Override
    public void set(T value) {
      throw new UnsupportedOperationException(
              "ReadOnlyRxProperty doesn't support two-way binding.");
    }

    void setValue(T value) {
      this.value = value;
      parent.valueEmitter.onNext(value);
      notifyChange();
    }
  }

  private static <T> boolean compare(T value1, T value2) {
    return (value1 == null && value2 == null) || (value1 != null && value1.equals(value2));
  }

  @Retention(RetentionPolicy.SOURCE)
  @IntDef(flag = true, value = {NONE, DISTINCT_UNTIL_CHANGED, RAISE_LATEST_VALUE_ON_SUBSCRIBE})
  public @interface MODE {

  }

}