package io.ganguo.library.core;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.library.core.container.Property2;

/**
 * 单元测试 Property2
 * Created by Lynn on 11/3/16.
 */

public class Property2Test extends BaseTestCase {
    @Test
    @TestDes("test Property2")
    public void testProperty2() {
        Property2<Integer, String> testProperty1 = new Property2<>();
        asFalse(testProperty1.isValid());
        testProperty1.setFirst(1);
        asFalse(testProperty1.isValid());
        testProperty1.setSecond("test");
        asTrue(testProperty1.isValid());

        asEquals((int) testProperty1.getFirst(), 1);
        asEquals(testProperty1.getSecond(), "test");

        Property2<String, Boolean> testProperty2 = new Property2<>("test2", true);
        asTrue(testProperty2.isValid());
        testProperty2.setFirst(null);
        asFalse(testProperty2.isValid());

        asNull(testProperty2.getFirst());
        asTrue(testProperty2.getSecond());
    }
}
