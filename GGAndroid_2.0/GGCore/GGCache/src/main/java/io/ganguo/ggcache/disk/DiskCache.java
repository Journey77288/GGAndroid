package io.ganguo.ggcache.disk;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import io.ganguo.ggcache.CacheRecord;
import io.ganguo.ggcache.GsonParser;
import io.ganguo.ggcache.ICache;
import io.ganguo.ggcache.IJsonParser;

/**
 * 持久化存储 disk cache
 * Created by Lynn on 2016/12/14.
 */

public class DiskCache implements ICache {
    private static final String BYTE_ARRAY_KEY_SUFFIX = "_value";

    private IJsonParser mJsonParser = null;
    private DiskCachePool mPool = null;

    public DiskCache(final DiskOption option) {
        if (option == null) {
            throw new IllegalArgumentException("Disk Option is null");
        }

        mJsonParser = new GsonParser();
        mPool = new DiskCachePool(option);
    }

    @Override
    public void put(@NonNull String key, String value) {
        put(key, value, false, 0L);
    }

    @Override
    public void put(@NonNull String key, String value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    /**
     * string -> CacheRecord<String> -> json string
     */
    private void put(@NonNull String key, String value, boolean expirable, long expiredTime) {
        //相对时间间隔
        final CacheRecord<String> cacheRecord = new CacheRecord<>(value, expirable,
                System.currentTimeMillis() + expiredTime);
        final Type type = mJsonParser.newParameterizedType(CacheRecord.class, String.class);
        mPool.put(key, mJsonParser.toJson(cacheRecord, type));
    }

    @Override
    public <T> void put(@NonNull String key, T value) {
        put(key, value, false, 0L);
    }

    @Override
    public <T> void put(@NonNull String key, T value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    /**
     * object -> CacheRecord<T> -> json string
     */
    private <T> void put(@NonNull String key, T value, boolean expirable, long expiredTime) {
        final CacheRecord<T> cacheRecord = new CacheRecord<>(value, expirable,
                System.currentTimeMillis() + expiredTime);
        final Type type = mJsonParser.newParameterizedType(CacheRecord.class, value.getClass());
        mPool.put(key, mJsonParser.toJson(cacheRecord, type));
    }

    @Override
    public void put(@NonNull String key, byte[] value) {
        put(key, value, false, 0L);
    }

    @Override
    public void put(@NonNull String key, byte[] value, long expiredTime) {
        put(key, value, true, expiredTime);
    }

    /**
     * value 对应实际的 key 添加 "_value" 的 suffix
     * - key: CacheRecord<String> -> json
     * - value: byte[]
     */
    private void put(@NonNull String key, byte[] value, boolean expirable, long expiredTime) {
        final String valueKey = key + BYTE_ARRAY_KEY_SUFFIX;
        //cache record
        put(key, valueKey, expirable, expiredTime);
        //actual value
        mPool.put(valueKey, value);
    }

    /**
     * Bitmap -> byte[]
     */
    @Override
    public void put(@NonNull String key, Bitmap value) {
        put(key, DiskUtils.bitmap2ByteArray(value));
    }

    /**
     * Drawable -> Bitmap -> byte[]
     */
    @Override
    public void put(@NonNull String key, Drawable value) {
        put(key, DiskUtils.drawable2Bitmap(value));
    }

    @Override
    public void put(@NonNull String key, Bitmap value, long expiredTime) {
        put(key, DiskUtils.bitmap2ByteArray(value), expiredTime);
    }

    @Override
    public void put(@NonNull String key, Drawable value, long expiredTime) {
        put(key, DiskUtils.drawable2Bitmap(value), expiredTime);
    }

    @Override
    public String getString(@NonNull String key) {
        final Type type = mJsonParser.newParameterizedType(CacheRecord.class, String.class);
        final String cachedRecord = mPool.getString(key);

        if (cachedRecord == null) {
            //cache not exists
            return null;
        }

        final CacheRecord<String> cacheRecord = mJsonParser.fromJson(cachedRecord, type);
        if (cacheRecord.isExpirable() && cacheRecord.isExpired()) {
            //expired, remove cache
            mPool.remove(key);
            return null;
        }
        return cacheRecord.getData();
    }

    @Override
    public byte[] getByteArray(@NonNull String key) {
        final Type type = mJsonParser.newParameterizedType(CacheRecord.class, String.class);
        final String cachedRecord = mPool.getString(key);
        if (cachedRecord == null) {
            //cache not exists
            return null;
        }

        final CacheRecord<String> valueKeyRecord = mJsonParser.fromJson(cachedRecord, type);
        if (valueKeyRecord.isExpirable() && valueKeyRecord.isExpired()) {
            //expired, remove cache
            mPool.remove(key);
            mPool.remove(valueKeyRecord.getData());
            return null;
        }
        return mPool.getByteArray(valueKeyRecord.getData());
    }

    @Override
    public Bitmap getBitmap(@NonNull String key) {
        final byte[] res = getByteArray(key);
        if (res == null || res.length <= 0) {
            return null;
        }
        return DiskUtils.byteArray2Bitmap(res);
    }

    @Override
    public Drawable getDrawable(@NonNull String key) {
        final Bitmap res = getBitmap(key);
        if (res == null) {
            return null;
        }
        return DiskUtils.bitmap2Drawable(res);
    }

    /**
     * @param key                 cache 对应的key
     * @param rawClazz            cache item type 实际类型
     * @param parameterizedClazzs 涉及到范型, 对应的 arguments class
     */
    @Override
    public <T> T get(@NonNull String key, Class<T> rawClazz, Type... parameterizedClazzs) {
        final String cachedRecord = mPool.getString(key);
        if (cachedRecord == null) {
            //cache not exists
            return null;
        }

        final Type type;
        if (parameterizedClazzs != null && parameterizedClazzs.length > 0) {
            //generic type
            final Type parameterizedTypes = mJsonParser.newParameterizedType(rawClazz, parameterizedClazzs);
            type = mJsonParser.newParameterizedType(CacheRecord.class, parameterizedTypes);
        } else {
            //common object
            type = mJsonParser.newParameterizedType(CacheRecord.class, rawClazz);
        }

        final CacheRecord<T> valueRecord = mJsonParser.fromJson(cachedRecord, type);
        if (valueRecord.isExpirable() && valueRecord.isExpired()) {
            //expired, remove cache
            mPool.remove(key);
            return null;
        }
        return valueRecord.getData();
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
