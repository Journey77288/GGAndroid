package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.databinding.ActivitySideBarSampleBinding
import io.ganguo.sample.viewmodel.component.ActivitySideBarSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/20
 *     desc   : SideBar Sample
 * </pre>
 */
class SideBarSampleActivity : AppCoreVModelActivity<ActivitySideBarSampleBinding, ActivitySideBarSampleVModel>() {
    override fun createViewModel(): ActivitySideBarSampleVModel {
        val sideBarVisible = intent.getBooleanExtra(Keys.Intent.Common.FLAG, false)
        return ActivitySideBarSampleVModel(sideBarVisible)
    }

    override fun onViewAttached(viewModel: ActivitySideBarSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context, sideBarVisible: Boolean) {
            val intent = Intent(context, SideBarSampleActivity::class.java)
            intent.putExtra(Keys.Intent.Common.FLAG, sideBarVisible)
            context.startActivity(intent)
        }
    }
}