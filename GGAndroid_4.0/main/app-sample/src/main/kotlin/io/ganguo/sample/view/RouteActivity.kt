package io.ganguo.sample.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.app.ui.activity.BaseActivity
import io.ganguo.sample.bean.Keys.RxBus.Common.INTENT_DATA_KEY
import io.ganguo.sample.enum.ActionUri

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/03/26
 *     desc   : 跳转App内部路由页
 * </pre>
 */
class RouteActivity : BaseActivity() {

    override fun beforeInitView() {

    }

    override fun initView() {
        intentForScheme()
        finish()
    }

    private fun intentForScheme() {
        intent ?: return
        val data = intent.getStringExtra(INTENT_DATA_KEY)
        val uri = if (data.isNullOrBlank()) {
            intent.data
        } else {
            Uri.parse(data)
        }
        val action = uri?.getQueryParameter(SCHEME_URI)
        if (action.isNullOrBlank() || !isCertifiedAction(action)) {
            return
        }
        intent.action = action
        intent.component = null
        intent.selector = null
        when(action) {
            ActionUri.SPLASH.uri -> {
                SplashActivity.start(this)
            }
            else -> {
                SampleActivity.start(this)
                try {
                    this.startActivityIfNeeded(intent, -1)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 校验链接的action是否合法
     *
     * @param action String
     * @return Boolean
     */
    private fun isCertifiedAction(action: String): Boolean {
        if (action.isNullOrBlank()) {
            return false
        }
        for (item in ActionUri.values()) {
            if (item.uri == action) return true
        }
        return false
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    companion object {
        const val SCHEME_URI = "uri"

        @JvmStatic
        fun intentFor(context: Context, action: String): Intent {
            val intent = Intent(context, RouteActivity::class.java)
            intent.putExtra(INTENT_DATA_KEY, action)
            return intent
        }
    }
}