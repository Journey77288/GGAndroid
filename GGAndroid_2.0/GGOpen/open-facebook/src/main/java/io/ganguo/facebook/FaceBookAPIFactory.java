package io.ganguo.facebook;


import com.facebook.CallbackManager;

/**
 * <p>
 * Facebook API 工厂类
 * </p>
 * Created by leo on 2018/9/7.
 */
public class FaceBookAPIFactory {
    private FaceBookAPIFactory() {
    }

    /**
     * function: 单例 懒加载
     *
     * @return
     */
    public static FaceBookAPIFactory get() {
        if (LazyHolder.HOLDER == null) {
            LazyHolder.HOLDER = new FaceBookAPIFactory();
        }
        return LazyHolder.HOLDER;
    }

    public CallbackManager getCallbackManager() {
        if (LazyHolder.CALL_BACK_MANAGER == null) {
            LazyHolder.CALL_BACK_MANAGER = CallbackManager.Factory.create();
        }
        return LazyHolder.CALL_BACK_MANAGER;
    }

    static class LazyHolder {
        static CallbackManager CALL_BACK_MANAGER = CallbackManager.Factory.create();
        static FaceBookAPIFactory HOLDER = new FaceBookAPIFactory();
    }

    public static void release() {
        LazyHolder.HOLDER = null;
        LazyHolder.CALL_BACK_MANAGER = null;
    }


}
