package io.ganguo.utils.util;

import io.ganguo.utils.bean.Globals;

/**
 * Grid网格布局相关，位置计算
 * <p>
 * Created by leo on 16/7/29.
 */
public class GridPositions {

    private GridPositions() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * function:判断是否是在最左边一列
     *
     * @param position 当前位置
     * @param rowCount 列表总数量
     * @return
     */
    public static boolean isLeft(int position, int rowCount) {
        if (rowCount == 0) {
            throw new RuntimeException("rowCount could not be zero");
        }

        if (position % rowCount == 0) {
            return true;
        }
        return false;
    }

    /**
     * function: 判断是否是在最右边一列
     *
     * @param position 当前位置
     * @param rowCount 列表总数量
     * @return
     */
    public static boolean isRight(int position, int rowCount) {
        if (rowCount == 0) {
            throw new RuntimeException("rowCount could not be zero");
        }

        if (position % rowCount == rowCount - 1) {
            return true;
        }
        return false;
    }

    /**
     * function: 判断是否是在第一行
     *
     * @param position 当前位置
     * @param rowCount 列表总数量
     * @return
     */
    public static boolean isFirstLine(int position, int rowCount) {
        if (rowCount == 0) {
            throw new RuntimeException("rowCount could not be zero");
        }

        if (position < rowCount) {
            return true;
        }
        return false;
    }

    /**
     * 是否在最后一行
     *
     * @param position 当前位置
     * @param size     列表总数量
     * @param rowCount 列数
     */
    public static boolean isLastLine(int position, int size, int rowCount) {
        if (rowCount == 0) {
            throw new RuntimeException("rowCount could not be zero");
        }
        if (size <= rowCount) {
            return true;
        }
        if (position < rowCount) {
            return false;
        }
        int number = size - position;
        if (number <= rowCount) {
            return true;
        }
        return false;
    }
}

