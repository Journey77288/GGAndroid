package io.ganguo.utils.util;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - Regs工具类
 * Created by Lynn on 10/21/16.
 */

public class RegsTest extends BaseTestCase {
    @Test
    @TestDes("测试Regs isUrl")
    public void testIsUrl() throws Exception {
        asFalse(Regs.isUrl(""));
        asFalse(Regs.isUrl(null));
        asFalse(Regs.isUrl("aljdklafj"));
        asFalse(Regs.isUrl("xx:/xx"));
        asFalse(Regs.isUrl("xx:xx"));
        asFalse(Regs.isUrl("xx//xx"));

        //满足条件
        asTrue(Regs.isUrl("xxx://xxx"));

        //TODO 升级URL判断?
    }

    //TODO MOBILE这个判断应该也需要变化
    @Test
    @TestDes("测试Regs isMobile")
    public void testisMobile() throws Exception {
        asFalse(Regs.isMobile(""));
        asFalse(Regs.isMobile(null));
        asFalse(Regs.isMobile("aljdklafj"));
        asFalse(Regs.isMobile("12345678910"));

        for (int i = 0; i < 9; i++) {
            // 14[57]
            if (i != 5 && i != 7) {
                asFalse(Regs.isMobile("14" + i + "11111111"));
            } else {
                asTrue(Regs.isMobile("14" + i + "11111111"));
            }

            //15[012356789], 没有4
            if (i != 4) {
                asTrue(Regs.isMobile("15" + i + "11111111"));
            } else {
                asFalse(Regs.isMobile("15" + i + "11111111"));
            }

            //13, 14, 15, 17, 18开头号码
            if (i != 3 && i != 4 && i != 5 && i != 7 && i != 8) {
                //1x7 只有7在所以规定号码中可以作为第三个有效数字
                asFalse(Regs.isMobile("1" + i + "711111111"));
            } else {
                asTrue(Regs.isMobile("1" + i + "711111111"));
            }
        }

        //前缀 0|86|17951, 额，居然中间不能有空格
        //TODO 帮助类需要修改逻辑了
        asTrue(Regs.isMobile("013911111111"));
        asTrue(Regs.isMobile("8613911111111"));
        asTrue(Regs.isMobile("1795113911111111"));
    }

    @Test
    @TestDes("测试Regs isLetter方法")
    public void testIsLetter() throws Exception {
        //大小写
        asTrue(Regs.isLetter("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        //含有非字母, 数字, 中文, 符号等
        asFalse(Regs.isLetter("abcdefghijklmnopqrstuvwxyz1"));
        asFalse(Regs.isLetter("abcdefghijklmnopqrstuvwxyz中文"));
        asFalse(Regs.isLetter("abcdefghijklmnopqrstuvwxyz,"));
        //空
        asFalse(Regs.isLetter(""));
        asFalse(Regs.isLetter(" "));
        asFalse(Regs.isLetter(null));
    }

    @Test
    @TestDes("测试Regs isCapitalLetter")
    public void testIsCapitalLetter() throws Exception {
        //大小写
        asTrue(Regs.isCapitalLetter("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        asFalse(Regs.isCapitalLetter("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        //含有非字母, 数字, 中文, 符号等
        asFalse(Regs.isLetter("ABCDEFGHIJKLMNOPQRSTUVWXYZ1"));
        asFalse(Regs.isLetter("ABCDEFGHIJKLMNOPQRSTUVWXYZ中文"));
        asFalse(Regs.isLetter("ABCDEFGHIJKLMNOPQRSTUVWXYZ,"));
        //空
        asFalse(Regs.isLetter(""));
        asFalse(Regs.isLetter(" "));
        asFalse(Regs.isLetter(null));
    }

    @Test
    @TestDes("测试Regs isLowercaseLetter方法")
    public void testIsLowercaseLetter() throws Exception {
        //大小写
        asTrue(Regs.isLowercaseLetter("abcdefghijklmnopqrstuvwxyz"));
        asFalse(Regs.isLowercaseLetter("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        //含有非字母, 数字, 中文, 符号等
        asFalse(Regs.isLetter("abcdefghijklmnopqrstuvwxyz1"));
        asFalse(Regs.isLetter("abcdefghijklmnopqrstuvwxyz中文"));
        asFalse(Regs.isLetter("abcdefghijklmnopqrstuvwxyz,"));
        //空
        asFalse(Regs.isLetter(""));
        asFalse(Regs.isLetter(" "));
        asFalse(Regs.isLetter(null));
    }

    @Test
    @TestDes("测试Regs isNumber方法")
    public void testIsNumber() {
        //数字
        asTrue(Regs.isNumber("1234567890"));
        //含有非数字
        asFalse(Regs.isNumber("1234567890a"));
        asFalse(Regs.isNumber("1234567890中文"));
        asFalse(Regs.isNumber("1234567890,"));
        asFalse(Regs.isNumber(""));
        asFalse(Regs.isNumber(" "));
        asFalse(Regs.isNumber(null));
    }

    @Test
    @TestDes("测试Regs isNumberLetter方法")
    public void testIsNumberLetter() {
        //数字/字母
        asTrue(Regs.isNumberLetter("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"));
        //含有非数字/字母
        asFalse(Regs.isNumberLetter("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890中文"));
        asFalse(Regs.isNumberLetter("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890,"));
        //empty case
        asFalse(Regs.isNumberLetter(""));
        asFalse(Regs.isNumberLetter(" "));
        asFalse(Regs.isNumberLetter(null));
    }

    @Test
    @TestDes("测试Regs isChinese方法")
    public void testIsChinese() {
        //中文
        asTrue(Regs.isChinese("中文"));
        //含有非中文
        asFalse(Regs.isChinese("中文a"));
        asFalse(Regs.isChinese("中文0"));
        asFalse(Regs.isChinese("中文,"));
        //empty case
        asFalse(Regs.isChinese(""));
        asFalse(Regs.isChinese(" "));
        asFalse(Regs.isChinese(null));
    }

    @Test
    @TestDes("测试Regs isContainChinese方法")
    public void testIsContainChinese() {
        //全中文
        asTrue(Regs.isContainChinese("中文"));
        //含有非中文
        asTrue(Regs.isContainChinese("中文a"));
        asTrue(Regs.isContainChinese("中文0"));
        asTrue(Regs.isContainChinese("中文,"));
        //empty case
        asFalse(Regs.isContainChinese(""));
        asFalse(Regs.isContainChinese(" "));
        asFalse(Regs.isContainChinese(null));
    }

    //TODO 邮箱， 身份证号码
}
