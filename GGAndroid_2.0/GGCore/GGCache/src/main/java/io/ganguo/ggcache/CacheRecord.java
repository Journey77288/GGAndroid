package io.ganguo.ggcache;

/**
 * cachepool 保存的实际数据record
 * TODO security 加密
 * Created by Lynn on 2016/12/15.
 */

public class CacheRecord<T> {
    //是否有缓存时间限制
    private final boolean mExpirable;
    //具体的过时时间戳
    private final long mExpiredTime;
    //具体存储的数据
    private final T data;

    public CacheRecord(T data) {
        this(data, false, 0L);
    }

    public CacheRecord(final T data, final boolean expirable, final long expiredTime) {
        this.data = data;
        this.mExpirable = expirable;
        this.mExpiredTime = expiredTime;
    }

    public T getData() {
        return data;
    }

    public boolean isExpirable() {
        return mExpirable;
    }

    /**
     * 过时判断
     */
    public boolean isExpired() {
        return isExpirable() && (System.currentTimeMillis() - mExpiredTime >= 0);
    }
}
