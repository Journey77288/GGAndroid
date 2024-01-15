package io.ganguo.ggcache.disk;

import android.support.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * disk cache pool
 * thread safe
 * Created by Lynn on 2016/12/12.
 */

final class DiskCachePool implements IDiskCachePool {
    // appVersion 改变，缓存会被清除，如非必要，不必改变
    static int DEFAULT_APP_VERSION = 1;
    // valueCount key - value, 映射关系, 默认 1 对 1
    static int DEFAULT_VALUE_COUNT = 1;

    private final DiskOption mOption;
    private final DiskCacheWriteLocker mWriteLock = new DiskCacheWriteLocker();
    private DiskLruCache mCacheActor = null;

    DiskCachePool(@NonNull final DiskOption option) {
        mOption = option;
    }

    /**
     * 获取 DiskLruCache 实例
     */
    synchronized DiskLruCache getCacheActor() {
        if (mCacheActor == null || mCacheActor.isClosed()) {
            mCacheActor = newDiskCacheInstance();
        }
        return mCacheActor;
    }

    /**
     * 创建 DiskLruCache 实例
     * 涉及到io操作, 不应该在main thread初始化
     */
    private synchronized DiskLruCache newDiskCacheInstance() {
        try {
            final File cacheFile = DiskUtils.getDiskCacheDir(mOption.getContext(), mOption.getDirName());
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            return DiskLruCache.open(cacheFile,
                    DiskUtils.getAppVersion(mOption.getContext(), DEFAULT_APP_VERSION),
                    DEFAULT_VALUE_COUNT,
                    mOption.getMaxSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 字符流
     */
    @Override
    public void put(@NonNull String key, String value) {
        mWriteLock.acquire(key);
        try {
            final DiskLruCache.Editor editor = editor(key);
            BufferedWriter writer = null;
            if (editor == null) {
                return;
            }
            try {
                final OutputStream os = editor.newOutputStream(0);
                writer = new BufferedWriter(new OutputStreamWriter(os));
                //写入数据
                writer.write(value);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
                //终止
                try {
                    editor.abort();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            mWriteLock.release(key);
        }
    }

    /**
     * 字节流数组
     */
    @Override
    public void put(@NonNull String key, byte[] value) {
        mWriteLock.acquire(key);
        try {
            final DiskLruCache.Editor editor = editor(key);
            OutputStream os = null;

            if (editor == null) {
                return;
            }

            try {
                os = editor.newOutputStream(0);
                os.write(value);
                os.flush();
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    editor.abort();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            mWriteLock.release(key);
        }
    }

    @Override
    public String getString(@NonNull String key) {
        final InputStream ins = getInputStream(key);
        if (ins == null) {
            return null;
        }

        String res = null;

        try {
            res = Util.readFully(new InputStreamReader(ins, Util.UTF_8));
        } catch (IOException e) {
            try {
                // 异常情况下，可能会导致 readFully 方法没有正确关闭输入流
                ins.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public byte[] getByteArray(@NonNull String key) {
        byte[] res = null;

        final InputStream ins = getInputStream(key);
        if (ins == null) {
            return null;
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final byte[] buffer = new byte[256];
            int len = -1;
            while ((len = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            res = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public synchronized void remove(@NonNull String key) {
        try {
            final String actualKey = DiskUtils.hashKeyForDisk(key);
            getCacheActor().remove(actualKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用delete 会导致 disk cache close
     */
    @Override
    public synchronized void clear() {
        try {
            Util.deleteContents(getCacheActor().getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void close() {
        try {
            getCacheActor().close();
            mCacheActor = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取editor
     */
    DiskLruCache.Editor editor(final @NonNull String key) {
        try {
            final String actualKey = DiskUtils.hashKeyForDisk(key);
            final DiskLruCache.Editor editor = getCacheActor().edit(actualKey);
            if (editor == null) {
                //TODO 当同一个key对应的editor被编辑的时候，再请求会返回null
            }
            return editor;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    InputStream getInputStream(@NonNull final String key) {
        try {
            final DiskLruCache.Snapshot snapshot = getCacheActor().get(DiskUtils.hashKeyForDisk(key));
            if (snapshot == null) {
                // TODO 没有找到， entry.readable == false
                return null;
            }
            return snapshot.getInputStream(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
