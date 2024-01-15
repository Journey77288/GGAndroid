package io.ganguo.ggcache.disk;

import android.support.annotation.NonNull;

import io.ganguo.ggcache.ICachePool;

/**
 * disk cache pool interface
 * Created by Lynn on 2016/12/21.
 */

public interface IDiskCachePool extends ICachePool {

    void put(@NonNull String key, String value);

    void put(@NonNull String key, byte[] value);

    String getString(@NonNull String key);

    byte[] getByteArray(@NonNull String key);
}
