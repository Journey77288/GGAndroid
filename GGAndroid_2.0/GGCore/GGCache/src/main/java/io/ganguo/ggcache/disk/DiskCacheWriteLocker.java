package io.ganguo.ggcache.disk;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * write lock
 * Created by Lynn on 2016/12/21.
 */

final class DiskCacheWriteLocker {
    private final Map<String, WriteLock> locks = new HashMap<>();
    private final WriteLockPool writeLockPool = new WriteLockPool();

    /**
     * 根据当前的 key 获取 lock
     */
    void acquire(@NonNull String key) {
        WriteLock writeLock;
        synchronized (this) {
            // 尝试获取 lock
            writeLock = locks.get(key);
            if (writeLock == null) {
                // 当前的 key 第一次获取 lock , 从 pool 中获取
                writeLock = writeLockPool.obtain();
                // 把当前使用的 lock 和 key 记录起来
                locks.put(key, writeLock);
            }
            writeLock.interestedThreads++;
        }
        // lock 之后执行的代码块
        writeLock.lock.lock();
    }

    /**
     * 释放当前 key 对应的 lock
     */
    void release(@NonNull String key) {
        WriteLock writeLock;
        synchronized (this) {
            // 根据对应 key , 取出已经在实用的 lock
            writeLock = locks.get(key);

            if (writeLock == null) {
                throw new IllegalStateException("write lock is null");
            }

            if (writeLock.interestedThreads < 1) {
                throw new IllegalStateException("Cannot release a lock that is not held"
                        + ", key: " + key
                        + ", interestedThreads: " + writeLock.interestedThreads);
            }

            writeLock.interestedThreads--;
            if (writeLock.interestedThreads == 0) {
                // lock 没有被持有了, 放回 lock pool
                WriteLock removed = locks.remove(key);
                if (!removed.equals(writeLock)) {
                    throw new IllegalStateException("Removed the wrong lock"
                            + ", expected to remove: " + writeLock
                            + ", but actually removed: " + removed
                            + ", key: " + key);
                }
                writeLockPool.offer(removed);
            }
        }
        // 释放被 lock 的代码块, 后面的线程可以尝试 lock 进入
        writeLock.lock.unlock();
    }

    /**
     * write lock
     */
    private static class WriteLock {
        final Lock lock = new ReentrantLock();
        int interestedThreads;

        WriteLock() {
        }
    }

    /**
     * 缓存 write lock 的 lock pool
     */
    private static class WriteLockPool {
        private static final int MAX_POOL_SIZE = 10;
        private final Queue<WriteLock> pool = new ArrayDeque<>();

        WriteLockPool() {
        }

        /**
         * 尝试从池子中获取 lock
         * 如果没有空闲的 lock, 新建一个
         */
        WriteLock obtain() {
            WriteLock result;
            synchronized (pool) {
                result = pool.poll();
            }
            if (result == null) {
                result = new WriteLock();
            }
            return result;
        }

        /**
         * 把用完的 lock 加入到池子中, 以便复用
         */
        void offer(WriteLock writeLock) {
            synchronized (pool) {
                if (pool.size() < MAX_POOL_SIZE) {
                    pool.offer(writeLock);
                }
            }
        }
    }
}
