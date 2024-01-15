package io.ganguo.utils.util;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - Randoms
 * Created by Lynn on 10/26/16.
 */

public class RandomsTest extends BaseTestCase {
    @Test
    @TestDes("test Randoms getRandomNumbersAndLetters")
    public void testGetRandomNumbersAndLetters() {
        String test = Randoms.getRandomNumbersAndLetters(100);

        asEquals(Randoms.getRandomNumbersAndLetters(0), "");
        asTrue(Regs.isNumberLetter(test));
    }

    @Test
    @TestDes("test Randoms getRandomNumbers")
    public void testGetRandomNumbers() {
        String test = Randoms.getRandomNumbers(100);

        asEquals(Randoms.getRandomNumbers(0), "");
        asTrue(Regs.isNumber(test));
    }

    @Test
    @TestDes("test Randoms getRandomNumbers")
    public void testGetRandomLetters() {
        String test = Randoms.getRandomLetters(100);

        asEquals(Randoms.getRandomLetters(0), "");
        asTrue(Regs.isLetter(test));
    }

    @Test
    @TestDes("test Randoms getRandomCapitalLetters")
    public void testGetRandomCapitalLetters() {
        String test = Randoms.getRandomCapitalLetters(100);

        asEquals(Randoms.getRandomCapitalLetters(0), "");
        asTrue(Regs.isCapitalLetter(test));
    }

    @Test
    @TestDes("test Randoms getRandomLowerCaseLetters")
    public void testGetRandomLowercaseLetters() {
        String test = Randoms.getRandomLowerCaseLetters(100);

        asEquals(Randoms.getRandomLowerCaseLetters(0), "");
        asTrue(Regs.isLowercaseLetter(test));
    }

    @Test
    @TestDes("test Randoms getRandom(String source, int length)")
    public void testGetRandom() {
        String source = null;
        String test = Randoms.getRandom(source, 10);
        asNull(test);

        source = "中文ab12,";
        String regxp = "^[中文ab12,]+$";
        test = Randoms.getRandom(source, 10);
        asNotNull(test);
        asTrue(test.matches(regxp));
    }

    @Test
    @TestDes("test Randoms getRandom(char[] source, int length)")
    public void testGetRandomCharArray() {
        char[] source = null;
        String test = Randoms.getRandom(source, 10);
        asNull(test);

        source = new char[]{'中', '文', 'a', 'b', '1', '2', ','};
        final String regxp = "^[中文ab12,]+$";

        test = Randoms.getRandom(source, 0);
        asEquals(test, "");

        test = Randoms.getRandom(source, 10);
        asNotNull(test);
        asTrue(test.matches(regxp));
    }

    @Test
    @TestDes("test Randoms getRandom(int max)")
    public void testGetRandomInt() {
        final int max = Integer.MAX_VALUE >> 1;
        for (int i = 0; i < 1000; i++) {
            final int num = Randoms.getRandom(max);
            asTrue(num <= max);
        }
    }

    @Test
    @TestDes("test Randoms getRandom(int min, int max)")
    public void testGetRandomIntRange() {
        final int max = Integer.MAX_VALUE >> 1;
        final int min = Integer.MIN_VALUE << 1;
        for (int i = 0; i < 1000; i++) {
            final int num = Randoms.getRandom(min, max);
            asTrue(min <= num && num <= max);
        }
    }

    //TODO shuffle? 判断两次是否相同的方法不合理
}
