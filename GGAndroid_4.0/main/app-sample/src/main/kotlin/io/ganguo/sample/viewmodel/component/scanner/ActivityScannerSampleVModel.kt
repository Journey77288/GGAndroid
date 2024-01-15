package io.ganguo.sample.viewmodel.component.scanner

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.blankj.utilcode.util.BarUtils
import io.ganguo.appcompat.Toasts
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.permission.requestImageMediaPermission
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ActivityScannerSampleBinding
import io.ganguo.scanner.bean.CodecType
import io.ganguo.scanner.helper.DecodeHelper
import io.ganguo.scanner.view.ScanCameraView
import io.ganguo.scanner.view.ScanFrameView
import io.ganguo.scanner.view.ScanLineView
import io.ganguo.single.ImageChooser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Scanner Sample
 * </pre>
 */
class ActivityScannerSampleVModel(private val scanType: Int, private val supportScanningFrame: Boolean)
    : ViewModel<ActivityInterface<ActivityScannerSampleBinding>>() {
    override val layoutId: Int = R.layout.activity_scanner_sample
    private val scannerView: ScanFrameView by lazy { viewIF.binding.scannerView }
    private val lineView: ScanLineView by lazy { viewIF.binding.lineView }
    private val cameraView: ScanCameraView by lazy { viewIF.binding.cameraView }
    val isFlashLightOn = ObservableBoolean(false)

    override fun onViewAttached(view: View) {
        initHeader()
        initScanView()
        initCameraView()
    }

    /**
     * 配置标题栏
     */
    private fun initHeader() {
        val title = if (isScanQrCode()) {
            getString(R.string.str_qr_code_scanner_sample)
        } else {
            getString(R.string.str_bar_code_scanner_sample)
        }
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, title)
                .apply {
                    backgroundColorRes(io.ganguo.resources.R.color.transparent)
                    marginTop(BarUtils.getStatusBarHeight())
                    appendRightItem(createAlbumButtonVModel())
                }
                .let {
                    ViewModelHelper.bind(viewIF.binding.flyHeader, this, it)
                }
    }

    /**
     * 创建相册标题按钮ViewModel
     *
     * @return TextViewModel
     */
    private fun createAlbumButtonVModel(): TextViewModel = let {
        TextViewModel()
                .also {
                    it.gravity(Gravity.CENTER)
                    it.text(getString(R.string.str_album))
                    it.width(ViewGroup.LayoutParams.WRAP_CONTENT)
                    it.height(ViewGroup.LayoutParams.MATCH_PARENT)
                    it.paddingHorizontalRes(io.ganguo.resources.R.dimen.dp_15)
                    it.textSizeRes(io.ganguo.resources.R.dimen.font_14)
                    it.textColorRes(io.ganguo.resources.R.color.white)
                    it.click {
                        viewIF.activity.requestImageMediaPermission {
                            if (it.success) {
                                pickAlbumForScan()
                            } else {
                                Toasts.show(R.string.str_get_permission_fail)
                            }
                        }
                    }
                }
    }

    /**
     * 扫描相册中选中的二维码图片
     */
    private fun pickAlbumForScan() {
        ImageChooser.pickPhoto(context)
                .map {
                    if (isScanQrCode()) {
                        DecodeHelper.decodingQrCode(it).orEmpty()
                    } else {
                        DecodeHelper.decodingBarCode(it)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Toasts.show(it.orEmpty()) }
                .subscribe(Functions.emptyConsumer(), printThrowable("--pickAlbumForScan--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 判断扫描的是否为二维码
     *
     * @return Boolean
     */
    private fun isScanQrCode(): Boolean = let {
        scanType == CodecType.QR.value
    }

    /**
     * 初始化扫码控件
     */
    private fun initScanView() {
        if (isScanQrCode()) {
            initQrScanView()
        } else {
            initBarScanView()
        }
        initScanLine()
    }

    /**
     * 初始化条形码扫码控件
     */
    private fun initBarScanView() {
        val width = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_266)
        val height = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_133)
        scannerView.setScanningFrameSize(width, height)
        lineView.scanningRange = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_120)
        lineView.scanningSpeed = 2000
    }

    /**
     * 初始化二维码扫码控件
     */
    private fun initQrScanView() {
        val size = getDimensionPixelSize(io.ganguo.resources.R.dimen.dp_266)
        scannerView.setScanningFrameSize(size, size)
        lineView.scanningRange = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_250)
    }

    /**
     * 初始化扫码线
     */
    private fun initScanLine() {
        // 不支持扫码框时，隐藏扫码框。实际使用，不需要显示扫码框，扫码框控件不添加到布局即可
        if (!supportScanningFrame) {
            scannerView.visibility = View.GONE
        }
        // 扫描线控件必须要绑定ViewModel生命周期
        lineView.setLifecycleOwner(lifecycleOwner)
    }

    /**
     * 初始化相机控件
     */
    private fun initCameraView() {
        cameraView.playSounds = false
        cameraView.startScan(lifecycleOwner)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Toasts.show(it) }
                .subscribe(Functions.emptyConsumer(), printThrowable("--initCameraView--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 点击手电筒开关按钮
     *
     * @param view
     */
    fun actionSwitchFlashlight(view: View) {
        cameraView.switchTorch()
        isFlashLightOn.set(cameraView.isOnTorch())
    }

    override fun onDestroy() {
        cameraView.release()
        super.onDestroy()
    }
}