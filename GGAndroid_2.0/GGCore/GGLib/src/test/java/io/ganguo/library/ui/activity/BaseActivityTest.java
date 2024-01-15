package io.ganguo.library.ui.activity;

import org.junit.Test;
import org.robolectric.Robolectric;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;

/**
 * 单元测试 BaseActivity
 * Created by Lynn on 10/28/16.
 */

public class BaseActivityTest extends GGLibraryTestBase {

    @Test
    @TestDes
    public void testBaseActivity() {
        TestActivity activity = Robolectric.buildActivity(TestActivity.class).create().get();
        asNotNull(activity);
    }
}
