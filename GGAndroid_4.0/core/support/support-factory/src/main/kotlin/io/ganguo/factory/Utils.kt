package io.ganguo.factory

import android.content.Context

/**
 * WhatApp Package Name
 */
private const val PACKAGE_WHATS_APP = "com.whatsapp"

/**
 * Telegram Package Name
 */
private const val PACKAGE_TELEGRAM = "org.telegram.messenger"

/**
 * WeChat Package Name
 */
private const val PACKAGE_WECHAT = "com.tencent.mm"

/**
 * Line Package Name
 */
private const val PACKAGE_LINE = "jp.naver.line.android"

/**
 * Twitter Package Name
 */
private const val PACKAGE_TWITTER = "com.twitter.android"

/**
 * QQ Package Name
 */
private const val PACKAGE_QQ = "com.tencent.mobileqq"

/**
 * AliPay Package Name
 */
private const val PACKAGE_ALIPAY = "com.eg.android.AlipayGphone"

/**
 * FaceBook Package Name
 */
private const val PACKAGE_FACE_BOOK = " com.facebook.katana"

/**
 * Weibo Package Name
 */
private const val PACKAGE_SINA_WB = "com.sina.weibo"


/**
 * Determine whether App is installed
 * @param context
 * @param packageName
 * @return
 */
fun isAppInstalled(context: Context, packageName: String?): Boolean {
    val pm = context.packageManager
    val piano = pm.getInstalledPackages(0)
    for (i in piano.indices) {
        if (piano[i].packageName == packageName) {
            return true
        }
    }
    return false
}


/**
 * Determine whether Telegram is installed
 * @param context
 * @return
 */
fun isTelegramInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_TELEGRAM)
}


/**
 * Determine whether Sina WeiBo is installed
 * @param context
 * @return
 */
fun isWeiBoInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_SINA_WB)
}

/**
 * Determine whether WeChat is installed
 * @param context
 * @return
 */
fun isWeChatInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_WECHAT)
}

/**
 * Determine whether WhatsApp is installed
 * @param context
 * @return
 */
fun isWhatsAppInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_WHATS_APP)
}


/**
 * Determine whether FaceBook is installed
 * @param context
 * @return
 */
fun isFaceBookInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_FACE_BOOK)
}



/**
 * Determine whether Line is installed
 * @param context
 * @return
 */
fun isLineInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_LINE)
}



/**
 * Determine whether Gmail is installed
 * @param context
 * @return
 */
fun isAliPayInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_ALIPAY)
}


/**
 * Determine whether Gmail is installed
 * @param context
 * @return
 */
fun isTwitterInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_TWITTER)
}


/**
 * Determine whether Gmail is installed
 * @param context
 * @return
 */
fun isQQInstalled(context: Context): Boolean {
    return isAppInstalled(context, PACKAGE_QQ)
}
