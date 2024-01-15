@file:JvmName("Strings")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : 字符串工具
 * </pre>
 */
package io.ganguo.utils

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*


/**
 * 判断字符串是否为空
 *
 * @param str
 * @return
 */
fun isEmpty(vararg str: String?): Boolean {
    for (s in str) {
        if (s.isNullOrEmpty()) {
            return true
        }
    }
    return false
}

/**
 * 判断字符串是否不为空
 *
 * @param str
 * @return
 */
fun isNotEmpty(vararg str: String?): Boolean {
    for (s in str) {
        if (isEmpty(s)) {
            return false
        }
    }
    return true
}

/**
 * 判断是不是一样的
 *
 * @param other 待比较字符串
 */
fun String?.isEqualsIgnoreCase(other: String?): Boolean {
    return equals(other, ignoreCase = true)
}

/**
 * 以一种简单的方式格式化字符串
 * 例如 String s = Strings.format("{0} is {1}", "apple", "fruit");
 * 输出 apple is fruit.
 *
 * @param args
 * @return
 */
fun String?.format(vararg args: Any): String? {
    if (this == null) {
        return this
    }
    var value = this
    for (i in args.indices) {
        value = this.replace("{$i}", args[i].toString())
    }
    return value
}

/**
 * 随机的UUID
 *
 * @return
 */
fun randomUUID(): String? {
    return UUID.randomUUID().toString()
}


/**
 * 首字母变为大写，其他保持不变
 * @return
 */
fun String?.capitalize(): String? {
    if (this == null || isEmpty()) {
        return this
    }
    var strLen: Int = this.length
    val firstChar = this[0]
    val newChar: Char = Character.toUpperCase(firstChar)
    if (firstChar == newChar) {
        // already capitalized
        return this
    }
    val newChars = CharArray(strLen)
    newChars[0] = newChar
    toCharArray(newChars, 1, 1, strLen)
    return String(newChars)
}

/**
 * 判断是否是json数据结构
 * @return
 */
fun String?.isJson(): Boolean {
    if (this == null) {
        return false
    }
    return try {
        JSONObject(this)
        true
    } catch (e: JSONException) {
        false
    }
}


/**
 * 从raw获取string
 *
 * @param context
 * @param resId   res/raw/下的资源id
 * @return
 */
fun getStringFromRaw(context: Context, resId: Int): String? {
    return try {
        resId.toInputStream(context)?.readString()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

/**
 * 从raw获取多行string
 *
 * @param context
 * @param resId   res/raw/下的资源id
 * @return
 */
fun getStringListFromRaw(context: Context, resId: Int): List<String>? {
    return try {
        mutableListOf<String>()
            .apply {
                resId.toInputStream(context)?.readString() {
                    add(it)
                }
            }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

/**
 * 从 [InputStream] 读取字符串数据
 * @param readLineAction Function1<String, Unit>
 * @return String?
 */
inline fun InputStream.readString(readLineAction: Action1<String> = {}): String? {
    var br: BufferedReader? = null
    var ir: InputStreamReader? = null
    return try {
        val str = StringBuilder()
        ir = InputStreamReader(this)
        br = BufferedReader(ir)
        var line: String?
        while (br.readLine().also { line = it } != null) {
            str.append(line)
            line?.let {
                readLineAction.invoke(it)
            }
        }
        str.toString()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } finally {
        ir?.close()
        br?.close()
    }
}