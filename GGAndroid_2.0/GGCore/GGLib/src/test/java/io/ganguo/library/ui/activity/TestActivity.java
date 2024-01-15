package io.ganguo.library.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * TODO 不能再测试类当中定义内部activity类, 测试过程会报错，找不到正确的constructor
 * Created by Lynn on 10/28/16.
 */

public class TestActivity extends BaseActivity {
    public boolean mIsGetResult = false;
    public static final int TEST_REQUEST_CODE = 100;
    public final String TAG = TestActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void beforeInitView() {
        Log.d(TAG, "beforeInitView");
    }

    @Override
    public void initView() {
        Log.d(TAG, "initView");
    }

    @Override
    public void initListener() {
        Log.d(TAG, "initListener");
    }

    @Override
    public void initData() {
        Log.d(TAG, "initData");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TEST_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mIsGetResult = true;
            }
        }
    }
}
