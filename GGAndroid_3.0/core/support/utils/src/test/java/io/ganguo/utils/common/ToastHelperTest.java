package io.ganguo.utils.common;

import org.junit.Test;
import org.robolectric.shadows.ShadowToast;

import io.ganguo.gtest.AndroidTestCase;


import io.ganguo.gtest.TestDes;
import io.ganguo.utils.helper.ToastHelper;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * 单元测试 ToastHelper
 * Created by Lynn on 10/27/16.
 */

//TODO AndroidTestCase
public class ToastHelperTest extends AndroidTestCase {
    public static String TEST_MESSAGE = "test_message";

    @Test
    @TestDes("test showMessage方法")
    public void testShowMessage() {
        ToastHelper.showMessage( TEST_MESSAGE);
        asThat(ShadowToast.getTextOfLatestToast(), equalTo(TEST_MESSAGE));
    }

    @Test
    @TestDes("test showMessageMiddle")
    public void testShowMessageMiddle() {
        ToastHelper.showMessageMiddle( TEST_MESSAGE);
        asThat(ShadowToast.getTextOfLatestToast(), equalTo(TEST_MESSAGE));
    }

    @Test
    @TestDes("test showMessage(Context context, int resId)方法")
    public void testShowMessageRes() {
//        String resString = getApplicationContext().getString(R.string.app_name);
//        ToastHelper.showMessage(getApplicationContext(), R.string.app_name);
//        asThat(ShadowToast.getTextOfLatestToast(), equalTo(resString));
    }
}
