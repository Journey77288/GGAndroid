package io.ganguo.core.ui.activity

import io.ganguo.utils.util.postDelayed


/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/02
 *     desc   : 闪屏页 - 基类
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
        postDelayed(delayMilliseconds()) {
            starNextPage()
            finish()
        }
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(0, android.R.anim.fade_out)
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