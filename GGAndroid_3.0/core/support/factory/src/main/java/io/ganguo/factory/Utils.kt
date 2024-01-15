package io.ganguo.factory

import android.content.Context

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/10
 *     desc   : 工具集合
 * </pre>
 */


/**
 * 判断App是否安装
 *
 * @param context     上下文
 * @param packageName 包名
 * @return
 */
fun isAppInstalled(context: Context, packageName: String?): Boolean {
    val pm = context.packageManager
    val pinfo = pm.getInstalledPackages(0)
    for (i in pinfo.indices) {
        if (pinfo[i].packageName == packageName) {
            return true
        }
    }
    return false
}
