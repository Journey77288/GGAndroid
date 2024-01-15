package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.component.exhibitionState.ActivityExhibitionStateSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Exhibition State Sample
 * </pre>
 */
class ExhibitionStateSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityExhibitionStateSampleVModel>() {
    override fun createViewModel(): ActivityExhibitionStateSampleVModel {
        return ActivityExhibitionStateSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityExhibitionStateSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ExhibitionStateSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}