package io.ganguo.app.ui.activity

import android.app.Activity
import android.os.Build


/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/02
 *     desc   : Splash Base Class
 * </pre>
 */
abstract class BaseSplashActivity : BaseActivity() {
    override fun beforeInitView() {
    }

    override fun initListener() {
    }


    override fun initView() {
    }

    override fun initData() {
        startDelayNextPage()
    }

    /**
     * 跳转到下一页
     */
    private fun startDelayNextPage() {
        window.decorView.postDelayed({
            starNextPage()
            finish()
        }, delayMilliseconds())
    }


    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, android.R.anim.fade_out)
        } else {
            overridePendingTransition(0, android.R.anim.fade_out)
        }
    }

    /**
     * 延时跳转页面时长
     * @return Long
     */
    abstract fun delayMilliseconds(): Long


    /**
     * 在这里做判断逻辑，跳转到对应的页面，主页/或者其他
     */
    abstract fun starNextPage()
}
