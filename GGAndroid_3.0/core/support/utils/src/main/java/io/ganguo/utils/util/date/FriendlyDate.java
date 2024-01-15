package io.ganguo.utils.util.date;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 本类作为Date类的扩展
 * {@link #toFriendlyDate}函数, 用于处理日志、微博、评论时间的处理，让时间显示更为友好
 * {@link Date}
 *
 * @author 小叶
 *         http://wumian.us/archives/2013122188.html
 * @date 2013-12-20 21:45
 */
public class FriendlyDate {

    // 默认时间格式
    private static final String dateFormat = "yyyy年MM月dd日";

    // 月份+日期
    private static final String nowDateFormat = "MM月dd日";

    // 小时+分钟
    private static final String timeFormat = " HH:mm";

    // 天数时差
    private static final String pattern_DayAgo = "{0}天之前";

    // 小时时差
    private static final String pattern_HoursAgo = "{0}小时之前";

    // 分钟时差
    private static final String pattern_MinutelAgo = "{0}分钟之前";

    // 秒时差
    private static final String pattern_SecondsAgo = "{0}秒之前";

    private final Date mDate;

    public FriendlyDate(Date date) {
        this.mDate = date;
    }

    /**
     * 转换成用户所在时区的时间，并返回站点设置的日期格式,返回较为友好的时间格式
     *
     * @param showTime 是否显示时间
     * @return 格式化后的时间字符串
     */
    public String toFriendlyDate(boolean showTime) {
        try {
            // 现在时间
            final Date now = new Date();
            //今天凌晨0：00：01
            Date nowZero = new Date();
            nowZero.setHours(0);
            nowZero.setMinutes(0);
            nowZero.setSeconds(1);
            String value = "{0}";
            if (showTime) {
                value = "{0}" + DateUtils.format(timeFormat, mDate);
            }

            // 求时间差
            final TimeSpan daySpan = new TimeSpan(mDate, nowZero);
            final TimeSpan timeSpan = new TimeSpan(mDate, now);
            if (mDate.getTime() > now.getTime()) {
                //转换的时间比当前时间晚, 则显示年份
                return MessageFormat.format(value, DateUtils.format(dateFormat, mDate));
            }

            // 时间差超过7天，年份为当年显示月-日 年份不为同一年显示年-月-日
            if (daySpan.days > 6) {
                if (mDate.getYear() == now.getYear()) {
                    //如果在同一年内, 则不显示年份
                    return MessageFormat.format(value, DateUtils.format(nowDateFormat, mDate));
                } else {
                    //如果不在同一年, 则显示年份
                    return MessageFormat.format(value, DateUtils.format(dateFormat, mDate));
                }
            }

            // 天数相差大于3天
            if (daySpan.days >= 2) {
                String timeScope = MessageFormat.format(pattern_DayAgo,
                        daySpan.days + 1);
                return MessageFormat.format(value, timeScope);
            }

            if (daySpan.days == 1) {
                return MessageFormat.format(value, "前天");
            }

            if (daySpan.days == 0 && mDate.getTime() < nowZero.getTime()) {  //当天凌晨00:00之前的才算昨天
                return MessageFormat.format(value, "昨天");
            }
            if (timeSpan.hours >= 1) {
                return MessageFormat.format(pattern_HoursAgo, timeSpan.hours);
            }

            if (timeSpan.minutes > 30) {
                return "半小时前";
            }

            if (timeSpan.minutes >= 1) {
                return MessageFormat.format(pattern_MinutelAgo, timeSpan.minutes);
            }
            if (timeSpan.seconds >= 1) {
                return MessageFormat.format(pattern_SecondsAgo, timeSpan.seconds);
            }
            return "刚刚";
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // 用于统计时间差
    private static class TimeSpan {
        private static int DAY_STAMP = 86400000;

        private static int HOUR_STAMP = 3600000;

        private static int MINUTES_STAMP = 60000;

        private static int SECONDS_STAMP = 1000;

        // 相差天数
        private int days;

        // 相差小时数
        private int hours;

        // 相差分数
        private int minutes;

        // 相差秒数
        private int seconds;

        private TimeSpan(Date date1, Date date2) {
            long diff = Math.abs(date1.getTime() - date2.getTime());
            days = Long.valueOf(diff / DAY_STAMP).intValue();
            hours = Long.valueOf(diff / HOUR_STAMP % 24).intValue();
            minutes = Long.valueOf(diff / MINUTES_STAMP % 60).intValue();
            seconds = Long.valueOf(diff / SECONDS_STAMP % 60).intValue();
        }
    }
}