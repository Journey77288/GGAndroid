package io.ganguo.ggcache.disk;

import android.content.Context;

/**
 * for disk cache initialization
 * Created by Lynn on 2016/12/22.
 */

public final class DiskOption {
    // default 文件名字
    private static String DEFAULT_DIR_NAME = "ggcache_dir";
    // disk 缓存容量 15MB
    private static int DEFAULT_MAX_SIZE = 15 * 1024 * 1024;

    private final String mDirName;
    private final int mMaxSize;
    private final Context mContext;

    private DiskOption(Builder builder) {
        mContext = builder.context;
        mDirName = builder.dirName;
        mMaxSize = builder.maxSize;
    }

    public String getDirName() {
        return mDirName;
    }

    public int getMaxSize() {
        return mMaxSize;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 返回默认配置的 disk option 实例
     */
    public static DiskOption defaultOption(final Context context) {
        return new Builder(context)
                .dirName(DEFAULT_DIR_NAME)
                .maxSize(DEFAULT_MAX_SIZE)
                .build();
    }

    /**
     * Builder for building a disk cache option
     */
    public static class Builder {
        private String dirName;
        private int maxSize;
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder dirName(String dirName) {
            this.dirName = dirName;
            return this;
        }

        public Builder maxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public DiskOption build() {
            if (context == null) {
                throw new IllegalArgumentException("context could not be null");
            }

            if (maxSize <= 0) {
                maxSize = DEFAULT_MAX_SIZE;
            }

            if (dirName == null || dirName.equals("")) {
                dirName = DEFAULT_DIR_NAME;
            }

            return new DiskOption(this);
        }
    }
}
