package io.ganguo.library.exception;

import org.junit.Test;

import java.net.UnknownHostException;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;

/**
 * 单元测试 Exceptions
 * Created by Lynn on 10/28/16.
 */

public class ExceptionsTest extends GGLibraryTestBase {
    @Test
    @TestDes("test BaseException")
    public void testBaseException() throws Exception {
        expect(TestException.class);
        throw new TestException("test");
    }

    @Test
    public void testNetworkException() throws Exception {
        expect(NetworkException.class);

        final String msg = "unknown host";
        throw new NetworkException(new UnknownHostException(msg));
    }

    @Test
    public void testServerException() throws Exception {
        expect(ServerException.class);
        throw new ServerException();
    }

    class TestException extends BaseException {
        public TestException(String detailMessage) {
            super(detailMessage);
        }
    }
}
