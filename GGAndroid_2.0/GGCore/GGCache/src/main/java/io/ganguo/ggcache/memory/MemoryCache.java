package io.ganguo.ggcache.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import io.ganguo.ggcache.CacheRecord;
import io.ganguo.ggcache.ICache;

/**
 * in-memory cache
 * Created by Lynn on 2016/12/15.
 */

public class MemoryCache implements ICache {

    private MemoryCachePool mPool = null;

    public MemoryCache(final MemoryOption option) {
        if (option == null) {
            throw new IllegalArgumentException("Memory Option is null");
        }

        mPool = new MemoryCachePool(option);
    }

    @Override
    public void put(@NonNull String key, String value) {
        put(key, value, false, 0L);
    }

    @Override
    public void put(@NonNull String key, String value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    @Override
    public void put(@NonNull String key, byte[] value) {
        put(key, value, false, 0L);
    }

    @Override
    public void put(@NonNull String key, byte[] value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    @Override
    public void put(@NonNull String key, Bitmap value) {
        put(key, value, false, 0L);
    }

    @Override
    public void put(@NonNull String key, Drawable value) {
        put(key, value, false, 0L);
    }

    @Override
    public void put(@NonNull String key, Bitmap value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    @Override
    public void put(@NonNull String key, Drawable value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    @Override
    public <T> void put(@NonNull String key, T value) {
        put(key, value, false, 0L);
    }

    @Override
    public <T> void put(@NonNull String key, T value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    public <T> void put(@NonNull String key, T value, boolean expirable, long expiredTime) {
        final CacheRecord<T> record = new CacheRecord<>(value, expirable, expiredTime + System.currentTimeMillis());
        mPool.put(key, record);
    }

    @Override
    public String getString(@NonNull String key) {
        return get(key);
    }

    @Override
    public byte[] getByteArray(@NonNull String key) {
        return get(key);
    }

    @Override
    public Bitmap getBitmap(@NonNull String key) {
        return get(key);
    }

    @Override
    public Drawable getDrawable(@NonNull String key) {
        return get(key);
    }

    /**
     * 保留统一接口,
     * 以及兼容 Two-Layer Cache
     */
    @Override
    public <T> T get(@NonNull String key, Class<T> clazz, Type... parameterizedClazzs) {
        return get(key);
    }

    public <T> T get(@NonNull String key) {
        final CacheRecord<T> cachedRecord = mPool.get(key);
        if (cachedRecord == null) {
            //cache not exists
            return null;
        }

        if (cachedRecord.isExpirable() && cachedRecord.isExpired()) {
            //expired, remove
            mPool.remove(key);
            return null;
        }

        return cachedRecord.getData();
    }

    @Override
    public void remove(@NonNull String key) {
        mPool.remove(key);
    }

    @Override
    public void clear() {
        mPool.clear();
    }

    @Override
    public void close() {
        mPool.close();
    }
}
