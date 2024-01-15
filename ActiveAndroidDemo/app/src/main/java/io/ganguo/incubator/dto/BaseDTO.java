package io.ganguo.incubator.dto;

import android.util.Log;

import java.lang.reflect.Type;

import io.ganguo.library.util.gson.Gsons;

/**
 * Created by Tony on 10/15/15.
 */
public class BaseDTO {

    /**
     * 通过response转换实体
     *
     * @param type
     * @param <V>
     * @return
     */
    public static <V> V parse(String content, Class<V> type) {
        try {
            return Gsons.fromJson(content, type);
        } catch (Exception e) {
            Log.e("HttpResponse", "entity error.", e);
        }
        return null;
    }

    /**
     * 通过response转换实体
     *
     * @param type
     * @param <V>
     * @return
     */
    public static <V> V parse(String content, Type type) {
        try {
            return Gsons.fromJson(content, type);
        } catch (Exception e) {
            Log.e("HttpResponse", "entity error.", e);
        }
        return null;
    }

}
