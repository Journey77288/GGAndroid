package io.ganguo.utils.exception;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - Exception
 * Created by Lynn on 10/25/16.
 */

public class ExceptionsTest extends BaseTestCase {
    @Test
    @TestDes("test BaseException")
    public void testBaseException() throws Exception {
//        String throwString = "throw TestException";
        expect(TestException.class);
        throw new TestException("");
    }

    @Test
    @TestDes("test BeansException")
    public void testBeansException() throws Exception {
        String throwString = "throw BeansException";
        expect(BeansException.class, throwString);
        throw new BeansException(throwString);
    }

    private class TestException extends BaseException {
        public TestException(String detailMessage) {
            super(detailMessage);
        }

        public TestException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }
    }
}
