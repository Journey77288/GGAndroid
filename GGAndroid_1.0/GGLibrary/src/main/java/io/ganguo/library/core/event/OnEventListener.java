package io.ganguo.library.core.event;

/**
 * EventListener
 * <p/>
 * Created by tony on 8/28/14.
 */
public interface OnEventListener<T> {
    public void onEvent(T event);
}
