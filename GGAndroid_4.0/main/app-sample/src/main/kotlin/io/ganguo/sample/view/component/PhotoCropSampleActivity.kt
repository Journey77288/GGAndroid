package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.databinding.ActivityPhotoCropSampleBinding
import io.ganguo.sample.viewmodel.component.ActivityPhotoCropSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/07
 *     desc   : Photo Crop Sample
 * </pre>
 */
class PhotoCropSampleActivity : AppCoreVModelActivity<ActivityPhotoCropSampleBinding, ActivityPhotoCropSampleVModel>() {
    override fun createViewModel(): ActivityPhotoCropSampleVModel {
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Keys.Intent.Common.DATA, Uri::class.java)
        } else {
            intent.getParcelableExtra<Uri>(Keys.Intent.Common.DATA)
        }
        return ActivityPhotoCropSampleVModel(uri)
    }

    override fun onViewAttached(viewModel: ActivityPhotoCropSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context, uri: Uri) {
            val intent = Intent(context, PhotoCropSampleActivity::class.java)
                    .putExtra(Keys.Intent.Common.DATA, uri)
            context.startActivity(intent)
        }
    }
}