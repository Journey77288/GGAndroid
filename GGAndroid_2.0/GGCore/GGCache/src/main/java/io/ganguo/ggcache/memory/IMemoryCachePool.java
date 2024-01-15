package io.ganguo.ggcache.memory;

import android.support.annotation.NonNull;

import io.ganguo.ggcache.CacheRecord;
import io.ganguo.ggcache.ICachePool;

/**
 * memory cache pool interface
 * Created by Lynn on 2016/12/21.
 */

public interface IMemoryCachePool extends ICachePool {
    <T> CacheRecord<T> get(@NonNull String key);

    <T> void put(@NonNull String key, CacheRecord<T> record);
}
