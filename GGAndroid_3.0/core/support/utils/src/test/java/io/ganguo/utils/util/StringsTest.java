package io.ganguo.utils.util;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.utils.util.Strings;

/**
 * 单元测试 - Strings工具类
 * Created by Lynn on 10/21/16.
 */

public class StringsTest extends BaseTestCase {

    @Test
    @TestDes("测试Strings isEmpty方法")
    public void testEmpty() throws Exception {
        final String s = null;
        //判断是否为空
        asTrue(Strings.isEmpty(s));
        asTrue(Strings.isEmpty(null, null));
        //涉及空格
        asTrue(Strings.isEmpty(" ", null));
        asTrue(Strings.isEmpty(" "));
        //空串
        asTrue(Strings.isEmpty(""));
        asTrue(Strings.isEmpty("", " "));
        //普通字符串
        asFalse(Strings.isEmpty("a"));
        asFalse(Strings.isEmpty("a", "b"));
        //参数列表中含有 空格或者空串
        asTrue(Strings.isEmpty("", "common"));
        asTrue(Strings.isEmpty(" ", "common"));
    }

    /**
     * 跟空的测试对称
     *
     * @throws Exception
     */
    @Test
    @TestDes("测试Strings isNotEmpty方法")
    public void testNotEmpty() throws Exception {
        final String s = null;
        //判断是否为空
        asFalse(Strings.isNotEmpty(s));
        asFalse(Strings.isNotEmpty(null, null));
        //涉及空格
        asFalse(Strings.isNotEmpty(" ", null));
        asFalse(Strings.isNotEmpty(" "));
        //空串
        asFalse(Strings.isNotEmpty(""));
        asFalse(Strings.isNotEmpty("", " "));
        //普通字符串
        asTrue(Strings.isNotEmpty("a"));
        asTrue(Strings.isNotEmpty("a", "b"));
        //参数列表中含有 空格或者空串
        asFalse(Strings.isNotEmpty("", "common"));
        asFalse(Strings.isNotEmpty(" ", "common"));
    }

    @Test
    @TestDes("测试Strings isEquals方法")
    public void testEquals() throws Exception {
        //普通空串
        asFalse(Strings.isEquals("", ""));
        //空格
        asFalse(Strings.isEquals(" ", ""));
        asFalse(Strings.isEquals(" ", " "));
        //参数含有 空串, 空格，为空
        asFalse(Strings.isEquals(null, ""));
        asFalse(Strings.isEquals(null, null));
        asFalse(Strings.isEquals(" ", null));
        //common case
        asTrue(Strings.isEquals("abcd", "abcd"));
        asFalse(Strings.isEquals("ab", "abcs"));
        //大小写敏感
        asFalse(Strings.isEquals("ab", "Ab"));
    }

    @Test
    @TestDes("测试Strings isEqualsIgnoreCase方法 忽略大小写")
    public void testEqualsIgnore() throws Exception {
        //空格， 空串， 空
        asFalse(Strings.isEqualsIgnoreCase("", ""));
        asFalse(Strings.isEqualsIgnoreCase(null, ""));
        asFalse(Strings.isEqualsIgnoreCase(" ", ""));
        //common 大小写不敏感
        asTrue(Strings.isEqualsIgnoreCase("abcd", "abcd"));
        asTrue(Strings.isEqualsIgnoreCase("ABCD", "ABCD"));
        asTrue(Strings.isEqualsIgnoreCase("abcd", "ABCD"));
        asFalse(Strings.isEqualsIgnoreCase("abcd", "abdc"));
    }

    @Test
    @TestDes("测试Strings nullToEmpty 如果为空则返回空串")
    public void testNullToEmpty() throws Exception {
        //null
        asEquals(Strings.nullToEmpty(null), "");
        asFalse(Strings.nullToEmpty(null).equals(" "));
        //common case
        asEquals(Strings.nullToEmpty("abcd"), "abcd");
        asEquals(Strings.nullToEmpty(""), "");
        asFalse(Strings.nullToEmpty("").equals(" "));
    }

    @Test
    @TestDes("测试Strings format 格式化{args的index}")
    public void testFormat() throws Exception {
        //common case
        asEquals(Strings.format("{0}bcd", "a"), "abcd");
        //index 错位
        asEquals(Strings.format("{1}{3}{2}{0}", "a", "b", "c", "d"), "bdca");
        //错误的index
        asEquals(Strings.format("{1}bcd", "a"), "{1}bcd");
    }

    @Test
    @TestDes("测试Strings randomUUID")
    public void testUUID() throws Exception {
        final String s = Strings.randomUUID();
        asTrue("uuid: " + s, s != null);
    }

    @Test
    @TestDes("测试Strings capitalize方法")
    public void testCapitalize() throws Exception {
        asEquals(Strings.capitalize("abcd"), "Abcd");
        asEquals(Strings.capitalize("Abcd"), "Abcd");
        asEquals(Strings.capitalize(null), null);
        asEquals(Strings.capitalize(""), "");
    }
}
