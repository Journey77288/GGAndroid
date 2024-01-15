package io.ganguo.rx;



import androidx.databinding.ObservableBoolean;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * Command class implementation in "Command pattern" for Android Data Binding. Whether {@code
 * RxCommand} can execute is detected by boolean {@link Observable}.
 */
public class RxCommand<T> implements Disposable {
    private final ObservableBoolean canExecuteFlag;
    private final T listener;
    private Disposable disposable = null;
    private final Subject<Throwable> errorNotifier = PublishSubject.create();

    /**
     * Creates {@code RxCommand} which is always enabled.
     */
    public RxCommand() {
        this((Observable<Boolean>) null);
    }

    /**
     * Creates {@code RxCommand} from the specified {@link Observable}.
     *
     * @param source an {@link Observable} representing that can this {@code RxCommand} execute
     */
    public RxCommand(Observable<Boolean> source) {
        this(source, true);
    }

    /**
     * Creates {@code RxCommand} from the specified {@link Observable} with the initial state.
     *
     * @param source       an {@link Observable} to emit whether this {@code RxCommand} can execute
     * @param initialValue whether this {@code RxCommand} is enabled at first
     */
    public RxCommand(Observable<Boolean> source, boolean initialValue) {
        this(source, initialValue, null);
    }

    /**
     * Creates {@code RxCommand} to execute the specified command.
     *
     * @param command a callback called when this {@code RxCommand} is executed
     */
    public RxCommand(T command) {
        this(null, command);
    }

    /**
     * Creates {@code RxCommand} to execute the specified command from the specified {@link
     * Observable}.
     *
     * @param source  an {@link Observable} to emit whether this {@code RxCommand} can execute
     * @param command a callback called when this {@code RxCommand} is executed
     */
    public RxCommand(Observable<Boolean> source, T command) {
        this(source, true, command);
    }

    /**
     * Creates {@code RxCommand} to execute the specified command from the specified {@link
     * Observable} with the initial state.
     *
     * @param source       an {@link Observable} to emit whether this {@code RxCommand} can execute
     * @param initialValue whether this {@code RxCommand} is enabled at first
     * @param command      a callback called when this {@code RxCommand} is executed
     */
    public RxCommand(Observable<Boolean> source, boolean initialValue, T command) {
        canExecuteFlag = new ObservableBoolean(initialValue);
        listener = command;
        if (source == null) {
            disposable = null;
        } else {
            disposable = source.distinctUntilChanged()
                    .subscribeWith(new DisposableObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean value) {
                            canExecuteFlag.set(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            errorNotifier.onNext(e);
                        }

                        @Override
                        public void onComplete() {
                            errorNotifier.onComplete();
                        }
                    });
        }
    }

    /**
     * Gets whether this {@code RxCommand} can execute currently.
     *
     * @return {@code true} if this {@code RxCommand} can execute, {@code false} otherwise
     */
    public boolean canExecute() {
        return canExecuteFlag.get();
    }

    /**
     * Gets a hot {@link Observable} to emit errors propagated by the source {@link Observable}.
     *
     * @return a hot {@link Observable} to emit errors propagated by the source {@link Observable}
     */
    public Observable<Throwable> observeErrors() {
        return errorNotifier.hide();
    }

    /**
     * Stops receiving notifications by the source {@link Observable} and sending notifications to
     * error observers of this {@code RxCommand}.
     */
    @Override
    public void dispose() {
        if (!isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * Indicates whether this {@code RxCommand} is currently unsubscribed.
     *
     * @return {@code true} if this {@code RxCommand} has no {@link Observable} as source or is
     * currently unsubscribed, {@code false} otherwise
     */
    @Override
    public boolean isDisposed() {
        return disposable != null && disposable.isDisposed();
    }

    /**
     * @deprecated This is a magic method for Data Binding. Don't call it in your code.
     */
    @Deprecated
    public T getExec() {
        return listener;
    }

    /**
     * @deprecated This is a magic method for Data Binding. Don't call it in your code.
     */
    @Deprecated
    public void setExec(T value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @deprecated This is a magic method for Data Binding. Don't call it in your code.
     */
    @Deprecated
    public ObservableBoolean getEnabled() {
        return canExecuteFlag;
    }

    /**
     * @deprecated This is a magic method for Data Binding. Don't call it in your code.
     */
    @Deprecated
    public void setEnabled(ObservableBoolean value) {
        throw new UnsupportedOperationException();
    }

}
