package io.ganguo.utils.util;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.ganguo.utils.bean.Globals;
import io.ganguo.utils.util.log.Logger;

/**
 * 任务处理工具
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class Tasks {
    public static final int THREAD_POOL_SIZE = 3;

    private static Handler mHandler = null;
    private static ExecutorService singleExecutor = null;
    private static ExecutorService poolExecutor = null;

    private Tasks() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 主线程handler
     *
     * @return
     */
    public static Handler handler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        } else {
            if (mHandler.getLooper() != Looper.getMainLooper()) {
                mHandler = new Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }

    /**
     * 主线程执行
     *
     * @param runnable
     * @return
     */
    public static boolean runOnUiThread(Runnable runnable) {
        return handler().post((new RunnableAdapter(runnable)));
    }

    /**
     * 单线程列队执行
     *
     * @param runnable
     * @return
     */
    public static Future<?> runOnQueue(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();
        }
        return singleExecutor.submit((new RunnableAdapter(runnable)));
    }

    /**
     * 多线程执行
     *
     * @param runnable
     * @return
     */
    public static Future<?> runOnThreadPool(Runnable runnable) {
        if (poolExecutor == null) {
            poolExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        }
        return poolExecutor.submit(new RunnableAdapter(runnable));
    }

    /**
     * Runnable Adapter
     */
    public static class RunnableAdapter implements Runnable {
        private Runnable runnable;

        public RunnableAdapter(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            Benchmark.start("runnable[" + Thread.currentThread().getId() + "]");
            try {
                runnable.run();
            } catch (Throwable e) {
                Logger.e("running task occurs exception:", e);
            } finally {
                Benchmark.end("runnable[" + Thread.currentThread().getId() + "]");
            }
        }
    }


    /**
     * 用来移除我们主线程post的handler，避免出现空指针或者内存溢出
     * <p>
     * clazz参数 传入 getClass()
     * <p>
     * objectThis参数 传入 你使用的Object既this
     *
     * @param clazz
     * @param objectThis
     */
    public static void releaseHandlers(Class<?> clazz, Object objectThis) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            if (fields == null || fields.length <= 0) {
                return;
            }
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Handler.class.isAssignableFrom(field.getType())) continue;
                Handler handler = (Handler) field.get(objectThis);
                if (handler != null && handler.getLooper() == Looper.getMainLooper()) {
                    handler.removeCallbacksAndMessages(null);
                }
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
