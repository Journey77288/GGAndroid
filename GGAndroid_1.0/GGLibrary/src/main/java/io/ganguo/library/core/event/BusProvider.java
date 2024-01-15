package io.ganguo.library.core.event;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more efficient means
 * such as through injection directly into interested classes.
 * <p/>
 * https://github.com/square/otto/blob/master/otto-sample/src/main/java/com/squareup/otto/sample/BusProvider.java
 * <p/>
 * Created by Tony on 4/28/15.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.MAIN);

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}