package io.ganguo.demo.view.activity


import android.content.Intent
import io.ganguo.core.ui.activity.BaseSplashActivity

/**
 *
 *
 * SplashActivity 启动页
 *
 * Created by leo on 2018/8/31.
 */
class SplashActivity : BaseSplashActivity() {

    override fun delayMilliseconds(): Long {
        return 500
    }

    /**
     * 跳转到首页
     */
    override fun starNextPage() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}
