package io.ganguo.ggcache;

import android.content.Context;
import android.support.annotation.Nullable;

import io.ganguo.ggcache.disk.DiskCache;
import io.ganguo.ggcache.disk.DiskOption;
import io.ganguo.ggcache.memory.MemoryCache;
import io.ganguo.ggcache.memory.MemoryOption;

/**
 * 缓存 manager
 * 一般数据量有一定复杂度比较适合使用缓存
 * <p>
 * 图片的存储还是推荐使用 Glide, Picasso 等成熟框架
 * 除非实在必要自己去控制图片的缓存
 * <p>
 * primitive type 推荐使用 SharedPreference
 * 数据量级较大, 且有离线使用需求，推荐 DB 离线缓存
 * <p>
 * 敏感信息，如用户账号密码, 支付信息等不应该使用, 至少不能无加密处理直接缓存
 * TODO asynchronous
 * Created by Lynn on 2016/12/12.
 */

public final class GGCache {
    private static volatile TwoLayersCache sTwoLayersCache = null;

    /**
     * 初始化方法
     * 一般在 Application onCreate 调用
     */
    public static void init(final Context context) {
        init(DiskOption.defaultOption(context), MemoryOption.defaultOption());
    }

    /**
     * 初始化方法
     * 重写方法
     */
    public static void init(@Nullable final DiskOption diskOption,
                            @Nullable final MemoryOption memoryOption) {
        init(diskOption, memoryOption, true, true);
    }

    /**
     * 初始化方法
     * 重写方法
     */
    public static void init(@Nullable final DiskOption diskOption,
                            @Nullable final MemoryOption memoryOption,
                            final boolean enableDisk,
                            final boolean enableMemory) {
        sTwoLayersCache = new TwoLayersCache(diskOption, memoryOption, enableDisk, enableMemory);
    }

    /**
     * 获取 Disk Cache 实例
     */
    public static DiskCache disk() {
        return sTwoLayersCache.disk();
    }

    /**
     * 获取 Memory Cache 实例
     */
    public static MemoryCache memory() {
        return sTwoLayersCache.memory();
    }

    /**
     * 获取 Two Layers Cache 实例
     */
    public static TwoLayersCache cache() {
        return sTwoLayersCache;
    }
}
