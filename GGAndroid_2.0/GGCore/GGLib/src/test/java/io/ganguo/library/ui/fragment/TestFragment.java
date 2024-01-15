package io.ganguo.library.ui.fragment;

import android.util.Log;

import io.ganguo.library.R;

/**
 * 继承BaseFragment, 用于测试
 * Created by Lynn on 11/2/16.
 */

public class TestFragment extends BaseFragment {
    private static final String TAG = TestFragment.class.getSimpleName();

    public TestFragment() {
        Log.d(TAG, "TestFragment constructor method called");
    }

    @Override
    public int getLayoutResourceId() {
        Log.d(TAG, "TestFragment getLayoutResourceId method called");
        //TODO TEST ONLY
        return R.layout.fragment_list;
    }

    @Override
    public void initView() {
        Log.d(TAG, "TestFragment initView method called");

    }

    @Override
    public void initListener() {
        Log.d(TAG, "TestFragment initListener method called");

    }

    @Override
    public void initData() {
        Log.d(TAG, "TestFragment initData method called");

    }
}
