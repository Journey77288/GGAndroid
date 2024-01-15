package io.ganguo.factory.result

import android.app.Activity
import android.content.Intent

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : ActivityResult 接口回调
 * </pre>
 */
interface IActivityResult {
    /**
     * 注册ActivityResult回调
     * @param requestCode Int 请求code
     * @param resultCode Int 返回值
     * @param data Intent?
     * @param activity Activity?
     */
    fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity ?= null)

}