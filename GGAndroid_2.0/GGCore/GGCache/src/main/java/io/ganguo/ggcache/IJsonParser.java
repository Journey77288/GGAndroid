package io.ganguo.ggcache;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * json parser interface
 * Created by Lynn on 2016/12/15.
 */

public interface IJsonParser {
    /**
     * This method serializes the specified object into its equivalent Json representation.
     * This method should be used when the specified object is not a generic type.
     */
    String toJson(Object src);

    /**
     * This method serializes the specified object, including those of generic types, into its
     * equivalent Json representation. This method must be used if the specified object is a generic
     * type. For non-generic objects, use {@link #toJson(Object)} instead.
     */
    String toJson(Object src, Type typeOfSrc);

    /**
     * This method deserializes the specified Json into an object of the specified class. It is not
     * suitable to use if the specified class is a generic type since it will not have the generic
     * type information because of the Type Erasure feature of Java. Therefore, this method should not
     * be used if the desired type is a generic type. Note that this method works fine if the any of
     * the fields of the specified object are generics, just the object itself should not be a
     * generic type. For the cases when the object is of generic type, invoke
     */
    <T> T fromJson(String json, Class<T> classOfT) throws RuntimeException;

    /**
     * This method deserializes the specified Json into an object of the specified type. This method
     * is useful if the specified object is a generic type. For non-generic objects, use
     */
    <T> T fromJson(String json, Type type) throws RuntimeException;

    /** Returns an array type whose elements are all instances of {@code componentType}. */
    GenericArrayType arrayOf(Type componentType);

    /**
     * Returns a new parameterized type, applying {@code typeArguments} to {@code rawType}.
     */
    ParameterizedType newParameterizedType(Type rawType, Type... typeArguments);
}
