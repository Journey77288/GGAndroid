package io.scanner.sample.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.mlsdev.rximagepicker.RxImagePicker;

import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxFilter;
import io.ganguo.scanner.CodeDecodeHelper;
import io.ganguo.scanner.bean.ScannerConfig;
import io.ganguo.scanner.callback.ScanCallback;
import io.ganguo.scanner.camera.ScanCodeCamera;
import io.ganguo.scanner.view.ScanView;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.Tasks;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.library.ui.view.ActivityInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import io.scanner.sample.R;
import io.scanner.sample.databinding.ActivityScannerSampleBinding;

/**
 * <p>
 * 扫描二维码/条形码 示例Demo
 * </p>
 * Created by leo on 2018/8/8.
 */
public class ScannerSampleVModel extends BaseViewModel<ActivityInterface<ActivityScannerSampleBinding>> implements ScanCallback {
    public ObservableBoolean isOpenLight = new ObservableBoolean(false);
    public ObservableField<String> hint = new ObservableField<>();
    @ScannerConfig.ScanType
    protected int scanType;

    public ScannerSampleVModel(int scanType) {
        this.scanType = scanType;
    }


    /**
     * function: add Header
     */
    public void initHeader() {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel
                        .TitleItemViewModel("扫描二维码/条形码 Demo"))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .build();
        ViewModelHelper.bind(getView().getBinding().llyHeader, this, headerViewModel);
    }


    @Override
    public void onViewAttached(View view) {
        initHeader();
        //判断部分可以在布局中设置，此处为了方便演示Demo，放在了java代码中
        if (scanType == ScannerConfig.TYPE_BAR_CODE) {
            getScanView().setScanViewSize(getDimensionPixelOffsets(R.dimen.dp_200), getDimensionPixelOffsets(R.dimen.dp_100));
            hint.set(getStrings(R.string.str_bar_code_hint));
        } else {
            getScanView().setScanViewSize(getDimensionPixelOffsets(R.dimen.dp_200), getDimensionPixelOffsets(R.dimen.dp_200));
            hint.set(getStrings(R.string.str_qr_code_hint));
        }
        getCameraView().setScanType(scanType);
        getScanView().startScan();
    }


    /**
     * function：ZBarView
     *
     * @return
     */
    public ScanCodeCamera getCameraView() {
        return getView().getBinding().cameraView;
    }

    /**
     * function：ZBarView
     *
     * @return
     */
    public ScanView getScanView() {
        return getView().getBinding().scanView;
    }


    /**
     * function：开/关闪光灯
     *
     * @return
     */
    public View.OnClickListener onSwitchLightClick() {
        return v -> {
            if (isOpenLight.get()) {
                getCameraView().offLight();
            } else {
                getCameraView().onLight();
            }
            isOpenLight.set(!isOpenLight.get());
        };
    }

    /**
     * function：开/关闪光灯
     *
     * @return
     */
    public View.OnClickListener onToGalleryClick() {
        return v -> RxImagePicker
                .get()
                .setChooseMode(RxImagePicker.GALLERY)
                .start(getContext())
                .compose(RxFilter.filterNotNull())
                .subscribeOn(Schedulers.io())
                .map(file -> CodeDecodeHelper.decodeQRcode(file.getAbsolutePath()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(result -> handlerQRecode(result))
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "-onToGalleryClick-"));
    }

    /**
     * function：处理解析成功的二维码数据
     *
     * @param result
     */
    protected void handlerQRecode(String result) {
        String data = result;
        onResumeStartScan();
        if (Strings.isEmpty(data)) {
            data = "没有找到二维码";
            ToastHelper.showMessage( data);
            return;
        }
        //扫描成功后，可以在这里处理数据、添加振动、音频反馈等反馈
        ToastHelper.showMessage( data);
    }


    /**
     * function：延时两秒，恢复扫描
     */
    protected void onResumeStartScan() {
        Tasks.handler().postDelayed(() -> startScan(), 2000);
    }


    @Override
    public int getItemLayoutId() {
        return R.layout.activity_scanner_sample;
    }

    @Override
    public void onDestroy() {
        stopScan();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        pauseScan();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        startScan();
    }

    /**
     * function：开始扫描
     */
    protected void startScan() {
        if (!isAttach()) {
            return;
        }
        getCameraView().setScanCallback(this);
        getCameraView().start();
        getScanView().onResume();
    }

    /**
     * function：停止扫描
     */
    protected void pauseScan() {
        if (!isAttach()) {
            return;
        }
        getCameraView().offLight();
        getCameraView().pauseCamera();
        getScanView().onPause();
    }


    /**
     * function：停止扫描
     */
    protected void stopScan() {
        if (!isAttach()) {
            return;
        }
        getCameraView().offLight();
        getCameraView().stopCamera();
        getScanView().onPause();
    }


    @Override
    public void onScanResult(String content) {
        if (getCameraView() != null) {
            getCameraView().offLight();
        }
        handlerQRecode(content);
    }
}