package io.ganguo.library.core;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.library.core.container.Property3;

/**
 * 单元测试 Property3
 * Created by Lynn on 11/3/16.
 */

public class Property3Test extends BaseTestCase {
    @Test
    @TestDes("test Property3")
    public void testProperty3() {
        Property3<Integer, String, Boolean> testProperty1 = new Property3<>();
        asFalse(testProperty1.isValid());

        testProperty1.setFirst(1);
        testProperty1.setSecond("test");
        testProperty1.setThird(true);

        asEquals(testProperty1.getFirst().getClass(), Integer.class);
        asEquals(testProperty1.getSecond().getClass(), String.class);
        asEquals(testProperty1.getThird().getClass(), Boolean.class);

        asEquals((int) testProperty1.getFirst(), 1);
        asEquals(testProperty1.getSecond(), "test");
        asTrue(testProperty1.getThird());
    }
}
