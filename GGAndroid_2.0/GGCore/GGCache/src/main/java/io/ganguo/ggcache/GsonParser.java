package io.ganguo.ggcache;

import com.google.gson.Gson;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * cache bridge
 * 转换 Object -> Json,
 * 然后 json 作为 DiskCache 的介质
 * gson impl
 * Created by Lynn on 2016/12/15.
 */

public class GsonParser implements IJsonParser {
    private final Gson mGson;

    public GsonParser() {
        mGson = new Gson();
    }

    public GsonParser(Gson gson) {
        mGson = gson;
    }

    @Override
    public String toJson(final Object src) {
        return mGson.toJson(src);
    }

    /**
     * 范型 generic type
     */
    @Override
    public String toJson(Object src, Type typeOfSrc) {
        return mGson.toJson(src, typeOfSrc);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) throws RuntimeException {
        return mGson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(String json, Type type) throws RuntimeException {
        return mGson.fromJson(json, type);
    }

    @Override
    public GenericArrayType arrayOf(Type componentType) {
        return Types.arrayOf(componentType);
    }

    @Override
    public ParameterizedType newParameterizedType(Type rawType, Type... typeArguments) {
        return Types.newParameterizedType(rawType, typeArguments);
    }
}
