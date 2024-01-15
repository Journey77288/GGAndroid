package io.ganguo.resources

import android.app.Application
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/01
 *     desc   : Resources Support utility classes
 * </pre>
 */
object ResourcesHelper : ResourcesDelegate {
    private lateinit var context: Application
    private lateinit var resources: Resources

    /**
     * get ResourcesHelper Instance
     * @return ResourcesHelper
     */
    fun me(): ResourcesHelper {
        return this
    }


    /**
     * init ResourcesHelper
     * @param context Application
     */
    fun initialize(context: Application) {
        check(!::resources.isInitialized) { "Already initialized" }
        this.context = context
        this.resources = context.resources
    }


    /**
     * Get the DrawableRes data from [drawableResId] and return the Drawable data
     * @param drawableResId Int
     * @return Drawable?
     */
    override fun getDrawable(@DrawableRes drawableResId: Int): Drawable? {
        checkResourcesId(drawableResId)
        return ResourcesCompat.getDrawable(resources, drawableResId, null)
    }


    /**
     * Get the color data from [colorResId] and return the integer data
     * @param colorResId Int
     * @return Int
     */
    override fun getColor(@ColorRes colorResId: Int): Int {
        return ResourcesCompat.getColor(resources, colorResId, null)
    }

    override fun getColorStateList(colorResId: Int): ColorStateList? {
        return ResourcesCompat.getColorStateList(resources,colorResId,null)
    }


    /**
     * Read a string resource from [stringRes] And format.
     * @param stringRes Int
     * @return String
     */
    override fun getString(@StringRes stringRes: Int): String {
        return getResources().getString(stringRes)
    }


    /**
     * Read a string resource from [stringRes].
     * @param stringRes Int
     * @param formatArgs Array<out Any>
     * @return String
     */
    override fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String {
        return getResources().getString(stringRes, *formatArgs)
    }


    /**
     * Reads an array of strings from [arrayResId].
     * @param arrayResId Int
     * @return Array<String>
     */
    override fun getStringArray(@ArrayRes arrayResId: Int): Array<String> {
        return getResources().getStringArray(arrayResId)
    }

    /**
     * Get the dimension data from [dimenResId] and return the integer data
     * @param dimenResId Int
     * @return Int
     */
    override fun getDimensionPixelOffset(@DimenRes dimenResId: Int): Int {
        checkResourcesId(dimenResId)
        return getResources().getDimensionPixelOffset(dimenResId)
    }

    /**
     * Get the dimension data from [dimenResId] and return the Float data
     * @param dimenResId Int
     * @return Int
     */
    override fun getDimension(@DimenRes dimenResId: Int): Float {
        checkResourcesId(dimenResId)
        return getResources().getDimension(dimenResId)
    }


    /**
     * Get the dimension data from [dimenResId] and return the Float Int
     * @param dimenResId Int
     * @return Int
     */
    override fun getDimensionPixelSize(@DimenRes dimenResId: Int): Int {
        checkResourcesId(dimenResId)
        return getResources().getDimensionPixelSize(dimenResId)
    }


    /**
     * Get the resource path
     * @param resourcesId Int
     * @return String?
     */
    override fun getResourcesPath(@IdRes resourcesId: Int): String? {
        return Uri.parse(resources.getResourcePackageName(resourcesId)).toString()
    }


    /**
     * get Resources instance
     * @return Resources
     */
    override fun getResources(): Resources {
        checkInit()
        return resources
    }

    /**
     * Detects whether the tool class is initialized
     */
    private fun checkInit() {
        check(::resources.isInitialized) {
            "You have to initialize `ResourcesHelper.initialize(context)` in Application first!!"
        }
    }

    /**
     * Check that the resource id is correct
     * @param resId Int
     */
    private fun checkResourcesId(resId: Int) {
        check(resId > 0) {
            "The resource id is incorrect. Please check the compiled environment or code"
        }
    }

}