package io.scanner.sample;

import android.content.Context;
import android.content.Intent;

import io.ganguo.library.bean.Constants;
import io.ganguo.scanner.bean.ScannerConfig;
import io.ganguo.vmodel.ViewModelActivity;
import io.scanner.sample.databinding.ActivityScannerSampleBinding;
import io.scanner.sample.viewmodel.ScannerSampleVModel;

/**
 * <p>
 * 扫描二维码/条形码 示例Demo
 * </p>
 * Created by leo on 2018/8/8.
 */
public class ScannerSampleActivity extends ViewModelActivity<ActivityScannerSampleBinding, ScannerSampleVModel> {
    /**
     * function：create Intent
     *
     * @param context
     * @param type
     * @return
     */
    public static Intent intentFor(Context context, @ScannerConfig.ScanType int type) {
        Intent intent = new Intent(context, ScannerSampleActivity.class);
        intent.putExtra(Constants.KEY_DATA, type);
        return intent;
    }

    @Override
    public ScannerSampleVModel createViewModel() {
        @ScannerConfig.ScanType
        int type = getIntent().getIntExtra(Constants.KEY_DATA, ScannerConfig.TYPE_ALL);
        return new ScannerSampleVModel(type);
    }

    @Override
    public void onViewAttached(ScannerSampleVModel viewModel) {

    }
}
