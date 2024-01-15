package io.ganguo.ggcache.memory;

import android.support.annotation.NonNull;
import android.util.LruCache;

import io.ganguo.ggcache.CacheRecord;

/**
 * memory cache pool
 * thread safe
 * Created by Lynn on 2016/12/12.
 */

final class MemoryCachePool implements IMemoryCachePool {
    //thread safe data structure
    private LruCache<String, CacheRecord> mCacheActor;
    private final MemoryOption mOption;

    MemoryCachePool(@NonNull MemoryOption option) {
        mOption = option;
    }

    synchronized LruCache<String, CacheRecord> getCacheActor() {
        if (mCacheActor == null) {
            mCacheActor = new LruCache<>(mOption.getMaxSize());
        }
        return mCacheActor;
    }

    @Override
    public <T> CacheRecord<T> get(@NonNull String key) {
        final CacheRecord record = getCacheActor().get(key);

        if (record == null) {
            return null;
        }

        return record;
    }

    @Override
    public <T> void put(@NonNull String key, CacheRecord<T> record) {
        getCacheActor().put(key, record);
    }

    @Override
    public void remove(@NonNull String key) {
        getCacheActor().remove(key);
    }

    @Override
    public void clear() {
        getCacheActor().evictAll();
    }

    @Override
    public void close() {
        clear();
        mCacheActor = null;
    }
}
