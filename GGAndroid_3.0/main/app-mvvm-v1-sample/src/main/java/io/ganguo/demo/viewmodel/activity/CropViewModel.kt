package io.ganguo.demo.viewmodel.activity

import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.BitmapFactory
import android.view.View
import io.ganguo.app.core.Config
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityCropBinding
import io.ganguo.utils.util.Files
import io.ganguo.utils.util.doAsync
import io.ganguo.utils.util.uiThread
import io.ganguo.viewmodel.pack.base.viewmodel.BaseActivityVModel
import io.ganguo.viewmodel.pack.helper.LoadingDialogHelper

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/09/25
 *     desc   : 裁剪页面
 * </pre>
 */

class CropViewModel(var path: String) : BaseActivityVModel<ActivityCropBinding>() {
    override val layoutId: Int by lazy {
        R.layout.activity_crop
    }
    private lateinit var tempPath: String

    override fun onViewAttached(view: View) {
        initData()
    }

    private fun initData() {
        //异步Load图会出现加载延迟现象，所以这里使用同步
        var bitmap = BitmapFactory.decodeFile(path)
        viewInterface.binding.cropView.imageBitmap = bitmap
    }

    /**
     * function:确定
     */
    fun actionCrop() {
        LoadingDialogHelper.showMaterLoading(context, R.string.loading)
        var file = Files.createNewFile(Config.imageCachePath.absolutePath)
        tempPath = file.absolutePath
        doAsync {
            viewInterface.binding.cropView.extensions()
                    .crop()
                    .quality(100)
                    .format(JPEG)
                    .into(file)
                    .get()
            uiThread {
                path = tempPath
                initData()
                LoadingDialogHelper.hideMaterLoading()
            }
        }
    }

    /**
     * function:取消
     */
    fun actionBack() {
        viewInterface.activity.finish()
    }


}