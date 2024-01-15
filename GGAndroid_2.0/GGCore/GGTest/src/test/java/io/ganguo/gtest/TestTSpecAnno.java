package io.ganguo.gtest;

import org.junit.Test;

/**
 * 测试说明注解的输出效果
 * 输出的description 可以在Run中查看到
 * 具体的格式可以通过更改TSpecRule中的实现方法，或则自定义新的输出规则来实现
 */
public class TestTSpecAnno extends BaseTestCase {
    /**
     * 正常的输出
     * {
     * Class : TestTSpecAnno
     * Method : testTSpecDes
     * Description : 这是测试用例方法的描述, 作用于注解Test
     * }
     * Run Test Case Passed!
     *
     * @throws Exception
     */
    @Test
    @TestDes("这是测试用例方法的描述, 作用于注解Test")
    public void testTSpecDes() throws Exception {
    }

    /**
     * 异常输出
     * java.lang.Throwable:
     * {
     *      Class : TestTSpecAnno
     *      Method : testTSpecDesException
     *      Description : 这是测试用例方法的描述, 作用于注解Test, 测试出错异常
     * }
     * Run Test Case Failed!
     * 测试抛出异常 (exception message)
     * at io.ganguo.gtest.TestTSpecAnno.testTSpecDesException(TestTSpecAnno.java:17)
     * at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * ...
     * Caused by: java.lang.RuntimeException: 测试抛出异常
     * ... 30 more
     */
//    @Test
//    @TestDes("这是测试用例方法的描述, 作用于注解Test, 测试出错异常")
//    public void testTSpecDesException() throws Exception {
//        throw new RuntimeException("测试抛出异常");
//    }
}