package io.ganguo.utils.util;

import java.util.regex.Pattern;

import io.ganguo.utils.bean.Globals;


/**
 * 正则校验
 * <p>
 * Created by Wilson on 8/12/15.
 */
public class Regs {

    private Regs() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * URL正则
     */
    private final static Pattern URL_PATTERN = Pattern
            .compile("[a-zA-z]+://[^\\s]*");

    /**
     * 邮政编码正则
     */
    private final static Pattern ZIP_PATTERN = Pattern
            .compile("[1-9]\\d{5}(?!\\d)");

    /**
     * 身份证正则
     */
    private final static Pattern IDCARD_PATTERN = Pattern
            .compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");

    /**
     * 手机号码正则
     */
    private final static Pattern MOBILE_PATTERN = Pattern
            .compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");

    /**
     * 邮箱正则
     */
    private final static Pattern EMAIL_PATTERN = Pattern
            .compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");


    /**
     * 判断是不是电子邮件地址
     *
     * @param text
     * @return
     */
    public static boolean isEmail(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(text).matches();
    }

    /**
     * 判断是不是URL地址
     *
     * @param text
     * @return
     */
    public static boolean isUrl(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        return URL_PATTERN.matcher(text).matches();
    }

    /**
     * 判断是不是手机号码
     *
     * @param text
     * @return
     */
    public static boolean isMobile(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(text).matches();
    }

    /**
     * 判断是不是身份证号码
     *
     * @param text
     * @return
     */
    public static boolean isIDCard(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        return IDCARD_PATTERN.matcher(text).matches();
    }

    /**
     * 判断是不是邮政编码
     *
     * @param text
     * @return
     */
    public static boolean isZipCode(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        return ZIP_PATTERN.matcher(text).matches();
    }

    /**
     * 只含字母和数字(也不带小数)
     *
     * @param text
     * @return
     */
    public static boolean isNumberLetter(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        String expr = "^[A-Za-z0-9]+$";
        return text.matches(expr);
    }

    /**
     * 只含数字(也不带小数)
     *
     * @param text
     * @return
     */
    public static boolean isNumber(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        String expr = "^[0-9]+$";
        return text.matches(expr);
    }

    /**
     * 只含字母
     *
     * @param text
     * @return
     */
    public static boolean isLetter(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        String expr = "^[A-Za-z]+$";
        return text.matches(expr);
    }

    /**
     * 只含大写字母
     *
     * @param text
     * @return
     */
    public static boolean isCapitalLetter(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        String expr = "^[A-Z]+$";
        return text.matches(expr);
    }

    /**
     * 只含小写字母
     *
     * @param text
     * @return
     */
    public static boolean isLowercaseLetter(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        String expr = "^[a-z]+$";
        return text.matches(expr);
    }

    /**
     * 只有中文
     *
     * @param text
     * @return
     */
    public static boolean isChinese(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        String expr = "^[\u0391-\uFFE5]+$";
        return text.matches(expr);
    }

    /**
     * 包含中文
     *
     * @param text
     * @return
     */
    public static boolean isContainChinese(String text) {
        if (Strings.isEmpty(text)) {
            return false;
        }
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < text.length(); i++) {
            String temp = text.substring(i, i + 1);
            boolean flag = temp.matches(chinese);
            if (flag) {
                return true;
            }
        }
        return false;
    }
}
