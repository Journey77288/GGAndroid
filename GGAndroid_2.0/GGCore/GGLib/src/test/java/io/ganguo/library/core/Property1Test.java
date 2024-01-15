package io.ganguo.library.core;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.library.core.container.Property1;

/**
 * 单元测试 Property1
 * Created by Lynn on 11/3/16.
 */

public class Property1Test extends BaseTestCase {
    @Test
    @TestDes("test Property1")
    public void testProperty1() {
        Property1<Integer> testProperty1 = new Property1<>();
        asFalse(testProperty1.isValid());
        testProperty1.set(1);
        asTrue(testProperty1.isValid());
        asEquals((int) testProperty1.get(), 1);

        Property1<String> testProperty2 = new Property1<>("test");
        asTrue(testProperty2.isValid());
        asNotNull(testProperty2.get());
        asEquals(testProperty2.get(), "test");
    }
}
