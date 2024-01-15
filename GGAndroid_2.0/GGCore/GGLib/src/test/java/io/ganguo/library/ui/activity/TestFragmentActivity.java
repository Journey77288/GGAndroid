package io.ganguo.library.ui.activity;

import android.util.Log;

/**
 * 单元测试 extends BaseFragmentActivity
 * Created by Lynn on 11/2/16.
 */

public class TestFragmentActivity extends BaseFragmentActivity {
    private static final String TAG = TestFragmentActivity.class.getSimpleName();

    @Override
    public void beforeInitView() {
        //TODO test only
        Log.d(TAG, "beforeInitView called");
    }

    @Override
    public void initView() {
        Log.d(TAG, "initView called");

    }

    @Override
    public void initListener() {
        Log.d(TAG, "initListener called");

    }

    @Override
    public void initData() {
        Log.d(TAG, "initData called");

    }
}
