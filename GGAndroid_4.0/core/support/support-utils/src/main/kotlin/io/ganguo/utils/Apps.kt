package io.ganguo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import io.ganguo.appcompat.Toasts.Companion.show
import kotlin.system.exitProcess

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : App Utils
 * </pre>
 */
object Apps {
    /**
     * 退出应用的双击返回间隙
     */
    private const val DOUBLE_BACK_PRESSED_DURATION: Long = 2000L
    private const val ACTIVITY_THREAD_CLASS_NAME = "android.app.ActivityThread"
    private const val CURRENT_PROCESS_NAME_METHOD_NAME = "currentProcessName"
    private var appExit: Boolean = false
    private var lastBackPressedTime = 0L// last time press back

    /**
     * Quit the application
     * @param allActivity List<Activity>
     */
    fun exitApp(allActivity: List<Activity>) {
        startPhoneDesktop(allActivity.first())
        // Call this method after 200 ms to avoid exiting the application black screen
        postDelayed(200) {
            try {
                allActivity.forEach {
                    it.finish()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(javaClass.simpleName, "Exit application failure")
            } finally {
                exitProcess(0)
            }
        }
    }

    /**
     * Double-click on the exit
     *
     * @param totalMillisInFuture Long  Double Back Pressed Duration
     * @param allActivity List<Activity>
     */
    fun exitByDoublePressed(
        totalMillisInFuture: Long = DOUBLE_BACK_PRESSED_DURATION, allActivity: List<Activity>) {
        if (System.currentTimeMillis() - lastBackPressedTime > totalMillisInFuture) {
            appExit = false
        }
        if (!appExit) {
            appExit = true
            show(R.string.str_double_pressed_exit)
            lastBackPressedTime = System.currentTimeMillis()
        } else {
            exitApp(allActivity)
        }
    }

    /**
     * Return to the phone home page
     * @param context Context
     */
    private fun startPhoneDesktop(context: Context) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addCategory(Intent.CATEGORY_HOME)
        context.startActivity(intent)
    }


    /**
     * get App ProcessName
     * @return String
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    fun getProcessName(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Application.getProcessName()
        } else {
            var processName = ""
            try {
                val clazz = Class.forName(ACTIVITY_THREAD_CLASS_NAME, false, Application::class.java.classLoader)
                val method = clazz.getDeclaredMethod(CURRENT_PROCESS_NAME_METHOD_NAME)
                method.isAccessible = true
                val invoke = method.invoke(null)
                if (invoke is String) {
                    processName = invoke
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return processName
        }
    }
}