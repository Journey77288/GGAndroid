package io.ganguo.library.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务 Runnable Util
 * <p/>
 * Created by tony on 10/13/14.
 */
public class TaskUtil {

    private static ExecutorService singleExecutor = null;
    private static ExecutorService poolExecutor = null;

    /**
     * 单线程池
     *
     * @param runnable
     */
    public static void submit(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();
        }

        singleExecutor.submit(runnable);
    }

    /**
     * 多线程池
     *
     * @param runnable
     */
    public static void submits(Runnable runnable) {
        if (poolExecutor == null) {
            poolExecutor = Executors.newCachedThreadPool();
        }

        poolExecutor.submit(runnable);
    }
}
