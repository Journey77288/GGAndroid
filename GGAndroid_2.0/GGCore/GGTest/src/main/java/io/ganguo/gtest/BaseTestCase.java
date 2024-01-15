package io.ganguo.gtest;

import android.util.Log;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * 测试用例 - base case class
 * 自定义的测试用例class 应该继承它
 * 尽量保证GTest module的依赖简单
 * Created by Lynn on 10/21/16.
 */

public class BaseTestCase {
    /**
     * 自定义Rule的使用方法，
     * 创建对应Rule的实例，
     * 测试用例运行的时候，
     * 会自动根据annotation调用对应Rule的apply方法
     **/
    @Rule
    public TestDesRule mDesRule = new TestDesRule();

    /**
     * Mockito的Rule
     * 使用相关的Mock方法前
     * 需要用Rule把Mockito框架注入
     * <p>
     * TODO Mockito本身不支持静态或者私有的方法，成员
     * 如果需要mock静态或私有, 要使用反射或者使用PowerMockito
     */
    @Rule
    public MockitoRule mMockRule = MockitoJUnit.rule();

    /**
     * 用于检测抛出的异常是否符合预期
     * 还可以设定参数来验证抛出异常的其他信息是否正确
     * TODO {@link //io.ganguo.utils.exception.ExceptionsTest}
     */
    @Rule
    public ExpectedException mCheckExpRule = ExpectedException.none();

    /**
     * 断言assert 在输入不满足规则时会报错， 测试通过失败
     * 封装常用的断言
     */

    /**
     * 判断两个整型是否相同
     *
     * @param msg 额外的输出信息
     * @param i1
     * @param i2
     */
    protected void asEquals(String msg, int i1, int i2) {
        assertEquals(msg, i1, i2);
    }

    /**
     * 重载方法
     * 判断两个整型是否相同
     *
     * @param i1
     * @param i2
     */
    protected void asEquals(int i1, int i2) {
        assertEquals(i1, i2);
    }

    /**
     * 重载方法
     * 判断两个字符串是否相同
     *
     * @param str1
     * @param str2
     */
    protected void asEquals(String str1, String str2) {
        assertEquals(str1, str2);
    }

    /**
     * 重载方法
     * 判断两个浮点数是否相同
     *
     * @param f1
     * @param f2
     */
    protected void asEquals(float f1, float f2) {
        //后一位表示相同的精度 Math.abs(a - b) <= 0.0000000001, 则可以判断相同
        asEquals(f1, f2, 10e-1f);
    }

    /**
     * 重载方法
     * 判断两个浮点数是否相同
     * TODO 因为使用较少，没有考虑double的精度情况
     *
     * @param f1
     * @param f2
     */
    protected void asEquals(float f1, float f2, float delta) {
        assertEquals(f1, f2, delta);
    }

    /**
     * 重载方法
     * 判断两个对象是否相同
     *
     * @param o1
     * @param o2
     */
    protected void asEquals(Object o1, Object o2) {
        assertEquals(o1, o2);
    }

    /**
     * 判断输入是否为空
     *
     * @param o
     */
    protected void asNull(Object o) {
        assertNull(o);
    }

    /**
     * 判断输入是否不为空
     *
     * @param o
     */
    protected void asNotNull(Object o) {
        assertNotNull(o);
    }

    /**
     * 判断两个对象是否为同一个类的实例
     *
     * @param o1
     * @param o2
     */
    protected void asSameClass(Object o1, Object o2) {
        asEquals(o1.getClass(), o2.getClass());
    }

    /**
     * 判断输入是否为 true
     *
     * @param msg    额外输出信息
     * @param isTrue
     */
    protected void asTrue(String msg, boolean isTrue) {
        assertTrue(msg, isTrue);
    }

    /**
     * 重载方法
     * 判断输入是否为 true
     *
     * @param isTrue
     */
    protected void asTrue(boolean isTrue) {
        assertTrue(isTrue);
    }

    /**
     * 判断输入是否为 false
     *
     * @param isFalse
     */
    protected void asFalse(String msg, boolean isFalse) {
        assertFalse(msg, isFalse);
    }

    /**
     * 重载方法
     * 判断输入是否为 false
     *
     * @param isFalse
     */
    protected void asFalse(boolean isFalse) {
        assertFalse(isFalse);
    }

    /**
     * 适用于复杂的判断规则，或者自定义判断规则
     *
     * @param actual
     * @param matcher
     * @param <T>
     */
    protected <T> void asThat(T actual, Matcher<? super T> matcher) {
        assertThat(actual, matcher);
    }

    /**
     * 常用的检测异常需要的方法
     */
    protected void expect(Class<? extends Throwable> clazz, String msg, Object... values) {
        mCheckExpRule.expect(clazz);
        if (msg != null) {
            mCheckExpRule.expectMessage(String.format(msg, values));
        }
    }

    /**
     * 重载方法
     * 常用检测异常需要的方法
     * 用于判断是否有预期的异常抛出
     *
     * @param clazz
     */
    protected void expect(Class<? extends Throwable> clazz) {
        expect(clazz, null);
    }

    /**
     * log helper
     * runtime elapsed time
     * 可以输出运行一段代码的运行时间
     */
    protected void logTime(final String tag, final long start, final long end) {
        Log.d(tag, "start= " + start + " end= " + end
                + " ellapsed= " + (end - start));
    }
}
