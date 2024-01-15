package io.ganguo.core

import okhttp3.internal.and
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/28
 *     desc   : MD5 工具类
 * </pre>
 */

class Md5Helper private constructor() {
    companion object {
        /**
         * 检查文件 MD5 是否匹配
         * @param file File
         * @param md5 String
         * @return Boolean
         */
        @JvmStatic
        fun checkFileCompleteness(file: File, md5: String): Boolean {
            return md5.isNotEmpty() && md5.toLowerCase() == getFileMD5(file)
        }

        /**
         * 获取文件的MD5值
         *
         * @param file
         * @return
         */
        @JvmStatic
        private fun getFileMD5(file: File?): String? {
            if (file == null || !file.exists()) {
                return ""
            }
            var fis: FileInputStream? = null
            try {
                val digest = MessageDigest.getInstance("MD5")
                fis = FileInputStream(file)
                val buffer = ByteArray(8192)
                var len: Int
                while (fis.read(buffer).also { len = it } != -1) {
                    digest.update(buffer, 0, len)
                }
                fis.close()
                return bytes2Hex(digest.digest())
            } catch (e: Exception) {
                e.printStackTrace()
                fis?.close()
            }
            return ""
        }

        /**
         * 一个byte转为2个hex字符
         *
         * @param src byte数组
         * @return 16进制大写字符串
         */
        @JvmStatic
        private fun bytes2Hex(src: ByteArray): String? {
            val res = CharArray(src.size shl 1)
            val hexDigits = charArrayOf(
                '0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                'a',
                'b',
                'c',
                'd',
                'e',
                'f'
            )
            var i = 0
            var j = 0
            while (i < src.size) {
                res[j++] = hexDigits[src[i].toInt().ushr(4).and(0x0F)]
                res[j++] = hexDigits[src[i].and(0x0F)]
                i++
            }
            return String(res)
        }
    }


}
