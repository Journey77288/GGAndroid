package io.ganguo.utils.bean;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * Created by Lynn on 10/25/16.
 */

public class BeanTest extends BaseTestCase {

    @Test
    @TestDes("test Globals")
    public void testGlobals() {
        asEquals(Globals.ERROR_MSG_UTILS_CONSTRUCTOR, "this is static util.");
        asEquals(Globals.KEY_DEVICE_ID, "key_device_id");
        asEquals(Globals.MESSENGER, "messenger");
    }
}
