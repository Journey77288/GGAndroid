package io.ganguo.rxresult

import android.app.Activity
import android.content.Intent


/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/29
 *     desc   : ActivityResult Container
 * </pre>
 */
class ActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?) {
    val isResultOK: Boolean
        get() = resultCode == Activity.RESULT_OK

    override fun toString(): String {
        return "ActivityResult{" +
                "requestCode=" + requestCode +
                ", resultCode=" + resultCode +
                ", data=" + data +
                '}'
    }

}