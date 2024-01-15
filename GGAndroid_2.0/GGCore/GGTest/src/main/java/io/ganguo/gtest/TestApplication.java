package io.ganguo.gtest;

import android.app.Application;
import android.util.Log;

/**
 * 测试专用 application
 * Created by Lynn on 10/27/16.
 */

public class TestApplication extends Application {
    private String TAG = TestApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
}
