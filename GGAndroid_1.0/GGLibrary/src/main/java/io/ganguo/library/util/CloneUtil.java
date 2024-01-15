package io.ganguo.library.util;

import io.ganguo.library.util.gson.GsonUtils;

/**
 * Created by Wilson on 30/10/14.
 */
public class CloneUtil {
    public static <T> T clone(T obj) {
        if (obj == null) {
            return null;
        }
        String json = GsonUtils.toJson(obj);

        return (T) GsonUtils.fromJson(json, obj.getClass());
    }
}
