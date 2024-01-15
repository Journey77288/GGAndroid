package io.ganguo.library.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.junit.Test;
import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;

import static org.robolectric.Shadows.shadowOf;


/**
 * 单元测试 BaseService
 * Created by Lynn on 10/28/16.
 */

public class BaseServiceTest extends GGLibraryTestBase {
    @Test
    @TestDes("test BaseService")
    public void testBaseService() {
        Intent intent = new Intent(getApplicationContext(), TestService.class);
        getApplicationContext().startService(intent);

        Intent resIntent = shadowOf(getApplicationContext()).getNextStartedService();
        asNotNull(resIntent);
        asEquals(intent.getComponent(), resIntent.getComponent());

        getApplicationContext().stopService(intent);
        Intent stopIntent = shadowOf(getApplicationContext()).getNextStoppedService();
        asNotNull(stopIntent);
        asEquals(intent.getComponent(), stopIntent.getComponent());
    }

    class TestService extends BaseService {
        private static final String TAG = "TestService";

        @Override
        public void onCreate() {
            super.onCreate();
            Log.d(TAG, "onCreate");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy");
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
