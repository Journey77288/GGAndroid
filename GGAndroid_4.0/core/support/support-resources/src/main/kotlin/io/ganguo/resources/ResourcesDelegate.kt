package io.ganguo.resources

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Resources Delegate interface
 * </pre>
 */
interface ResourcesDelegate {
    /**
     *
     * @param drawableResId Int
     * @return Drawable?
     */
    fun getDrawable(@DrawableRes drawableResId: Int): Drawable?


    /**
     * Get the color data from [colorResId] and return the integer data
     * @param colorResId Int
     * @return Int
     */
    fun getColor(@ColorRes colorResId: Int): Int

    /**
     * Get the ColorStateList color data from [colorResId] and return the integer data
     * @param colorResId Int
     * @return ColorStateList?
     */
    fun getColorStateList(@ColorRes colorResId: Int): ColorStateList?

    /**
     * Read a string resource from [stringRes] And format.
     * @param stringRes Int
     * @return String
     */
    fun getString(@StringRes stringRes: Int): String


    /**
     * Read a string resource from [stringRes].
     * @param stringRes Int
     * @param formatArgs Array<out Any>
     * @return String
     */
    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String


    /**
     * Reads an array of strings from [arrayResId].
     * @param arrayResId Int
     * @return Array<String>
     */
    fun getStringArray(@ArrayRes arrayResId: Int): Array<String>

    /**
     * Get the dimension data from [dimenResId] and return the integer data
     * @param dimenResId Int
     * @return Int
     */
    fun getDimensionPixelOffset(@DimenRes dimenResId: Int): Int

    /**
     * Get the dimension data from [dimenResId] and return the Float data
     * @param dimenResId Int
     * @return Int
     */
    fun getDimension(@DimenRes dimenResId: Int): Float

    /**
     * Get the dimension data from [dimenResId] and return the Float Int
     * @param dimenResId Int
     * @return Int
     */
    fun getDimensionPixelSize(@DimenRes dimenResId: Int): Int {
        return getResources().getDimensionPixelSize(dimenResId)
    }


    /**
     * Get the resource path
     * @param resourcesId Int
     * @return String?
     */
    fun getResourcesPath(@IdRes resourcesId: Int): String?


    /**
     * get Resources instance
     * @return Resources
     */
    fun getResources(): Resources


}