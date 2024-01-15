package io.ganguo.appcompat

import android.app.Application
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/10
 *   @desc   : Toast Message Util
 * </pre>
 */
class Toasts private constructor(var context: Application) {
    private lateinit var toast: Toast


    /**
     * show toast
     * @param stringResId Int
     * @param duration Int
     * @param xOffset Int
     * @param yOffset Int
     */
    private fun show(@StringRes stringResId: Int, duration: Int, xOffset: Int, yOffset: Int) {
        val msg = context.getString(stringResId)
        show(msg, duration, xOffset, yOffset)
    }


    /**
     * show toast
     * @param charSequence CharSequence
     * @param duration Int
     * @param xOffset Int
     * @param yOffset Int
     */
    private fun show(charSequence: CharSequence, duration: Int, xOffset: Int, yOffset: Int) {
        if (::toast.isInitialized) {
            toast.cancel()
        }
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        toast.duration = duration
        toast.setText(charSequence)
        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, xOffset, yOffset)
        toast.show()
    }


    companion object {
        private lateinit var toasts: Toasts

        /**
         * Initialize AA in Application
         * @param context Application
         */
        fun initialize(context: Application) {
            check(!::toasts.isInitialized) {
                "Already initialized"
            }
            toasts = Toasts(context)
        }


        /**
         * show toast
         * @param stringResId Int
         * @param duration Int
         * @param xOffset Int
         * @param yOffset Int
         */
        fun show(@StringRes stringResId: Int, duration: Int = Toast.LENGTH_LONG, xOffset: Int = 0, yOffset: Int = 100) {
            checkInitialize()
            toasts.show(stringResId, duration, xOffset, yOffset)
        }

        /**
         * show toast
         * @param charSequence CharSequence
         * @param duration Int
         * @param xOffset Int
         * @param yOffset Int
         */
        fun show(charSequence: CharSequence, duration: Int = Toast.LENGTH_LONG, xOffset: Int = 0, yOffset: Int = 100) {
            checkInitialize()
            toasts.show(charSequence, duration, xOffset, yOffset)
        }

        /**
         * Toasts is Initialize
         */
        private fun checkInitialize() {
            check(::toasts.isInitialized) {
                "Please initialize in Application"
            }
        }

    }
}
