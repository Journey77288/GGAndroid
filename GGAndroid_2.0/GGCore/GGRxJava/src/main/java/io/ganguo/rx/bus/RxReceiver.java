package io.ganguo.rx.bus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lynn on 9/1/16.
 */

public abstract class RxReceiver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {
        _complete();
    }

    @Override
    public void onError(Throwable e) {
        _error(e);
    }

    @Override
    public void onNext(T t) {
        onReceive(t);
    }

    private void _complete() {

    }

    private void _error(Throwable e) {
        e.printStackTrace();
    }

    protected abstract void onReceive(T t);
}
