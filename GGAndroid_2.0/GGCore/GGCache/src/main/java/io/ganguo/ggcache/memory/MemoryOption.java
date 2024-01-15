package io.ganguo.ggcache.memory;

/**
 * option for memory cache
 * Created by Lynn on 2016/12/22.
 */

public final class MemoryOption {
    // 默认 应用分配最大内存 1/1024 个数， 假设平均每个 cache record 1kB 大小
    private static final int DEFAULT_MAX_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024);

    private final int mMaxSize;

    private MemoryOption(Builder builder) {
        mMaxSize = builder.maxSize;
    }

    public int getMaxSize() {
        return mMaxSize;
    }

    /**
     * 返回默认的 memory cache option
     */
    public static MemoryOption defaultOption() {
        return new Builder()
                .maxSize(DEFAULT_MAX_SIZE)
                .build();
    }

    public static class Builder {
        private int maxSize;

        public Builder maxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public MemoryOption build() {
            if (maxSize <= 0) {
                maxSize = DEFAULT_MAX_SIZE;
            }

            return new MemoryOption(this);
        }
    }
}
