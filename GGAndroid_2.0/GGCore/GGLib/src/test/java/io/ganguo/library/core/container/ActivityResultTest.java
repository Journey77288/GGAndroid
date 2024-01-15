package io.ganguo.library.core.container;

import android.content.Intent;

import org.junit.Test;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;

/**
 * 单元测试 ActivityResult
 * Created by Lynn on 11/3/16.
 */

public class ActivityResultTest extends GGLibraryTestBase {
    @Test
    @TestDes("test ActivityResult Object")
    public void testActivityResult() {
        String testKey = "test_key";
        String testValue = "test_value";

        int resultCode = 100;
        int requestCode = 200;
        Intent intent = new Intent();
        intent.putExtra(testKey, testValue);

        ActivityResult activityResult = new ActivityResult(requestCode, resultCode, intent);

        asEquals(activityResult.getRequestCode(), requestCode);
        asEquals(activityResult.getResultCode(), resultCode);
        asNotNull(activityResult.getData());
        asEquals(testValue, activityResult.getData().getStringExtra(testKey));
    }
}
