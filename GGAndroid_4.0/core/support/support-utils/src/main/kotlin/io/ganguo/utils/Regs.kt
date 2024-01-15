@file:JvmName("Regs")

package io.ganguo.utils

import java.util.regex.Pattern

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/20
 *     desc   : 常用正则表达式
 * </pre>
 */
private object Regex {
    /**
     * URL正则
     */
    @JvmField
    internal val URL_PATTERN = Pattern
        .compile("[a-zA-z]+://[^\\s]*")
        .toRegex()

    /**
     * 邮政编码正则
     */
    @JvmField
    internal val ZIP_PATTERN = Pattern
        .compile("[1-9]\\d{5}(?!\\d)")
        .toRegex()

    /**
     * 身份证正则
     */
    @JvmField
    internal val IDCARD_PATTERN = Pattern
        .compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")
        .toRegex()

    /**
     * 手机号码正则
     */
    @JvmField
    internal val MOBILE_PATTERN = Pattern
        .compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57]|166|198|199)[0-9]{8}$")
        .toRegex()

    /**
     * 邮箱正则
     */
    @JvmField
    internal val EMAIL_PATTERN = Pattern
        .compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")
        .toRegex()

    /**
     * 数字和字母正则：只包含数字和字母，不包含特殊字符
     */
    @JvmField
    internal val NUMBER_LETTER = Pattern.compile("^[A-Za-z0-9]+$")
        .toRegex()

    /**
     * 数字正则：只包含数字，不含特殊字符
     */
    @JvmField
    internal val NUMBER = Pattern.compile("^[0-9]+$")
        .toRegex()

    /**
     * 字母正则：只包含字母，不含特殊字符
     */
    @JvmField
    internal val LETTER = Pattern.compile("^[A-Za-z]+$")
        .toRegex()

    /**
     * 大写字母正则：只包含大写字母，不含特殊字符
     */
    internal val CAPITAL_LETTER = Pattern.compile("^[A-Z]+$")
        .toRegex()

    /**
     * 小写字母正则：只包含小写字母，不含特殊字符
     */
    internal val LOWERCASE_LETTER = Pattern.compile("^[a-z]+$")
        .toRegex()

    /**
     * 中文字符正则
     */
    internal val CHINESE_CHARACTER = Pattern.compile("[\u0391-\uFFE5]")
        .toRegex()

    /**
     * 密码正则：密码需要包含数字、字母，不含特殊字符，且长度需要8~16位
     */
    internal val PASSWORD = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")
        .toRegex()
}


/**
 * 密碼是否有效(格式为字母+数字，且长度为8~16位)
 *
 * @return
 */
fun String?.isPasswordValid(): Boolean {
    return this?.matches(Regex.PASSWORD) ?: false
}

/**
 * 判断是不是电子邮件地址
 * @return
 */
fun String?.isEmail(): Boolean {
    return this?.matches(Regex.EMAIL_PATTERN) ?: false
}

/**
 * 判断是不是URL地址
 *
 * @return
 */
fun String?.isUrl(): Boolean {
    return this?.matches(Regex.URL_PATTERN) ?: false
}

/**
 * 判断是不是手机号码
 * @return
 */
fun String?.isMobile(): Boolean {
    return this?.matches(Regex.MOBILE_PATTERN) ?: false
}

/**
 * 判断是不是身份证号码
 * @return
 */
fun String?.isIDCard(): Boolean {
    return this?.matches(Regex.IDCARD_PATTERN) ?: false
}

/**
 * 判断是不是邮政编码
 * @return
 */
fun String?.isZipCode(): Boolean {
    return this?.matches(Regex.ZIP_PATTERN) ?: false
}

/**
 * 只含字母和数字(不带特殊字符)
 * @return
 */
fun String?.isNumberLetter(): Boolean {
    return this?.matches(Regex.NUMBER_LETTER) ?: false
}

/**
 * 只含数字(不带特殊字符)
 * @return
 */
fun String?.isNumber(): Boolean {
    return this?.matches(Regex.NUMBER) ?: false
}

/**
 * 只含字母(不带特殊字符)
 *
 * @return
 */
fun String?.isLetter(): Boolean {
    return this?.matches(Regex.LETTER) ?: false
}

/**
 * 只含大写字母(不带特殊字符)
 * @return
 */
fun String?.isCapitalLetter(): Boolean {
    return this?.matches(Regex.CAPITAL_LETTER) ?: false
}

/**
 * 只含小写字母(不带特殊字符)
 * @return
 */
fun String?.isLowercaseLetter(): Boolean {
    return this?.matches(Regex.LOWERCASE_LETTER) ?: false
}

/**
 * 只有中文
 * @return
 */
fun String?.isChineseCharacter(): Boolean {
    return this?.matches(Regex.CHINESE_CHARACTER) ?: false
}

/**
 * 包含中文
 * @return
 */
fun String?.isContainChineseCharacter(): Boolean {
    if (this == null || isEmpty()) {
        return false
    }
    for (i in this.indices) {
        val temp = substring(i, i + 1)
        val flag = temp.isChineseCharacter()
        if (flag) {
            return true
        }
    }
    return false
}