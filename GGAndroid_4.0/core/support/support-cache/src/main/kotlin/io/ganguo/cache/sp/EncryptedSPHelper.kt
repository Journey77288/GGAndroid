package io.ganguo.cache.sp

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import java.lang.reflect.Type
import android.content.SharedPreferences as AndroidSharedPreferences

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/27
 *     desc   : EncryptedSharedPreHelper Utils
 *              已加密SharedPreference工具类，建议用于存储对安全要求更高的信息
 * </pre>
 */
object EncryptedSPHelper {
    private lateinit var preferences: AndroidSharedPreferences
    private lateinit var converter: Converter

    /**
     * init SharedPreHelper
     * @param context Application
     */
    fun initialize(context: Application, converter: Converter) {
        check(!EncryptedSPHelper::preferences.isInitialized) { "Already initialized" }
        EncryptedSPHelper.converter = converter
        preferences = EncryptedSharedPreferences.create(
                getSharedPreferencesFileName(context), getMasterKeyAlias(context), context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    /**
     * getSharedPreferencesFileName
     * @param context Context
     * @return String
     * 备注 SharePreferencesFileName 不能包含特殊符号：比如"_"等
     * 如有特殊符号会使clear报SecurityException: Could not decrypt key. decryption failed
     */
    private fun getSharedPreferencesFileName(context: Context): String = let {
        context.packageName + "preferences"
    }

    /**
     * getSharedPreferencesFileName
     * @param context Context
     * @return String
     * 备注 MasterKeyAlias 不能包含特殊符号：比如"_"等
     * 如有特殊符号会使clear报SecurityException: Could not decrypt key. decryption failed
     */
    private fun getMasterKeyAlias(context: Context): String = let {
        context.packageName + "masterKeyAlias"
    }

    /**
     * get EncryptedSharedPreHelper
     * @return EncryptedSharedPreHelper
     */
    @JvmStatic
    internal fun sp(): AndroidSharedPreferences = let {
        check(EncryptedSPHelper::preferences.isInitialized) {
            "You have to initialize EncryptedSharedPreHelper.initialize(context,PreferencesConverter) in Application first!!"
        }
        preferences
    }

    /**
     * Set a String value in the preferences' editor, to be written back
     * @param key String
     * @param value String
     */
    @JvmStatic
    fun putString(key: String, value: String) {
        edit {
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
        edit {
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
        edit {
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
        edit {
            putLong(key, value)
        }
    }


    /**
     * 保存修改
     * @param action [@kotlin.ExtensionFunctionType] Function1<Editor, Unit>
     */
    private fun edit(action: android.content.SharedPreferences.Editor.() -> Unit) {
        sp().edit(true, action)
    }

    /**
     * Set a boolean value in the preferences' editor, to be written back
     * @param key String
     * @param value Boolean
     */
    @JvmStatic
    fun putBoolean(key: String, value: Boolean) {
        edit {
            putBoolean(key, value)
        }
    }


    /**
     * Set a boolean value in the preferences' editor, to be written back
     * @param key String
     * @param value Boolean
     */
    @JvmStatic
    fun putFloat(key: String, value: Float) {
        edit {
            putFloat(key, value)
        }
    }

    /**
     * Retrieve aBoolean String value from the preferences.
     * @param key String
     * @param def Boolean
     * @return Boolean
     */
    @JvmStatic
    fun getFloat(key: String, def: Float = 0.0f): Float = let {
        sp().getFloat(key, def)
    }

    /**
     * Retrieve aBoolean String value from the preferences.
     * @param key String
     * @param def Boolean
     * @return Boolean
     */
    @JvmStatic
    fun getBoolean(key: String, def: Boolean): Boolean = let {
        sp().getBoolean(key, def)
    }


    /**
     * Retrieve a String value from the preferences.
     * @param key String?
     * @return String?
     */
    @JvmStatic
    fun getString(key: String): String? = let {
        sp().getString(key, null)
    }

    /**
     * Retrieve a Int value from the preferences.
     * @param key String
     * @param def Long
     * @return Long
     */
    @JvmStatic
    fun getInt(key: String, def: Int): Int = let {
        sp().getInt(key, def)
    }

    /**
     * Retrieve a long value from the preferences.
     * @param key String
     * @param def Long
     * @return Long
     */
    @JvmStatic
    fun getLong(key: String, def: Long): Long = let {
        sp().getLong(key, def)
    }


    /**
     * Retrieve a Object value from the preferences.
     * @param key String
     * @param clazz Class<T>
     * @return T?
     */
    @JvmStatic
    fun <T> getObject(key: String, clazz: Class<T>): T? = let {
        val jsonString = sp().getString(key, null)
        jsonString?.let {
            converter.toObject(jsonString, clazz)
        }
    }


    /**
     * Retrieve a Object value from the preferences.
     * @param key String
     * @param type Type
     * @return T?
     */
    @JvmStatic
    fun <T> geObject(key: String, type: Type): List<T>? = let {
        val jsonString = sp().getString(key, null)
        jsonString?.let {
            converter.toObject(jsonString, type)
        }
    }

    /**
     * Whether there are preferences local a cache corresponding to [key]
     * @param key String?
     * @return Boolean
     */
    @JvmStatic
    fun containsKey(key: String): Boolean = let {
        sp().contains(key)
    }

    /**
     * Delete multiple preferences caches
     * @param keys Array<out String>
     */
    @JvmStatic
    fun remove(vararg keys: String) {
        edit {
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
        edit {
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

        /**
         * String to List Object
         * @param jsonString String
         * @param listType Type
         */
        fun <T> toObject(jsonString: String, listType: Type): List<T>?
    }
}
