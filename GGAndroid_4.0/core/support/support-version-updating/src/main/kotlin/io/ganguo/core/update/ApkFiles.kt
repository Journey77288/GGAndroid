package io.ganguo.core.update

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.InputStreamReader

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/08
 *     desc   : Apk Utils
 * </pre>
 */

@Suppress("JAVA_CLASS_ON_COMPANION")
class ApkFiles private constructor() {

    companion object {
        private const val DEFAULT_APK_NAME = "app.apk"
        private const val TYPE_PACKAGE_ARCHIVE = "application/vnd.android.package-archive"

        /**
         * 静默安装
         * @param apkPath String
         * @return Boolean
         */
        @JvmStatic
        fun silentInstall(apkPath: String): Boolean {
            require(Roots.isDeviceRooted()) {
                "Unable to install silently without root"
            }
            var result = false
            var outputStream: DataOutputStream? = null
            var bufferedReader: BufferedReader? = null
            try {
                var stringBuilder = StringBuilder()
                var process = Runtime.getRuntime().exec("su")
                outputStream = DataOutputStream(process.outputStream)
                var command = generateInstallCommand(apkPath)
                outputStream.write(command.toByteArray())
                outputStream.flush()
                outputStream.writeBytes("exit\n")
                outputStream.flush()
                process.waitFor()
                bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                var message = stringBuilder.toString()
                if (!message.contains("Failure")) {
                    result = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(javaClass.simpleName, e.message.orEmpty())
            } finally {
                outputStream?.close()
                bufferedReader?.close()
            }
            return result
        }

        /**
         * 根据系统版本返回对应的安装命令
         * @param apkPath String
         * @return String
         */
        @JvmStatic
        private fun generateInstallCommand(apkPath: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                "cat $apkPath | pm install -S ${File(apkPath).length()}\n"
            } else {
                "pm install -r $apkPath\n"
            }
        }

        /**
         * install apk from File
         * @param context Context
         * @param file File
         */
        @JvmStatic
        fun installApk(context: Context, file: File) {
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            } else {
                Uri.fromFile(file)
            }
            installApk(context, uri)
        }

        /**
         * install apk from Uri
         * @param context Context
         * @param file Uri
         */
        @JvmStatic
        fun installApk(context: Context, file: Uri) {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = Intent.ACTION_VIEW
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            intent.setDataAndType(file, TYPE_PACKAGE_ARCHIVE)
            context.startActivity(intent)
        }

        /**
         * 创建 APK 文件
         * @return File
         */
        @JvmStatic
        fun createApkFile(fileDirPath: String, link: String): File {
            var file = File(fileDirPath, subApkName(link))
            file.parentFile?.let {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            return file
        }

        /**
         * 生成文件名
         * @param link String
         * @return String
         */
        @JvmStatic
        private fun subApkName(link: String, defaultApkName: String = DEFAULT_APK_NAME): String {
            var name = defaultApkName
            if (link.isNotEmpty()) {
                name = link.substring(link.lastIndexOf("/"))
            }
            if (!name.endsWith(".apk")) {
                name = defaultApkName
            }
            return name
        }

        /**
         * 删除本地 APK 文件
         * @param file File
         */
        @JvmStatic
        fun deleteFile(file: File?) {
            deleteFile(file?.absolutePath.orEmpty())
        }

        /**
         * 删除本地 APK 文件
         * @param fileDirPath String
         */
        @JvmStatic
        fun deleteFile(fileDirPath: String) {
            if (fileDirPath.isNullOrEmpty()) {
                return
            }
            File(fileDirPath).let {
                if (it.isDirectory) {
                    deleteFiles(it.listFiles())
                } else {
                    it.delete()
                }
            }
        }

        /**
         * 清除文件目录
         * @param files Array<File>
         */
        fun deleteFiles(files: Array<File>?) {
            if (files.isNullOrEmpty()) {
                return
            }
            files.forEach {
                if (it.isDirectory) {
                    deleteFiles(it.listFiles())
                } else {
                    it.delete()
                }
            }
        }
    }
}
