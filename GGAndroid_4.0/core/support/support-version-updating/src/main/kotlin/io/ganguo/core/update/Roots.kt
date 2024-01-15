package io.ganguo.core.update

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/08
 *     desc   : phone root utils
 * </pre>
 */
class Roots private constructor() {
    companion object {

        /**
         * 判断设备是否有root权限
         * @return Boolean
         */
        fun isDeviceRooted(): Boolean {
            return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
        }

        /**
         * 判断设备是否有root权限
         * @return Boolean
         */
        private fun checkRootMethod1(): Boolean {
            val buildTags = Build.TAGS
            return buildTags != null && buildTags.contains("test-keys")
        }

        /**
         * 判断设备是否有root权限
         * @return Boolean
         */
        private fun checkRootMethod2(): Boolean {
            val paths = arrayOf("/system/app/Superuser.apk",
                    "/sbin/su",
                    "/system/bin/su",
                    "/system/xbin/su",
                    "/data/local/xbin/su",
                    "/data/local/bin/su",
                    "/system/sd/xbin/su",
                    "/system/bin/failsafe/su",
                    "/data/local/su", "/su/bin/su")
            for (path in paths) {
                if (File(path).exists()) return true
            }
            return false
        }

        /**
         * 判断设备是否有root权限
         * @return Boolean
         */
        private fun checkRootMethod3(): Boolean {
            var process: Process? = null
            return try {
                process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
                val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
                bufferedReader.readLine() != null
            } catch (t: Throwable) {
                false
            } finally {
                process?.destroy()
            }
        }
    }
}
