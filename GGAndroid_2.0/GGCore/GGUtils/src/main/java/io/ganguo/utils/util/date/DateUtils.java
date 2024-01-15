package io.ganguo.utils.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tony on 10/27/14.
 */
public class DateUtils {
    public static String dayNames[] = {"日", "一", "二", "三", "四", "五", "六"};

    /**
     * <pre>
     * 判断date和当前日期是否在同一周内
     * 注:
     * Calendar类提供了一个获取日期在所属年份中是第几周的方法，对于上一年末的某一天
     * 和新年初的某一天在同一周内也一样可以处理，例如2012-12-31和2013-01-01虽然在
     * 不同的年份中，但是使用此方法依然判断二者属于同一周内
     * </pre>
     *
     * @param date
     * @return
     */
    public static boolean isSameWeekWithToday(Date date) {

        if (date == null) {
            return false;
        }

        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);

        // 1.比较当前日期在年份中的周数是否相同
        if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 格式化时间
     * pattern HH:mm 20:10
     *
     * @param pattern yyyy-MM-dd
     * @param date    日期
     * @return 格式化后时间字符串
     */
    public static String format(String pattern, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 本周六 20:00
     *
     * @param date
     * @return
     */
    public static String getTimeOnWeek(Date date) {
        final StringBuilder friendTime = new StringBuilder();
        if (isSameWeekWithToday(date)) {
            friendTime.append("本周");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            friendTime.append(dayNames[dayOfWeek - 1]);
            friendTime.append(" ");
            friendTime.append(format("HH:mm", date));
        } else {
            friendTime.append(format("yyyy-MM-dd HH:mm", date));
        }
        return friendTime.toString();
    }

    /**
     * 今天是否在两个时间之内
     *
     * @param begin
     * @param end
     * @return
     */
    public static boolean isBetweenWithToday(Date begin, Date end) {
        Date today = new Date();
        return today.getTime() > begin.getTime() && today.getTime() <= end.getTime();
    }

    /**
     * 当前时间 距离 传入时间还有多久
     * dd天hh小时mm分钟
     * 11小时23分钟
     *
     * @param date
     * @return
     */
    public static String timeDiffFromNow(Date date, String[] format) {
        if (date == null) return null;
        return timeDiff(date.getTime() - System.currentTimeMillis(), format);
    }

    /**
     * 传入时间 距离现在 还有多久
     * hh小时mm分钟
     * 11小时23分钟
     *
     * @param date
     * @return
     */
    public static String timeDiffToNow(Date date, String[] format) {
        if (date == null) return null;
        return timeDiff(System.currentTimeMillis() - date.getTime(), format);
    }

    /**
     * 计算时间差值
     *
     * @param diff
     * @param format
     * @return
     */
    private static String timeDiff(final long diff, final String[] format) {
        final int sec = 1000;
        final int min = 60 * sec;
        final int hour = 60 * min;
        final int day = 24 * hour;

        StringBuilder dateFormatted = new StringBuilder();

        final long dd = diff / day;
        final long hh = diff / hour - dd * 24;
        final long mm = diff / min - hh * 60 - dd * 24 * 60;
        final long ss = diff / sec - mm * 60 - hh * 60 * 60 - dd * 24 * 60 * 60;

        if (dd > 0) {
            dateFormatted.append(dd);
            dateFormatted.append(format[0]);
        }

        if (hh > 0) {
            dateFormatted.append(hh);
            dateFormatted.append(format[1]);
        }
        if (mm > 0) {
            dateFormatted.append(mm);
            dateFormatted.append(format[2]);
        }
        if (ss > 0) {
            dateFormatted.append(ss);
            dateFormatted.append(format[3]);
        }

        return dateFormatted.toString();
    }
}
