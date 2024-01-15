package io.ganguo.demo.viewmodel.activity

import android.graphics.Bitmap
import android.view.View
import io.ganguo.app.core.Config
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivitySaveViewBinding
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.*
import io.ganguo.viewmodel.pack.base.viewmodel.BaseActivityVModel
import io.ganguo.viewmodel.pack.helper.LoadingDialogHelper


/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/10/09
 *     desc   :
 * </pre>
 */

class SaveViewViewModel : BaseActivityVModel<ActivitySaveViewBinding>() {
    override val layoutId: Int by lazy {
        R.layout.activity_save_view
    }

    override fun onViewAttached(viewInterface: View) {
        initLongClick()
    }

    /**
     * function:初始化长按点击
     */
    private fun initLongClick() {
        viewInterface.binding.ivQrCode.setOnLongClickListener {
            onSave()
            true
        }
    }

    /**
     * function:保存本地
     */
    private fun onSave() {
        LoadingDialogHelper.showMaterLoading(context, "")
        doAsync {
            var bitmap = Views.createBitmapFromView(viewInterface.binding.ivQrCode)
            var file = Files.createNewFile(Config.dataPath.absolutePath, "jpg")
            Bitmaps.toFile(bitmap, file, Bitmap.CompressFormat.JPEG, 100)
            uiThread {
                LoadingDialogHelper.hideMaterLoading()
                ToastHelper.showMessage("save to ${file.absolutePath}")
            }
        }
    }

}