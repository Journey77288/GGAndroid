package io.ganguo.utils.util;

import org.junit.Before;
import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - Numbers工具类
 * Created by Lynn on 10/21/16.
 */

public class NumbersTest extends BaseTestCase {

    /**
     * 私有成员变量 需要通过反射的方式来mock
     * 因为Mockito本身不支持mock静态成员/方法和私有成员/方法
     * mock对象非void方法默认行为是返回类型的默认值
     * primitive type - default value 0, 0.0f, false 等
     * object - null
     * 对于void 方法， 无操作 nop
     */
    @Before
    public void init() {
    }

    @Test
    @TestDes("测试Numbers toInt方法")
    public void testToInt() throws Exception {
        Integer i = 10;
        asEquals(Numbers.toInt(i), 10);
        asEquals(Numbers.toInt(i, 1), 10);
        i = null;
        asEquals(Numbers.toInt(i), 0);
        asEquals(Numbers.toInt(i, 1), 1);
        //默认是0
        asFalse(Numbers.toInt(i) == 11);

        String s = "aa";
        asEquals(Numbers.toInt(s), 0);
        asEquals(Numbers.toInt(s, 1), 1);
        s = "123455";
        asEquals(Numbers.toInt(s), 123455);
        asEquals(Numbers.toInt(s, 1), 123455);
    }

    @Test
    @TestDes("测试Numbers toLong")
    public void testToLong() {
        Long l = 10L;
        asEquals(Numbers.toLong(l), 10L);

        asFalse(Numbers.toLong(l) == 0L);
        asFalse(Numbers.toLong(l, 1L) == 1L);

        String lstr = "jkdf";

        asEquals(Numbers.toLong(lstr), 0L);
        asEquals(Numbers.toLong(lstr, 10L), 10L);

        lstr = "1234";
        asEquals(Numbers.toLong(lstr), 1234L);
        asFalse(Numbers.toLong(lstr) == 0L);
        asFalse(Numbers.toLong(lstr, 10L) == 10L);
    }

    @Test
    @TestDes("测试Numbers toDouble")
    public void testToDouble() {
        Double d = 11D;
        asEquals(Numbers.toDouble(d), 11D);
        asFalse(Numbers.toDouble(d, 1D) == 1D);

        String dstr = "jkdf";

        asEquals(Numbers.toDouble(dstr), 0D);
        asEquals(Numbers.toDouble(dstr, 10D), 10D);

        dstr = "1234";
        asEquals(Numbers.toDouble(dstr), 1234D);
        asFalse(Numbers.toDouble(dstr) == 0D);
        asFalse(Numbers.toDouble(dstr, 10D) == 10D);
    }

    @Test
    @TestDes("测试Numbers toFloat")
    public void testToFloat() {
        Float f = 11F;
        asEquals(Numbers.toFloat(f), 11F);
        asFalse(Numbers.toFloat(f, 1F) == 1F);

        String fstr = "jkdf";

        asEquals(Numbers.toFloat(fstr), 0F);
        asEquals(Numbers.toFloat(fstr, 10F), 10F);

        fstr = "1234";
        asEquals(Numbers.toFloat(fstr), 1234F);
        asFalse(Numbers.toFloat(fstr) == 0F);
        asFalse(Numbers.toFloat(fstr, 10F) == 10F);
    }

    @Test
    @TestDes("test Numbers toPretty")
    public void testToPretty() {
        asEquals(Numbers.toPretty(1.0), "1.00");
        asEquals(Numbers.toPretty(123.), "123.00");
        asEquals(Numbers.toPretty(234.12345), "234.12");
        asEquals(Numbers.toPretty(12.1), "12.10");
    }

    @Test
    @TestDes("test Numbers random")
    public void testRandom() {
        final int min = -10;
        final int max = 10;
        for (int i = 0; i < 1000; i++) {
            final int ans = Numbers.random(min, max);
            asTrue(ans >= min);
            asTrue(ans <= max);
        }
    }

    @Test
    @TestDes("test Numbers toChinese")
    public void testToChinese() {
        asEquals(Numbers.toChinese(0), "");
        asEquals(Numbers.toChinese(-0), "");
        asEquals(Numbers.toChinese(1), "一");
        asEquals(Numbers.toChinese(12), "一十二");
        asEquals(Numbers.toChinese(234), "二百三十四");
        asEquals(Numbers.toChinese(3456), "三千四百五十六");
        asEquals(Numbers.toChinese(45678), "四万五千六百七十八");
        asEquals(Numbers.toChinese(567891), "五十六万七千八百九十一");
        asEquals(Numbers.toChinese(1234567), "一百二十三万四千五百六十七");
        asEquals(Numbers.toChinese(12345678), "一千二百三十四万五千六百七十八");
        asEquals(Numbers.toChinese(123456789), "一亿二千三百四十五万六千七百八十九");
        //0
        asEquals(Numbers.toChinese(101), "一百零一");
        asEquals(Numbers.toChinese(01), "一");
        asEquals(Numbers.toChinese(100), "一百");
    }

    @Test
    @TestDes("test Numbers formatDecimal double")
    public void testFormatDouble() {
        //scale 取后几位
        asEquals(Numbers.formatDecimal(1.1D, 0), 1.0D);
        asEquals(Numbers.formatDecimal(1000.111D, 1), 1000.1D);
        asEquals(Numbers.formatDecimal(10.11D, 2), 10.11D);
        //超出有限位数
        asEquals(Numbers.formatDecimal(1.1111D, 5), 1.1111D);
        asEquals(Numbers.formatDecimal(1.1111D, 10), 1.1111D);
    }

    @Test
    @TestDes("test Numbers formatDecimal float")
    public void testFormatFloat() {
        //scale 取后几位
        asEquals(Numbers.formatDecimal(1.1f, 0), 1.0f);
        asEquals(Numbers.formatDecimal(1000.111f, 1), 1000.1f);
        asEquals(Numbers.formatDecimal(10.11f, 2), 10.11f);
        //超出有限位数
        asEquals(Numbers.formatDecimal(1.1111f, 5), 1.1111f);
        asEquals(Numbers.formatDecimal(1.1111f, 10), 1.1111f);
    }
}
