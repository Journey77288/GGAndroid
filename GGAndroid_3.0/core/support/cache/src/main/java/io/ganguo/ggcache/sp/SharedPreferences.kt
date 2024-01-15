package io.ganguo.ggcache.sp

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/27
 *     desc   : SharedPreferences Utils
 * </pre>
 */
object SharedPreferences {
    private lateinit var preferences: SharedPreferences
    private lateinit var converter: Converter

    /**
     * init SharedPreferences
     * @param context Application
     */
    fun initialize(context: Application, converter: Converter) {
        check(!::preferences.isInitialized) { "Already initialized" }
        this.converter = converter
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }


    /**
     * get SharedPreferences
     * @return SharedPreferences
     */
    @JvmStatic
    private fun sp(): SharedPreferences {
        check(::preferences.isInitialized) {
            "You have to initialize SharedPreferences.initialize(context,PreferencesConverter) in Application first!!"
        }
        return preferences
    }

    /**
     * Set a String value in the preferences' editor, to be written back
     * @param key String
     * @param value String
     */
    @JvmStatic
    fun putString(key: String, value: String) {
        sp().edit {
            putString(key, value)
        }
    }


    /**
     * Set a Object value in the preferences' editor, to be written back
     * @param key String
     * @param obj Any
     */
    @JvmStatic
    fun putObject(key: String, obj: Any) {
        val jsonString = converter.toJsonString(obj)
        sp().edit {
            putString(key, jsonString)
        }
    }

    /**
     * Set a Int value in the preferences' editor, to be written back
     * @param key String
     * @param value Int
     */
    @JvmStatic
    fun putInt(key: String, value: Int) {
        sp().edit {
            putInt(key, value)
        }
    }

    /**
     * Set a Long value in the preferences' editor, to be written back
     * @param key String
     * @param value Long
     */
    @JvmStatic
    fun putLong(key: String, value: Long) {
        sp().edit {
            putLong(key, value)
        }
    }

    /**
     * Set a boolean value in the preferences' editor, to be written back
     * @param key String
     * @param value Boolean
     */
    @JvmStatic
    fun putBoolean(key: String, value: Boolean) {
        sp().edit {
            putBoolean(key, value)
        }
    }

    /**
     * Retrieve aBoolean String value from the preferences.
     * @param key String
     * @param def Boolean
     * @return Boolean
     */
    @JvmStatic
    fun getBoolean(key: String, def: Boolean): Boolean {
        return sp().getBoolean(key, def)
    }

    /**
     * Retrieve a String value from the preferences.
     * @param key String?
     * @return String?
     */
    @JvmStatic
    fun getString(key: String): String? {
        return sp().getString(key, null)
    }

    /**
     * Retrieve a Int value from the preferences.
     * @param key String
     * @param def Long
     * @return Long
     */
    @JvmStatic
    fun getInt(key: String, def: Int): Int {
        return sp().getInt(key, def)
    }

    /**
     * Retrieve a long value from the preferences.
     * @param key String
     * @param def Long
     * @return Long
     */
    @JvmStatic
    fun getLong(key: String, def: Long): Long {
        return sp().getLong(key, def)
    }

    /**
     * Retrieve a Object value from the preferences.
     * @param key String
     * @param clazz Class<T>
     * @return T?
     */
    @JvmStatic
    fun <T> getObject(key: String, clazz: Class<T>): T? {
        val jsonString = sp().getString(key, null)
        return jsonString?.let {
            converter.toObject(jsonString, clazz)
        }
    }

    /**
     * Whether there are preferences local a cache corresponding to [key]
     * @param key String?
     * @return Boolean
     */
    @JvmStatic
    fun containsKey(key: String): Boolean {
        return sp().contains(key)
    }

    /**
     * Delete multiple preferences caches
     * @param keys Array<out String>
     */
    @JvmStatic
    fun remove(vararg keys: String) {
        sp().edit {
            keys.forEach {
                remove(it)
            }
        }
    }

    /**
     * clear preferences cache
     */
    @JvmStatic
    fun clearAll() {
        sp().edit {
            clear()
        }
    }


    /**
     * String to Object or Object to json String Converter
     */
    interface Converter {
        /**
         * Object to json String
         * @param any Any
         * @return String
         */
        fun toJsonString(any: Any): String

        /**
         * String to Object
         * @param jsonString String
         * @param clazz Class<T>
         */
        fun <T> toObject(jsonString: String, clazz: Class<T>): T?
    }
}