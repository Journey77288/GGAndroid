package io.ganguo.ggcache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.lang.reflect.Type;

/**
 * 对外暴露的 cache api
 * Created by Lynn on 2016/12/15.
 */

public interface ICache {
    void put(@NonNull String key, String value);

    void put(@NonNull String key, String value, long expiredTime);

    <T> void put(@NonNull String key, T value);

    <T> void put(@NonNull String key, T value, long expiredTime);

    void put(@NonNull String key, byte[] value);

    void put(@NonNull String key, byte[] value, long expiredTime);

    void put(@NonNull String key, Bitmap value);

    void put(@NonNull String key, Bitmap value, long expiredTime);

    void put(@NonNull String key, Drawable value);

    void put(@NonNull String key, Drawable value, long expiredTime);

    String getString(@NonNull String key);

    byte[] getByteArray(@NonNull String key);

    Bitmap getBitmap(@NonNull String key);

    Drawable getDrawable(@NonNull String key);

    <T> T get(@NonNull String key, Class<T> rawClazz, Type... parameterizedClazzs);

    void remove(@NonNull String key);

    void clear();

    void close();
}
