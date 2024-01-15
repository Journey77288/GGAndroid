package io.scanner.sample.viewmodel


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import com.afollestad.assent.Permission
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.scanner.bean.CodecType
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Permissions
import io.ganguo.utils.util.requestPermissions
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.pack.common.item.ItemSampleVModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.core.ViewModelHelper
import io.reactivex.functions.Action
import io.scanner.sample.CreateCodeSampleActivity
import io.scanner.sample.R
import io.scanner.sample.ScannerSampleActivity


/**
 * <pre>
 * author : leo
 * time   : 2018/10/20
 * desc   : 扫码Demo首页
 * </pre>
 */
class MainViewModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {

    /**
     * init header
     *
     * @param container
     */
    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        val headerViewModel = HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel("扫码相关Demo"))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity))
                .build()
        ViewModelHelper.bind(container, this, headerViewModel)
    }

    override fun onViewAttached(view: View) {
        addViewModel(R.color.orange_dark_translucent, "扫码二维码(无边框)", Action { openScannerSample(CodecType.ALL.value) })
        addViewModel(R.color.blue_dark_translucent, "扫码二维码", Action { openScannerSample(CodecType.QR.value, true) })
        addViewModel(R.color.green_dark_translucent, "扫码条形码", Action { openScannerSample(CodecType.BAR.value, true) })
        addViewModel(R.color.red_dark_translucent, "生成二维码&&条形码", Action {
            val intent = Intent(context, CreateCodeSampleActivity::class.java)
            context!!.startActivity(intent)
        })
        toggleEmptyView()
    }


    /**
     * add ItemViewModel
     *
     * @param color  颜色
     * @param action 文字
     * @param text   回调接口
     */
    private fun addViewModel(@ColorRes color: Int, text: String, action: Action) {
        adapter.add(ItemSampleVModel.newItemViewModel(color, text, action))
    }


    /**
     * 打开扫码demo
     *
     * @param type 扫描类型
     */
    private fun openScannerSample(type: Int, supportScanningFrame: Boolean = false) {
        var permissions = mutableListOf<Permission>()
        permissions.addAll(Permissions.CAMERA.value)
        permissions.addAll(Permissions.STORAGE.value)
        viewInterface.activity.requestPermissions(permissions) {
            if (it) {
                val intent = ScannerSampleActivity.intentFor(context, type, supportScanningFrame)
                context!!.startActivity(intent)
            } else {
                ToastHelper.showMessage("需要相机和内存读写权限,才能获得扫码结果")
            }
        }
    }

}
