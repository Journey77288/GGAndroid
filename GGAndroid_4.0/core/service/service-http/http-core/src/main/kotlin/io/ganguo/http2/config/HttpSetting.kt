package io.ganguo.http2.config

import android.content.Context
import android.webkit.WebSettings

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/30
 *     desc   : 网络请求配置接口
 * </pre>
 */
interface HttpSetting {

    /**
     * 网络配置
     */
    fun setting()

    /**
     * 获取UserAgent
     * @param context Context
     * @return String
     */
    fun getUserAgent(context: Context): String {
        val defaultAgent = try {
            WebSettings.getDefaultUserAgent(context)
        } catch (e: Exception) {
            System.getProperty("http.agent").orEmpty()
        }
        return formatChineseChar(defaultAgent)
    }


    /**
     * 格式化中文字符串
     * @param str String
     * @return String
     */
    fun formatChineseChar(str: String): String {
        val sb = StringBuffer()
        for (i in str.indices) {
            var c: Char = str[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    /**
     * 创建Api服务
     * @param clazz Class<S>
     */
    fun <S> createApiService(clazz: Class<S>): S

}
