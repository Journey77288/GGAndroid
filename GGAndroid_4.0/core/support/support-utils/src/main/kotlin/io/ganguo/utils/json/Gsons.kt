package io.ganguo.utils.json

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.lang.reflect.Type

/**
 * Gson 工具类
 *
 *
 * Created by tony on 8/21/14.
 */
object Gsons {
    /**
     * Get reusable pre-configured [Gson] instance
     *
     * @return Gson instance
     */
    var gson = createGson(true)
    private var GSON_NO_NULLS = createGson(false)

    /**
     * gson builder
     *
     * @param builder
     */
    fun createGson(builder: GsonBuilder) {
        gson = builder.create()
        GSON_NO_NULLS = builder.create()
    }
    /**
     * Create the standard [Gson] configurationØ
     *
     * @param serializeNulls whether nulls should be serialized
     * @return created gson, never null
     */
    /**
     * Create the standard [Gson] configuration
     *
     * @return created gson, never null
     */
    @JvmOverloads
    fun createGson(serializeNulls: Boolean = true): Gson {
        val builder = GsonBuilder()

        // date formatter
        //builder.registerTypeAdapter(Date.class, new DateFormatter());
        //builder.registerTypeAdapter(DateTime.class, new DateTimeFormatter());
        //builder.registerTypeAdapter(DateTimeZone.class, new DateTimeZoneFormatter());

        // 命名规则
        // tokenAuth -> token_auth
        // token_auth -> tokenAuth
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        builder.setLenient()
        // 是否序列号带空的参数到gson中
        // { token:null }
        if (serializeNulls) {
            builder.serializeNulls()
        }
        return builder.create()
    }

    /**
     * Get reusable pre-configured [Gson] instance
     *
     * @return Gson instance
     */
    fun getGson(serializeNulls: Boolean): Gson {
        return if (serializeNulls) gson else GSON_NO_NULLS
    }
    /**
     * Convert object to json
     *
     * @return json string
     */
    /**
     * Convert object to json
     *
     * @return json string
     */
    @JvmOverloads
    fun toJson(`object`: Any?, includeNulls: Boolean = true): String {
        return if (includeNulls) gson.toJson(`object`) else GSON_NO_NULLS.toJson(
            `object`
        )
    }

    /**
     * Convert string to given type
     *
     * @return instance of type
     */
    fun <V> fromJson(json: String?, type: Class<V>?): V {
        return gson.fromJson(json, type)
    }

    /**
     * Convert string to given type
     *
     * @return instance of type
     */
    fun <V> fromJson(json: String?, type: Type?): V {
        return gson.fromJson(json, type)
    }

    /**
     * Convert content of reader to given type
     *
     * @return instance of type
     */
    fun <V> fromJson(reader: Reader?, type: Class<V>?): V {
        return gson.fromJson(reader, type)
    }

    /**
     * Convert content of reader to given type
     *
     * @return instance of type
     */
    fun <V> fromJson(reader: Reader?, type: Type?): V {
        return gson.fromJson(reader, type)
    }

    /**
     * Convert string to list of type
     *
     * @return list of type
     */
    fun <V> fromJsonList(json: String?): List<V> {
        val type: Type = object : TypeToken<ArrayList<V>>() {}.type
        return gson.fromJson<ArrayList<V>>(json, type)
    }
}