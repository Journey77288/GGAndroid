package io.ganguo.utils.util;

import java.util.Collection;
import java.util.Map;

import io.ganguo.utils.bean.Globals;


/**
 * 集合工具
 * 与{@link java.util.Collections}区分开
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class Collections {

    private Collections() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 数组是否为空
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(final T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 集合是否不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }


    /**
     * 集合是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || isEmpty(map.keySet());
    }

    /**
     * Map是否不为空
     *
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * collection 是否包含 obj
     *
     * @param collection
     * @param obj
     * @return
     */
    public static boolean isContains(Collection<?> collection, Object obj) {
        return isEmpty(collection) ? false : collection.contains(obj);
    }

    /**
     * collection 是否包含 collection
     *
     * @param all
     * @param from
     * @return
     */
    public static boolean isContainsAll(Collection<?> all, Collection<?> from) {
        return isEmpty(all) || isEmpty(from) ? false : all.contains(from);
    }


}
