package io.ganguo.ggcache;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * base cache pool exposed interface
 * Created by Lynn on 2016/12/12.
 */

public interface ICachePool {
    void remove(@NonNull String key);

    void clear();

    void close();
}
