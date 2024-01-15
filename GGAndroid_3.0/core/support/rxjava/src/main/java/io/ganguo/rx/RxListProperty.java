package io.ganguo.rx;


import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Roger on 05/01/2017.
 */

public class RxListProperty<E> extends RxProperty<List<E>> {
    public RxListProperty() {
        super();
    }

    public RxListProperty(@NonNull List<E> defaultValue) {
        super(defaultValue);
    }

    public RxListProperty(@NonNull Observable<List<E>> source) {
        super(source);
    }

    public RxListProperty(@NonNull Provider<List<E>> provider) {
        super(provider);
    }

    private RxListProperty(@NonNull Observable<List<E>> source, @NonNull Maybe<List<E>> initialMaybe, @MODE int mode) {
        super(source, initialMaybe, mode);
    }

    public int size() {
        final List<E> list = get();
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public boolean add(E e) {
        final List<E> list = get();
        if (list != null) {
            list.add(e);
            forceNotify();
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean remove(Object o) {
        final List<E> list = get();
        if (list != null) {
            if (list.remove(o)) {
                forceNotify();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean addAll(@NonNull Collection<? extends E> c) {
        final List<E> list = get();
        if (list != null) {
            list.addAll(c);
            forceNotify();
            return true;
        } else {
            return false;
        }
    }

    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        final List<E> list = get();
        if (list != null) {
            list.addAll(index, c);
            forceNotify();
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean removeAll(@NonNull Collection<?> c) {
        final List<E> list = get();
        if (list != null) {
            if (list.removeAll(c)) {
                forceNotify();
            }
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        final List<E> list = get();
        if (list != null) {
            list.clear();
            forceNotify();
        }
    }

    public E set(int index, E element) {
        final List<E> list = get();
        if (list != null) {
            E result = list.set(index, element);
            forceNotify();
            return result;
        } else {
            return null;
        }
    }

    public void add(int index, E element) {
        final List<E> list = get();
        if (list != null) {
            list.add(index, element);
            forceNotify();
        }
    }

    public E remove(int index) {
        final List<E> list = get();
        if (list != null) {
            E result = list.remove(index);
            forceNotify();
            return result;
        } else {
            return null;
        }
    }

    public int indexOf(E item) {
        final List<E> list = get();
        if (list != null) {
            return list.indexOf(item);
        }
        return -1;
    }

    public boolean containsObject(Object obj) {
        final List<E> list = get();
        if (list != null) {
            return list.contains(obj);
        }
        return false;
    }

    public boolean containsAll(Collection<?> collection) {
        final List<E> list = get();
        if (list != null) {
            return list.containsAll(collection);
        }
        return false;
    }

}
