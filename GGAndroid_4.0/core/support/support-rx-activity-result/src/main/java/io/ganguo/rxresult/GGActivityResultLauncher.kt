package io.ganguo.rxresult

import android.app.Activity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/04/29
 *     desc   : Activity Result API 封装
 *              注意Launcher必须在生命周期onStarted前进行注册，不然调起Launcher会报错
 * </pre>
 * @param [caller] Activity Result的调起组件，为Activity或Fragment
 * @param [contract] Activity Result合约，一般可从ActivityResultContracts中获取
 */
class GGActivityResultLauncher<I, O>(caller: ActivityResultCaller, contract: ActivityResultContract<I, O>) {
    private var launcher: ActivityResultLauncher<I>
    private var callback: ActivityResultCallback<O> ?= null

    init {
        launcher = caller.registerForActivityResult(contract) { result ->
            callback?.onActivityResult(result)
        }
    }

    fun launch(input: I, callback: ActivityResultCallback<O>) {
        launch(input, null, callback)
    }

    fun launch(input: I, options: ActivityOptionsCompat?, callback: ActivityResultCallback<O>) {
        this.callback = callback
        launcher.launch(input, options)
    }
}