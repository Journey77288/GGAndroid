package io.ganguo.utils.util.crypto;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - Ciphers 加密帮助类
 * Created by Lynn on 10/24/16.
 */

public class CiphersTest extends BaseTestCase {
    @Test
    @TestDes("DES算法 加密解密 测试")
    public void testEncodeAndDecode() {
        //TODO 不少于八位
        final String key = "12345678";
        //被加密内容
        final String content = "asdfghjk";
        String encodedContent = Ciphers.encode(key, content);

        asEquals(content, Ciphers.decode(key, encodedContent));
    }


    @Test
    @TestDes("md5 测试")
    public void testByteToStr() {
        String content = "11";

        //TODO md5 不可逆，测试？
        String one = Ciphers.md5(content);
        String two = Ciphers.md5(content);

        asNotNull(one);
        asNotNull(two);
        asEquals(one, two);
    }
}
