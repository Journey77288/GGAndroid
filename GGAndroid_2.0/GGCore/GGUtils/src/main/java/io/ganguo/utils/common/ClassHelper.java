package io.ganguo.utils.common;

/**
 * Class判断工具类
 * Created by leo on 2018/6/15.
 */
public class ClassHelper {

    /**
     * function：判断是否是同一个类
     * @param cls
     * @param target
     *
     * @return
     */
    public static boolean isEquals(Class<?> cls, Class<?> target) {
        return (cls == null || target == null) ? false : cls.equals(target);
    }
}
