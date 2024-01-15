package io.ganguo.incubator.view.general.activity

import android.content.Intent
import io.ganguo.core.ui.activity.BaseSplashActivity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/02
 *     desc   : 闪屏页
 * </pre>
 */
class SplashActivity : BaseSplashActivity() {
    override fun delayMilliseconds(): Long {
        return 500
    }

    override fun starNextPage() {
        startActivity(Intent(this, ApkInfoActivity::class.java))
    }


}