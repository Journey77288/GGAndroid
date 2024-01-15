package io.ganguo.rx.bus;

import android.support.v4.util.Pair;

/**
 * rxbus event
 * Created by Lynn on 9/1/16.
 */

public class RxEvent extends Pair<String, Object> {
    public RxEvent(final String tag, final Object obj) {
        super(tag, obj);
    }
}
