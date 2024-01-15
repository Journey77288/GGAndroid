package io.ganguo.utils.util;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - LocationUtils
 * Created by Lynn on 10/25/16.
 */

public class GridPositionsTest extends BaseTestCase {
    @Test
    @TestDes("test LocationUtils")
    public void test() {
        asTrue(GridPositions.isLeft(0, 1));
        asTrue(GridPositions.isLeft(12, 3));
        asFalse(GridPositions.isLeft(11, 3));

        asTrue(GridPositions.isRight(0, 1));
        asTrue(GridPositions.isRight(11, 4));
        asFalse(GridPositions.isRight(12, 3));

        asTrue(GridPositions.isFirstLine(0, 1));
        for (int i = 0; i < 6; i++) {
            if (i < 6) {
                asTrue(GridPositions.isFirstLine(i, 6));
            } else {
                asFalse(GridPositions.isFirstLine(i, 6));
            }
        }

        asTrue(GridPositions.isLastLine(0, 1, 1));
        // pos >= size - size % row count
        asTrue(GridPositions.isLastLine(12, 13, 11));
        asFalse(GridPositions.isLastLine(12, 24, 15));
    }
}
