package io.ganguo.library.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wilson on 14-8-21.
 */
public class StringUtils {
    private static final String TAG = StringUtils.class.getName();

    public static final String VERSION_SEPERATOR = ".";
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim());
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String... str) {
        for (String s : str) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String... str) {
        for (String s : str) {
            if (isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是不是一个固定电话
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobileNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "0\\d{2,3}-\\d{5,9}|0\\d{2,3}-\\d{5,9}";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

    /**
     * 判断是不是一个手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

    /**
     * 判断是不是一样的
     *
     * @param src
     * @param target
     * @return
     */
    public static boolean equals(String src, String target) {
        if (isEmpty(src) || isEmpty(target))
            return false;
        return src.equals(target);
    }

    public static List<String> testToList(int count) {
        List<String> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add("Hello World! " + i);
        }
        return list;
    }

    /**
     * 字符串 转为 List<String>
     *
     * @param str
     * @param seperator
     * @return
     */
    public static List<String> stringToList(String str, String seperator) {
        List<String> itemList = new ArrayList<String>();
        if (isEmpty(str)) {
            return itemList;
        }
        StringTokenizer st = new StringTokenizer(str, seperator);
        while (st.hasMoreTokens()) {
            itemList.add(st.nextToken());
        }

        return itemList;
    }

    /**
     * 以一种简单的方式格式化字符串
     * 如  String s = StringHelper.format("{0} is {1}", "apple", "fruit");
     * System.out.println(s);	//输出  apple is fruit.
     *
     * @param pattern
     * @param args
     * @return
     */
    public static String format(String pattern, Object... args) {
        for (int i = 0; i < args.length; i++) {
            pattern = pattern.replace("{" + i + "}", args[i].toString());
        }
        return pattern;
    }

    /**
     * 格式化字符串, {"1","2","3"} ==> "1,2,3"
     *
     * @return
     */
    public static String formatArray(String seperator, Object... args) {
        if (args.length <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i].toString() + seperator);
        }
        return builder.substring(0, builder.length() - 1);
    }
}
