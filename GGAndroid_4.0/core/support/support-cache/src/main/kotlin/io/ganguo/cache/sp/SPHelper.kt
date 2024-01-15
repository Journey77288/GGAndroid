package io.ganguo.cache.sp

import android.app.Application
import com.tencent.mmkv.MMKV
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/11/23
 *     desc   : SharedPreHelper Utils
 *              SharedPreferences工具类，通过MMKV实现，相比默认SharedPreferences性能更好，稳定性更强，建议默认使用
 * </pre>
 */
object SPHelper {
    private lateinit var mmkv: MMKV

    fun initialize(context: Application) {
        check(!isInitialize()) { "Already initialized" }
        MMKV.initialize(context)
        mmkv = MMKV.defaultMMKV()
    }

    private fun isInitialize(): Boolean = let { ::mmkv.isInitialized }

    /**
     * get SPHelper
     * @return SPHelper
     */
    @JvmStatic
    internal fun sp(): MMKV = let {
        check(isInitialize()) { "You have to initialize SPHelper.initialize(context,PreferencesConverter) in Application first!!" }
        mmkv
    }

    /**
     * Set a String value in the preferences' editor, to be written back
     * @param key String
     * @param value String
     */
    @JvmStatic
    fun putString(key: String, value: String) {
        sp().encode(key, value)
    }

    /**
     * Set a Object value in the preferences' editor, to be written back
     * @param key String
     * @param obj Any
     */
    @JvmStatic
    inline fun <reified T> putObject(key: String, obj: T) {
        val jsonString = Json.encodeToString<T>(obj)
        putString(key, jsonString)
    }

    /**
     * Set a Int value in the preferences' editor, to be written back
     * @param key String
     * @param value Int
     */
    @JvmStatic
    fun putInt(key: String, value: Int) {
        sp().encode(key, value)
    }

    /**
     * Set a Long value in the preferences' editor, to be written back
     * @param key String
     * @param value Long
     */
    @JvmStatic
    fun putLong(key: String, value: Long) {
        sp().encode(key, value)
    }

    /**
     * Set a boolean value in the preferences' editor, to be written back
     * @param key String
     * @param value Boolean
     */
    @JvmStatic
    fun putBoolean(key: String, value: Boolean) {
        sp().encode(key, value)
    }

    /**
     * Set a boolean value in the preferences' editor, to be written back
     * @param key String
     * @param value Boolean
     */
    @JvmStatic
    fun putFloat(key: String, value: Float) {
        sp().encode(key, value)
    }

    /**
     * Retrieve aBoolean String value from the preferences.
     * @param key String
     * @param def Boolean
     * @return Boolean
     */
    @JvmStatic
    fun getFloat(key: String, def: Float = 0.0f): Float = let {
        sp().decodeFloat(key, def)
    }

    /**
     * Retrieve aBoolean String value from the preferences.
     * @param key String
     * @param def Boolean
     * @return Boolean
     */
    @JvmStatic
    fun getBoolean(key: String, def: Boolean): Boolean = let {
        sp().decodeBool(key, def)
    }

    /**
     * Retrieve a String value from the preferences.
     * @param key String?
     * @return String?
     */
    @JvmStatic
    fun getString(key: String): String? = let {
        sp().decodeString(key, null)
    }

    /**
     * Retrieve a Int value from the preferences.
     * @param key String
     * @param def Long
     * @return Long
     */
    @JvmStatic
    fun getInt(key: String, def: Int): Int = let {
        sp().decodeInt(key, def)
    }

    /**
     * Retrieve a long value from the preferences.
     * @param key String
     * @param def Long
     * @return Long
     */
    @JvmStatic
    fun getLong(key: String, def: Long): Long = let {
        sp().decodeLong(key, def)
    }

    /**
     * Retrieve a Object value from the preferences.
     * @param key String
     * @return T?
     */
    @JvmStatic
    inline fun <reified T> getObject(key: String): T? = let {
        val jsonString = getString(key)
        jsonString?.let {
            Json.decodeFromString(it)
        }
    }

    /**
     * Retrieve a Object value from the preferences.
     * @param key String
     * @return List<T>?
     */
    @JvmStatic
    inline fun <reified T> getObjectList(key: String): List<T>? = let {
        val jsonString = getString(key)
        jsonString?.let {
            Json.decodeFromString(it)
        }
    }

    /**
     * Whether there are preferences local a cache corresponding to [key]
     * @param key String?
     * @return Boolean
     */
    @JvmStatic
    fun containsKey(key: String): Boolean = let {
        sp().containsKey(key)
    }

    /**
     * Delete multiple preferences caches
     * @param keys Array<out String>
     */
    @JvmStatic
    fun remove(vararg keys: String) {
        sp().removeValuesForKeys(keys)
    }

    /**
     * clear preferences cache
     */
    @JvmStatic
    fun clearAll() {
        sp().clearAll()
    }
}