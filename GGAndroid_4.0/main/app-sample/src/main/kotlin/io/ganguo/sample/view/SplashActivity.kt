package io.ganguo.sample.view

import android.content.Context
import android.content.Intent
import io.ganguo.app.ui.activity.BaseSplashActivity
import io.ganguo.screen.port.IScreenConfigNeglect

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/02
 *     desc   : 闪屏
 * </pre>
 */
class SplashActivity : BaseSplashActivity() {

    override fun delayMilliseconds(): Long {
        return 300
    }

    override fun starNextPage() {
        val intent = Intent(this, SampleActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, SplashActivity::class.java))
        }
    }
}
