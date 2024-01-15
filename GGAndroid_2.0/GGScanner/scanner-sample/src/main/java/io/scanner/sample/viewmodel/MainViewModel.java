package io.scanner.sample.viewmodel;

import android.content.Intent;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.permission.Permission;

import java.util.List;

import io.ganguo.scanner.bean.ScannerConfig;
import io.ganguo.utils.util.Permissions;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.item.ItemSampleVModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.reactivex.functions.Action;
import io.scanner.sample.CreateCodeSampleActivity;
import io.scanner.sample.R;
import io.scanner.sample.ScannerSampleActivity;

/**
 * <p>
 * Demo首页
 * </p>
 * Created by leo on 2018/8/9.
 */
public class MainViewModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {

    /**
     * <p>
     * init header
     * </p>
     */
    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel.TitleItemViewModel("扫码相关Demo"))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .build();
        ViewModelHelper.bind(container, this, headerViewModel);
    }

    @Override
    public void onViewAttached(View view) {
        addViewModel(R.color.blue_dark_translucent, "扫码二维码", () -> openScannerSample(ScannerConfig.TYPE_QR_CODE));
        addViewModel(R.color.green_dark_translucent, "扫码条形码", () -> openScannerSample(ScannerConfig.TYPE_BAR_CODE));
        addViewModel(R.color.red_dark_translucent, "生成二维码&&条形码", () -> {
            Intent intent = new Intent(getContext(), CreateCodeSampleActivity.class);
            getContext().startActivity(intent);
        });
        toggleEmptyView();
    }


    /**
     * <p>
     * add ItemViewModel
     * </p>
     *
     * @param color
     * @param action
     * @param text
     */
    protected void addViewModel(@ColorRes int color, String text, Action action) {
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel(color, text, action));
    }


    /**
     * <p>
     * 打开扫码demo
     * </p>
     */
    protected void openScannerSample(@ScannerConfig.ScanType int type) {
        Permissions.requestPermission(getContext(), "需要相机和内存读写权限,才能获得扫码结果", new Permissions.onPermissionAdapterListener() {
            @Override
            public void onRequestSuccess(List<String> successPermissions) {
                Intent intent = ScannerSampleActivity.intentFor(getContext(), type);
                getContext().startActivity(intent);
            }
        }, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA);
    }

}
