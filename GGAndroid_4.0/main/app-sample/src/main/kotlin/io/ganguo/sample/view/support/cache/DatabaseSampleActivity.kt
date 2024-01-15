package io.ganguo.sample.view.support.cache

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.cache.ActivityDatabaseSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Database Sample
 * </pre>
 */
class DatabaseSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityDatabaseSampleVModel>() {
    override fun createViewModel(): ActivityDatabaseSampleVModel {
        return ActivityDatabaseSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityDatabaseSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, DatabaseSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}