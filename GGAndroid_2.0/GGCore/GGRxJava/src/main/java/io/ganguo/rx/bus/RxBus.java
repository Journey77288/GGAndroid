package io.ganguo.rx.bus;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * RxBus impl
 * Created by Lynn on 9/1/16.
 */

public final class RxBus {
    private static volatile RxBus INSTANCE = null;
    // thread safe
    private final Subject<Object> mBus = PublishSubject.create().toSerialized();
    // 为了实现sticky event, 即subscribe event时, 可以接收到订阅前的latest event
    // (利用 concurrent hashMap, 提升多线程下的特性)
    private final Map<String, Object> mStickyEventContainers = new ConcurrentHashMap<>();

    public static RxBus getDefault() {
        if (INSTANCE == null) {
            synchronized (RxBus.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RxBus();
                    return INSTANCE;
                }
            }
        }
        return INSTANCE;
    }

    @Deprecated
    private void send(Object o) {
        mBus.onNext(o);
    }

    @Deprecated
    private Observable<Object> observable() {
        return mBus;
    }

    @Deprecated
    private <T> Observable<T> toObservable(final Class<T> type) {
        return mBus.filter(new Predicate<Object>() {
            @Override
            public boolean test(Object o) throws Exception {
                return type.isInstance(o);
            }
        }).cast(type);
    }

    /**
     * 发送事件, 依靠tag区分event type, 自动包装
     */
    public void send(final Object o, @NonNull final String signal) {
        mBus.onNext(new RxEvent(signal, o));
    }

    /**
     * 发送Sticky Event
     * <p>
     * ps.使用Sticky时，如果receive事件和send事件同时调用，必须确保receive的subScribeOn线程跟Send的线程要一致
     * 不然会出现注册之后无法接受到Sticky事件的问题,或者让send事件放在receive之前
     * (主要是因为判断map方法跟SubscribeOn的线程顺序可能不是map之后再执行subscribe)
     */
    public void sendSticky(final Object o, @NonNull final String signal) {
        // 通过signal(event type)绑定最新事件携带的obj,
        // 以后每次调用sendSticky都会更新
        mStickyEventContainers.put(signal, o);
        // 正常发送事件
        send(o, signal);
    }

    /**
     * receive event
     *
     * @param type
     * @param signal
     * @return Observable<T>
     * <p>
     * 接收事件调用该方法
     * 转成 Observable, 只暴露receive event的data flow
     * type - 指定对应的event下, 过滤接收obj的类型
     * signal - 实际上是定义一个String来区分event type, 可以大大减少创建不同的event
     * TODO 后面可以增加Collections interface类别对象的进一步类型检查
     */
    @SuppressWarnings("unchecked")
    public <T> Observable<T> receiveEvent(@NonNull final Class<T> type, @NonNull final String signal) {
        return mBus.filter(new Predicate<Object>() {
            @Override
            public boolean test(final Object o) {
                if (!RxEvent.class.isInstance(o)) {
                    //TODO 检查如果不是RxEvent实例, 则过滤, 一般使用过程会自动包装, 不需要新建RxEvent
                    return false;
                }
                final RxEvent event = (RxEvent) o;
                // 判断event携带的obj类型是否匹配希望获取的对象类型
                // 以及判断根据signal过滤不同的event
                return type.isInstance(event.second) && signal.equals(event.first);
            }
        }).map(new Function<Object, T>() {
            @Override
            public T apply(Object o) {
                // 前面已经成功筛选了event
                final RxEvent event = (RxEvent) o;
                return (T) event.second;
            }
        });
    }

    /**
     * 接收Sticky Event
     *
     * @param type
     * @param signal
     * @param <T>
     * @return Observable<T>
     * <p>
     * ps.使用Sticky时，如果receive事件和send事件同时调用，必须确保receive的subScribeOn线程跟Send的线程要一致
     * 不然会出现注册之后无法接受到Sticky事件的问题,或者让send事件放在receive之前
     * (主要是因为判断map方法跟SubscribeOn的线程顺序可能不是map之后再执行subscribe)
     */
    @SuppressWarnings("unchecked")
    public <T> Observable<T> receiveStickyEvent(@NonNull final Class<T> type, @NonNull final String signal) {
        // commmon receiver
        final Observable<T> receiver = receiveEvent(type, signal);
        final Object o = mStickyEventContainers.get(signal);
        // 如果有保存的sticky event, 则key能获取到obj
        if (o != null && type.isInstance(o)) {
            // 发送出去该obj
            return receiver.mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                    try {
                        // 相当于绑定了一个cold event，
                        // 只要有新的订阅希望接收这个类型的sticky event就会触发,
                        // 而另外一个hot event(即正常receiver)不会受到影响
                        emitter.onNext(type.cast(o));
                    } catch (final Exception e) {
                        emitter.onError(e);
                    } finally {
                        emitter.onComplete();
                    }
                }
            }));
        } else {
            // 否则直接返回普通的receiver
            return receiver;
        }
    }

    /**
     * 删除某个类型的sticky event
     *
     * @param type
     * @return
     */
    public boolean removeStickyEvent(@NonNull String type) {
        return mStickyEventContainers.remove(type) != null;
    }

    /**
     * 删除所有的sticky event,
     * 一般用于 app exit / main activity destroy
     */
    public void removeAllStickyEvents() {
        mStickyEventContainers.clear();
    }

    /**
     * TODO 注意, 该方法在activity / fragment / viewmodel 对应生命周期的onDestroy调用
     * TODO 为了取消订阅, 以防event 捆绑或者引用了 view / context 等情况, 造成memory leak
     */
    public static void dispose(Disposable... disposables) {
        for (Disposable disposable : safeDispose(disposables)) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    /**
     * 如果传入的subscription 为空, 则默认返回一个空列表,
     * for each里面的block不会执行
     */
    @SuppressWarnings("unchecked")
    private static List<Disposable> safeDispose(Disposable... disposables) {
        return disposables == null ? (List<Disposable>) Collections.EMPTY_LIST : Arrays.asList(disposables);
    }
}
