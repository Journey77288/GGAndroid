package io.ganguo.library;

import android.app.Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.ui.activity.TestActivity;

/**
 * 单元测试 AppManager
 * Created by Lynn on 11/2/16.
 */

public class AppManagerTest extends GGLibraryTestBase {
    private ActivityController mActivityController;

    @Before
    public void setUp() throws Exception {
        mActivityController = Robolectric.buildActivity(TestActivity.class).create().resume().start();
    }

    @After
    public void tearDown() throws Exception {
        mActivityController.pause().stop().destroy();
    }

    @Test
    @TestDes("测试 添加/查找 activity")
    public void testAddAndGetActivity() {
        //base activity
//        AppManager.addActivity(testActivity);
        asNotNull(AppManager.getActivity(TestActivity.class));
    }

    @Test
    @TestDes("测试 当前activity")
    public void testCurrentActivity() {
        asNotNull(AppManager.currentActivity());
        asEquals(AppManager.currentActivity().getClass(), TestActivity.class);
    }

    @Test
    @TestDes("测试 移除activity")
    public void testRemoveActivity() {
        asNotNull(AppManager.currentActivity());
        AppManager.removeActivity(AppManager.currentActivity());
        asNull(AppManager.currentActivity());
    }

    @Test
    @TestDes("测试 结束activity")
    public void testFinishActivity() {
        asNotNull(AppManager.currentActivity());
        final Activity activity = AppManager.currentActivity();
        asNotNull(activity);

        asFalse(activity.isFinishing());
        AppManager.finishActivity(activity);
        asTrue(activity.isFinishing());
    }

    @Test
    @TestDes("测试 结束所有activity")
    public void testFinishAllActivity() {
        asNotNull(AppManager.currentActivity());
        AppManager.finishAllActivity();
        asNull(AppManager.currentActivity());
    }

    @Test
    @TestDes("测试 exitByDialog")
    public void testExitByDialog() {
//        AppManager.exitByDialog(AppManager.currentActivity());
//        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
//        asNotNull(dialog);
//        asTrue(dialog.isShowing());
//        shadowOf(dialog).clickOnText(R.string.exit_sure);
    }
}
