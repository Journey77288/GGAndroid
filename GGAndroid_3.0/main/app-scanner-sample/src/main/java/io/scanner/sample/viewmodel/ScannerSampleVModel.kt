package io.scanner.sample.viewmodel

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.rx.RxActions
import io.ganguo.rximagepicker2.RxImagePicker
import io.ganguo.rximagepicker2.RxImagePickerMode
import io.ganguo.scanner.bean.CodecType
import io.ganguo.scanner.view.ScanCameraView
import io.ganguo.scanner.helper.DecodeHelper
import io.ganguo.scanner.view.ScanFrameView
import io.ganguo.scanner.view.ScanLineView
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.requestStoragePermissions
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.viewmodel.pack.common.ImageViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import io.scanner.sample.R
import io.scanner.sample.databinding.ActivityScannerBinding

/**
 * 扫描二维码/条形码 示例Demo
 * Created by leo on 2018/8/8.
 * @property scanType 扫描类型
 * @property supportScanningFrame 是否需要支持扫描框
 */
class ScannerSampleVModel(var scanType: Int, var supportScanningFrame: Boolean) : BaseViewModel<ActivityInterface<ActivityScannerBinding>>() {
    var isOnLight = ObservableBoolean(false)
    var hint = ObservableField<String>()
    private val cameraView: ScanCameraView by lazy {
        viewInterface.binding.scanCamera
    }
    private val scanFrameView: ScanFrameView by lazy {
        viewInterface.binding.scanView
    }
    private val lineView: ScanLineView by lazy {
        viewInterface.binding.lineView
    }


    override fun onViewAttached(view: View) {
        initHeader()
        initView()
        initScanCamera()
    }

    /**
     * init Title Header
     */
    private fun initHeader() {
        val imageViewModel = ImageViewModel.Builder()
                .background(R.drawable.ic_close)
                .marginTop(R.dimen.dp_13)
                .marginLeft(R.dimen.dp_15)
                .onClickListener {
                    viewInterface.activity.finish()
                }
                .build()
        ViewModelHelper.bind(viewInterface.binding.llyHeader, this, imageViewModel)
    }


    /**
     * View初始化配置
     */
    private fun initView() {
        if (scanType == CodecType.BAR.value) {
            settingBarCodeMode()
        } else {
            settingQrCodeMode()
        }
        settingScanFrameView()

    }


    /**
     * 设置扫描框View样式、显示状态配置
     */
    private fun settingScanFrameView() {
        //配置不支持扫描框时，则隐藏扫描框。 实际使用时，不需要显示时，不添加scanFrameView到布局即可.
        if (!supportScanningFrame) {
            scanFrameView.visibility = View.GONE
            lineView.scanningRange = getDimensionPixelOffset(R.dimen.dp_250)
        }
        //Must be binding
        lineView.setLifecycleOwner(this)
    }


    /**
     * 设置条形码扫描模式
     */
    private fun settingBarCodeMode() {
        val width = getDimensionPixelOffset(R.dimen.dp_266)
        val height = getDimensionPixelOffset(R.dimen.dp_133)
        scanFrameView.setScanningFrameSize(width, height)
        lineView.scanningRange = getDimensionPixelOffset(R.dimen.dp_120)
        lineView.scanningSpeed = 2000
        hint.set(getString(R.string.str_bar_code_hint))
    }

    /**
     * 设置二维码扫描模式
     */
    private fun settingQrCodeMode() {
        var size = getDimensionPixelOffset(R.dimen.dp_266)
        scanFrameView.setScanningFrameSize(size, size)
        lineView.scanningRange = getDimensionPixelOffset(R.dimen.dp_250)
        hint.set(getString(R.string.str_qr_code_hint))
    }


    /**
     * 切换前后摄像头
     * @return View.OnClickListener
     */
    fun actionSwitchFacing(): View.OnClickListener {
        return View.OnClickListener {
            cameraView.switchFacing()
        }
    }

    /**
     * 开/关闪光灯
     *
     * @return
     */
    fun actionSwitchTorch(): View.OnClickListener {
        return View.OnClickListener {
            cameraView.switchTorch()
            isOnLight.set(cameraView.isOnTorch())
        }
    }

    /**
     * 从图库中选中图片，获取扫码结果
     * @return
     */
    fun actionCodeFromGallery(): View.OnClickListener {
        return View.OnClickListener {
            viewInterface.activity.requestStoragePermissions {
                RxImagePicker
                        .get()
                        .pickerMode(RxImagePickerMode.PHOTO_PICK)
                        .start(context)
                        .subscribeOn(Schedulers.io())
                        .map { DecodeHelper.decodingQrCode(it) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext { ToastHelper.showMessage(it) }
                        .compose(RxVMLifecycle.bindViewModel(this))
                        .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(javaClass.simpleName + "-onToGalleryClick-"))
            }
        }
    }

    /**
     * 初始化扫码相机
     */
    private fun initScanCamera() {
        cameraView.playSounds = false
        cameraView.startScan(this)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    ToastHelper.showMessage(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--startScan--"))
    }


    override val layoutId: Int = R.layout.activity_scanner

}