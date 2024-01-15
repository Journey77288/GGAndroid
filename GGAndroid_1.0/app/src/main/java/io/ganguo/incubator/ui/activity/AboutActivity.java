package io.ganguo.incubator.ui.activity;

import io.ganguo.incubator.AppEnvironment;
import io.ganguo.incubator.BuildConfig;
import io.ganguo.incubator.R;
import io.ganguo.library.ui.extend.BaseActivity;
import io.ganguo.library.util.AndroidUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;
import ui.Button;

/**
 * Created by Wilson on 31/7/15.
 */
public class AboutActivity extends BaseActivity {
    private Logger logger = LoggerFactory.getLogger(AboutActivity.class);
    private Button btn_version, btn_package_id, btn_dev_mode, btn_base_url;

    @Override
    protected void beforeInitView() {

        setContentView(R.layout.activity_ggandroid_about);
        // 设置状态栏颜色
        AndroidUtils.setStatusColor(this, getResources().getColor(R.color.bg_title));
    }

    @Override
    protected void initView() {
        btn_package_id = (Button) findViewById(R.id.btn_package_id);
        btn_version = (Button) findViewById(R.id.btn_version);
        btn_dev_mode = (Button) findViewById(R.id.btn_dev_mode);
        btn_base_url = (Button) findViewById(R.id.btn_base_url);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        btn_package_id.setText("Package: " + BuildConfig.APPLICATION_ID);
        btn_version.setText("Version: " + AndroidUtils.getAppVersionName(this));
        btn_dev_mode.setText("Dev_Mode: " + AppEnvironment.DEV_MODE);
        btn_base_url.setText("Base_Url: " + AppEnvironment.BASE_URL);

        logger.v("verbose");
        logger.d("debug");
        logger.i("info");
        logger.w("warn");
        logger.e("error");
    }
}
