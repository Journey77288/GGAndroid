package io.ganguo.screen.adapter

import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/23
 *     desc   : Application Callbacks
 * </pre>
 */
internal class ScreenAppCallback(private val callback: (Configuration) -> Unit) : ComponentCallbacks {
    override fun onLowMemory() {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        callback.invoke(newConfig)
    }
}