package io.ganguo.ggcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;

import io.ganguo.ggcache.disk.DiskCache;
import io.ganguo.ggcache.disk.DiskOption;
import io.ganguo.ggcache.memory.MemoryCache;
import io.ganguo.ggcache.memory.MemoryOption;

/**
 * memory - disk two level cache
 * Created by Lynn on 2016/12/23.
 */

public final class TwoLayersCache implements ICache {
    private final boolean mEnableDisk;
    private final boolean mEnableMemory;

    private final DiskCache mDisk;
    private final MemoryCache mMemory;

    public TwoLayersCache(Context context) {
        this(DiskOption.defaultOption(context), MemoryOption.defaultOption());
    }

    public TwoLayersCache(DiskOption diskOption, MemoryOption memoryOption) {
        this(diskOption != null ? new DiskCache(diskOption) : null,
                memoryOption != null ? new MemoryCache(memoryOption) : null);
    }

    public TwoLayersCache(DiskOption diskOption, MemoryOption memoryOption, boolean enableDisk, boolean enableMemory) {
        this(enableDisk ? new DiskCache(diskOption) : null,
                enableMemory ? new MemoryCache(memoryOption) : null);
    }

    public TwoLayersCache(DiskCache diskCache, MemoryCache memoryCache) {
        this.mDisk = diskCache;
        this.mMemory = memoryCache;

        mEnableDisk = (diskCache != null);
        mEnableMemory = (memoryCache != null);
    }

    @Override
    public void put(@NonNull String key, String value) {
        if (mEnableMemory) {
            mMemory.put(key, value);
        }

        if (mEnableDisk) {
            mDisk.put(key, value);
        }
    }

    @Override
    public void put(@NonNull String key, String value, long expiredTime) {
        if (mEnableMemory) {
            mMemory.put(key, value, expiredTime);
        }

        if (mEnableDisk) {
            mDisk.put(key, value, expiredTime);
        }
    }

    @Override
    public <T> void put(@NonNull String key, T value) {
        if (mEnableMemory) {
            mMemory.put(key, value);
        }

        if (mEnableDisk) {
            mDisk.put(key, value);
        }
    }

    @Override
    public <T> void put(@NonNull String key, T value, long expiredTime) {
        if (mEnableMemory) {
            mMemory.put(key, value, expiredTime);
        }

        if (mEnableDisk) {
            mDisk.put(key, value, expiredTime);
        }
    }

    @Override
    public void put(@NonNull String key, byte[] value) {
        if (mEnableMemory) {
            mMemory.put(key, value);
        }

        if (mEnableDisk) {
            mDisk.put(key, value);
        }
    }

    @Override
    public void put(@NonNull String key, byte[] value, long expiredTime) {
        if (mEnableMemory) {
            mMemory.put(key, value, expiredTime);
        }

        if (mEnableDisk) {
            mDisk.put(key, value, expiredTime);
        }
    }

    @Override
    public void put(@NonNull String key, Bitmap value) {
        if (mEnableMemory) {
            mMemory.put(key, value);
        }

        if (mEnableDisk) {
            mDisk.put(key, value);
        }
    }

    @Override
    public void put(@NonNull String key, Bitmap value, long expiredTime) {
        if (mEnableMemory) {
            mMemory.put(key, value, expiredTime);
        }

        if (mEnableDisk) {
            mDisk.put(key, value, expiredTime);
        }
    }

    @Override
    public void put(@NonNull String key, Drawable value) {
        if (mEnableMemory) {
            mMemory.put(key, value);
        }

        if (mEnableDisk) {
            mDisk.put(key, value);
        }
    }

    @Override
    public void put(@NonNull String key, Drawable value, long expiredTime) {
        if (mEnableMemory) {
            mMemory.put(key, value, expiredTime);
        }

        if (mEnableDisk) {
            mDisk.put(key, value, expiredTime);
        }
    }

    @Override
    public String getString(@NonNull String key) {
        String res = null;

        if (mEnableMemory) {
            res = mMemory.getString(key);
        }

        if (res == null && mEnableDisk) {
            res = mDisk.getString(key);

            if (res != null && mEnableMemory) {
                mMemory.put(key, res);
            }
        }

        return res;
    }

    @Override
    public byte[] getByteArray(@NonNull String key) {
        byte[] res = null;

        if (mEnableMemory) {
            res = mMemory.getByteArray(key);
        }

        if (res == null && mEnableDisk) {
            res = mDisk.getByteArray(key);

            if (res != null && mEnableMemory) {
                mMemory.put(key, res);
            }
        }

        return res;
    }

    @Override
    public Bitmap getBitmap(@NonNull String key) {
        Bitmap res = null;

        if (mEnableMemory) {
            res = mMemory.getBitmap(key);
        }

        if (res == null && mEnableDisk) {
            res = mDisk.getBitmap(key);

            if (res != null && mEnableMemory) {
                mMemory.put(key, res);
            }
        }

        return res;
    }

    @Override
    public Drawable getDrawable(@NonNull String key) {
        Drawable res = null;

        if (mEnableMemory) {
            res = mMemory.getDrawable(key);
        }

        if (res == null && mEnableDisk) {
            res = mDisk.getDrawable(key);

            if (res != null && mEnableMemory) {
                mMemory.put(key, res);
            }
        }

        return res;
    }

    @Override
    public <T> T get(@NonNull String key, Class<T> rawClazz, Type... parameterizedClazzs) {
        T res = null;

        if (mEnableMemory) {
            res = mMemory.get(key);
        }

        if (res == null && mEnableDisk) {
            res = mDisk.get(key, rawClazz, parameterizedClazzs);

            if (res != null && mEnableMemory) {
                mMemory.put(key, res);
            }
        }

        return res;
    }

    @Override
    public void remove(@NonNull String key) {
        if (mEnableMemory) {
            mMemory.remove(key);
        }

        if (mEnableDisk) {
            mDisk.remove(key);
        }
    }

    @Override
    public void clear() {
        if (mEnableMemory) {
            mMemory.clear();
        }

        if (mEnableDisk) {
            mDisk.clear();
        }
    }

    @Override
    public void close() {
        if (mEnableMemory) {
            mMemory.close();
        }

        if (mEnableDisk) {
            mDisk.close();
        }
    }

    /**
     * 返回 disk cache 实例
     */
    @Nullable
    public DiskCache disk() {
        return mDisk;
    }

    /**
     * 返回 memory cache 实例
     */
    @Nullable
    public MemoryCache memory() {
        return mMemory;
    }
}
