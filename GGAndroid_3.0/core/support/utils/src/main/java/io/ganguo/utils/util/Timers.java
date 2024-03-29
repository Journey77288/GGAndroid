package io.ganguo.utils.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.ganguo.utils.bean.Globals;

/**
 * Timer处理工具，UI线程执行
 *
 * @author ZhiHui_Chen
 * @Email: 6208317#qq.com
 * @Date 2013-5-23
 */
public class Timers {

    private final static Timer timer = new Timer();
    private final static Map<Runnable, TimerAdapter> map = new HashMap<Runnable, TimerAdapter>();

    private Timers() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 间隔多少毫秒执行一次
     *
     * @param runnable
     * @param interval
     */
    public static void setInterval(Runnable runnable, long interval) {
        TimerAdapter adapter = new TimerAdapter(runnable);
        map.put(runnable, adapter);
        timer.schedule(adapter, interval, interval);
    }

    /**
     * 多少毫秒后执行该线程 只执行一次
     *
     * @param runnable
     * @param delay
     */
    public static void setTimeout(Runnable runnable, long delay) {
        TimerAdapter adapter = new TimerAdapter(runnable);
        map.put(runnable, adapter);
        timer.schedule(adapter, delay);
    }

    public static void killTimer(Runnable runnable) {
        if (map.containsKey(runnable)) {
            TimerAdapter adapter = map.get(runnable);
            adapter.cancel();
            map.remove(runnable);
            timer.purge();
        }
    }

    public static void killAll() {
        for (Runnable r : map.keySet()) {
            map.get(r).cancel();
            timer.purge();
        }
        map.clear();
    }

    private static class TimerAdapter extends TimerTask {
        private Runnable runnable;

        public TimerAdapter(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                TasksKt.handler().post(runnable);
            } catch (Throwable e) {
                Log.e(TimerAdapter.class.getName(), "failed to run timer task:", e);
                System.out.println("TimerAdapter run timer error!" + e.getMessage());
            }
        }
    }
}

