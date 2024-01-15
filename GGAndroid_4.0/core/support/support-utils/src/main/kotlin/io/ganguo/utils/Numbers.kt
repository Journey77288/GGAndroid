@file:JvmName("Numbers")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/20
 *     desc   : 数字处理工具
 * </pre>
 */
package io.ganguo.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import kotlin.math.abs


/**
 * 从Resource获得输入流
 *
 * @param context
 * @return
 */
internal fun Int.toInputStream(context: Context): InputStream? {
    return try {
        context.resources.openRawResource(this)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}


/**
 * 格式化小数位，四舍五入
 *
 * @param newScale 小数位数
 * @return
 */
fun Double.formatDecimal(newScale: Int): Double {
    return try {
        val bg = BigDecimal(this)
        bg.setScale(newScale, BigDecimal.ROUND_HALF_UP).toDouble()
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}

/**
 * 格式化小数位，四舍五入
 *
 * @param newScale 小数位数
 * @return
 */
fun Float.formatDecimal(newScale: Int): Float {
    return try {
        val bg = BigDecimal(toDouble())
        bg.setScale(newScale, BigDecimal.ROUND_HALF_UP).toFloat()
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}


/**
 * 如果含小数位 输出小数位，否则去掉小数位
 * # 1 -> 1
 * # 1. -> 1
 * # 1.1 -> 1.10
 * # 1.1111 - > 1.11
 *
 * @param newScale 最大支持小数位数
 * @return
 */
fun Double?.toPretty(newScale: Int): String? {
    if (this == null) {
        return "0"
    }
    val intPrice: Int = toInt()
    return if (intPrice.toDouble() == this) {
        intPrice.toString()
    } else {
        String.format("%.${newScale}f", this)
    }
}

/**
 * 一定范围内的随机数
 *
 * @param min
 * @param max
 * @return
 */
fun random(min: Int, max: Int): Int {
    val range = abs(max - min) + 1
    return if (min <= max) {
        min
    } else {
        max
    }.let {
        (Math.random() * range).toInt() + it
    }
}

/**
 * 数字转中文
 *
 * @return
 * @throws IOException
 */
fun Int?.toChinese(): String? {
    // 单位数组
    val units =
        arrayOf("十", "百", "千", "万", "十", "百", "千", "亿")
    // 中文大写数字数组
    val numeric =
        arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
    var res = ""
    var numberStr = toString()
    // 遍历一行中所有数字
    var k = -1
    while (numberStr.isNotEmpty()) {

        // 解析最后一位
        val j = numberStr.substring(numberStr.length - 1, numberStr.length).toInt()
        var rtemp = numeric[j]

        // 数值不是0且不是个位 或者是万位或者是亿位 则去取单位
        if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7) {
            rtemp += units[k % 8]
        }
        // 拼在之前的前面
        res = rtemp + res
        // 去除最后一位
        numberStr = numberStr.substring(0, numberStr.length - 1)
        k++
    }
    // 去除后面连续的零零..
    while (res.endsWith(numeric[0])) {
        res = res.substring(0, res.lastIndexOf(numeric[0]))
    }
    // 将零零替换成零
    while (res.indexOf(numeric[0] + numeric[0]) != -1) {
        res = res.replace(numeric[0] + numeric[0].toRegex(), numeric[0])
    }
    // 将 零+某个单位 这样的窜替换成 该单位 去掉单位前面的零
    for (m in 1 until units.size) {
        res = res.replace(numeric[0] + units[m].toRegex(), units[m])
    }
    return res
}