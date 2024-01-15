package io.ganguo.utils.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import io.ganguo.utils.bean.Globals;

/**
 * <pre>
 *     author : leo
 *     time   : 2019/04/28
 *     desc   : 金额工具类，注意在日常开发中价格不能用double、float等类型，计算会出现精度问题，
 *              而应该统一保存为字符串，然后用工具类转换为BigDecimal类型来进行计算
 * </pre>
 */
public class Moneys {
    //精确到两位小数
    public static DecimalFormat DECIMAL_FORMAT_TWO = new DecimalFormat("#.##");
    //精确到四位小数
    public static DecimalFormat DECIMAL_FORMAT_FOUR = new DecimalFormat("#.####");

    private Moneys() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney(String value) {
        return formatMoney(DECIMAL_FORMAT_TWO, value);
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney(DecimalFormat format, BigDecimal value) {
        if (value == null) {
            return "0.00";
        }
        return format.format(value);
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney(BigDecimal value) {
        if (value == null) {
            return "0.00";
        }
        return formatMoney(DECIMAL_FORMAT_TWO, value);
    }

    /**
     * 格式化金额
     *
     * @param format DecimalFormat
     * @param value  金额值
     * @return
     */
    public static String formatMoney(DecimalFormat format, String value) {
        if (value == null || value == "") {
            value = "0.00";
        }
        return format.format(new BigDecimal(value));
    }


    /**
     * 金额相加
     *
     * @param valueStr 基础值
     * @param addStr   被加数
     * @return
     */
    public static String moneyAdd(String valueStr, String addStr) {
        return moneyAdd(DECIMAL_FORMAT_TWO, valueStr, addStr);
    }

    /**
     * 金额相加
     *
     * @param format
     * @param valueStr 基础值
     * @param addStr   被加数
     * @return
     */
    public static String moneyAdd(DecimalFormat format, String valueStr, String addStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal addend = new BigDecimal(addStr);
        return format.format(moneyAdd(value, addend));
    }

    /**
     * 金额相加
     *
     * @param value  基础值
     * @param augend 被加数
     * @return
     */
    public static BigDecimal moneyAdd(BigDecimal value, BigDecimal augend) {
        return value.add(augend);
    }

    /**
     * 金额相减
     *
     * @param valueStr 基础值
     * @param minusStr 减数
     * @return
     */
    public static String moneySubtract(String valueStr, String minusStr) {
        return moneySubtract(DECIMAL_FORMAT_TWO, valueStr, minusStr);
    }


    /**
     * 金额相减
     *
     * @param format   DecimalFormat
     * @param valueStr 基础值
     * @param minusStr 减数
     * @return
     */
    public static String moneySubtract(DecimalFormat format, String valueStr, String minusStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal subtrahend = new BigDecimal(minusStr);
        return format.format(moneySubtract(value, subtrahend));
    }

    /**
     * 金额相减
     *
     * @param value      基础值
     * @param subtrahend 减数
     * @return
     */
    public static BigDecimal moneySubtract(BigDecimal value, BigDecimal subtrahend) {
        return value.subtract(subtrahend);
    }


    /**
     * 金额相乘
     *
     * @param valueStr 基础值
     * @param mulStr   被乘数
     * @return
     */
    public static String moneyMultiply(String valueStr, String mulStr) {
        return moneyMultiply(DECIMAL_FORMAT_TWO, valueStr, mulStr);
    }


    /**
     * 金额相乘
     *
     * @param format   DecimalFormat
     * @param valueStr 基础值
     * @param mulStr   被乘数
     * @return
     */
    public static String moneyMultiply(DecimalFormat format, String valueStr, String mulStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return format.format(moneyMultiply(value, mulValue));
    }

    /**
     * 金额相乘
     *
     * @param value    基础值
     * @param mulValue 被乘数
     * @return
     */
    public static BigDecimal moneyMultiply(BigDecimal value, BigDecimal mulValue) {
        return value.multiply(mulValue);
    }


    /**
     * 金额相除
     * 精确小位小数
     *
     * @param valueStr  基础值
     * @param divideStr 被乘数
     * @return
     */
    public static String moneyDivide(String valueStr, String divideStr) {
        return moneyDivide(DECIMAL_FORMAT_TWO, valueStr, divideStr);
    }

    /**
     * 金额相除
     * 精确小位小数
     *
     * @param format    DecimalFormat
     * @param valueStr  基础值
     * @param divideStr 被乘数
     * @return
     */
    public static String moneyDivide(DecimalFormat format, String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return format.format(moneyDivide(value, divideValue));
    }


    /**
     * 金额相除
     * 精确小位小数
     *
     * @param value       基础值
     * @param divideValue 被乘数
     * @return
     */
    public static BigDecimal moneyDivide(BigDecimal value, BigDecimal divideValue) {
        return value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 值比较大小
     * 如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param valueStr     (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyCompare(String valueStr, String compValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        //0:等于    >0:大于    <0:小于
        int result = value.compareTo(compValue);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 值比较大小
     * 如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param valueStr     (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyCompare(BigDecimal valueStr, BigDecimal compValueStr) {
        //0:等于    >0:大于    <0:小于
        int result = valueStr.compareTo(compValueStr);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 金额乘以，省去小数点
     *
     * @param valueStr 基础值
     * @return
     */
    public static String moneyMulOfNotPoint(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(divideStr);
        valueStr = DECIMAL_FORMAT_TWO.format(value.multiply(mulValue));
        return DECIMAL_FORMAT_TWO.format(value.multiply(mulValue)).substring(0, valueStr.length() - 3);
    }

    /**
     * 给金额加逗号切割
     *
     * @param str
     * @return
     */
    public static String addComma(String str) {
        try {
            String banNum = "";
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                if (arr.length == 2) {
                    str = arr[0];
                    banNum = "." + arr[1];
                }
            }
            // 将传进数字反转
            String reverseStr = new StringBuilder(str).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            // 将数字重新反转
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            resultStr += banNum;
            return resultStr;
        } catch (Exception e) {
            return str;
        }

    }
}
