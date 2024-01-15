package io.ganguo.ggcache.sp

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences as RxSharedP

/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/27
 *     desc   : RxJava Preference Utils
 * </pre>
 */
object RxSharedPreferences {
    private lateinit var appContext: Context
    private val preference: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(getContext())
    }
    private val rxPreference: RxSharedP by lazy {
        RxSharedP.create(preference)
    }


    /**
     * get Context
     * @return Context
     */
    private fun getContext(): Context {
        check(::appContext.isInitialized) {
            "You have to initialize SharedPreferences.RxSharedPreferences(context) in Application first!!"
        }
        return appContext
    }

    /**
     * init RxSharedPreferences
     * @return
     */
    @JvmStatic
    fun initialize(context: Context) {
        check(!::appContext.isInitialized) { "Already initialized" }
        appContext = context
    }


    /**
     * Retrieve a Preference[String] Object value from the preferences.
     * @param key String
     * @return Preference<String>
     */
    @JvmStatic
    fun getString(key: String): Preference<String> {
        return rxPreference.getString(key)
    }


    /**
     * Retrieve a Preference[Boolean] Object value from the preferences.
     * @param key String
     * @param default Boolean
     * @return Preference<Boolean>
     */
    @JvmStatic
    fun getBoolean(key: String, default: Boolean): Preference<Boolean> {
        return rxPreference.getBoolean(key, default)
    }

    /**
     * Retrieve a Preference[Int] Object value from the preferences.
     * @param key String
     * @return Preference<Int>
     */
    @JvmStatic
    fun getInt(key: String): Preference<Int> {
        return rxPreference.getInteger(key)
    }

    /**
     * Retrieve a Preference[T] Object value from the preferences.
     * @param key String
     * @param default T
     * @param converter Converter<T>
     * @return Preference<T>
     */
    @JvmStatic
    fun <T> getObject(key: String, default: T, converter: Preference.Converter<T>): Preference<T> {
        return rxPreference.getObject(key, default, converter)
    }
}