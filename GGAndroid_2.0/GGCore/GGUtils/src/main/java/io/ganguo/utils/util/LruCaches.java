package io.ganguo.utils.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by leo on 16/8/17.
 * LruCache 图片缓存优化处理类
 */
public class LruCaches extends LruCache<String, Bitmap> {
    private static int MAX_MEMORY = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static LruCaches mLruCaches;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private LruCaches(int maxSize) {
        super(maxSize);
    }

    /**
     * 单例
     */
    public static LruCaches getInstance() {
        if (mLruCaches == null) {
            mLruCaches = new LruCaches(MAX_MEMORY / 5);
        }
        return mLruCaches;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    /**
     * 清理缓存
     */
    public void clearCache() {
        if (mLruCaches.size() > 0) {
            mLruCaches.evictAll();
        }
    }


    /**
     * 添加缓存图片
     *
     * @param key
     * @param bitmap
     */
    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mLruCaches.get(key) != null) {
            return;
        }
        if (!Strings.isEmpty(key) && bitmap != null) {
            mLruCaches.put(key, bitmap);
        }
    }


    /**
     * 获取缓存图片
     *
     * @param key
     */
    public synchronized Bitmap getBitmapFromMemCache(String key) {
        if (Strings.isEmpty(key)) {
            return null;
        }
        Bitmap bm = mLruCaches.get(key);
        if (bm != null && !bm.isRecycled()) {
            return bm;
        }
        return null;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public synchronized void removeImageCache(String key) {
        if (Strings.isEmpty(key)) {
            return;
        }
        Bitmap bm = mLruCaches.remove(key);
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
        }
    }

}
