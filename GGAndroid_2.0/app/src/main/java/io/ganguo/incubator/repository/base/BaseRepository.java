package io.ganguo.incubator.repository.base;

import io.ganguo.rx.RxProperty;
import io.reactivex.Observable;

/**
 * 存放常用的数据
 * Created by hulkyao on 5/6/2017.
 */

public abstract class BaseRepository<T> {
    protected RxProperty<T> data = new RxProperty<>();

    protected BaseRepository() {
        init();
    }

    public T data() {
        if (data.get() == null) {
            loadCache();
        }
        return data.get();
    }

    public Observable<T> asObservable() {
        if (data.get() == null) {
            loadCache();
        }
        return data;
    }

    public void init() {
        loadCache();
    }

    public void destroy() {
        if (data != null) {
            data.dispose();
        }
    }

    protected abstract void loadCache();

    public abstract void clearCache();

    public abstract void updateFromService();
}
