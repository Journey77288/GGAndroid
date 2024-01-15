package io.ganguo.library.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import io.ganguo.library.R;
import io.ganguo.utils.AppManager;
import io.ganguo.utils.common.ScreenAdaptors;
import io.ganguo.utils.common.UIHelper;


/**
 * Activity - 基类
 * 用于继承使用
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public abstract class BaseActivity extends Activity implements InitResources {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // register
        AppManager.addActivity(this);
        ScreenAdaptors.onBindScreenAdapter(this);
        // init resources
        beforeInitView();
        initView();
        initListener();
        initData();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // bind back action
        UIHelper.bindActionBack(this, findViewById(R.id.action_back));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister
        AppManager.removeActivity(this);
    }
}
