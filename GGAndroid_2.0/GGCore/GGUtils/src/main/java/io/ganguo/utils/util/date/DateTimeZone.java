package io.ganguo.utils.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日期时间类
 * yyyy-MM-dd'T'HH:mm:ssZ
 * <p/>
 * Created by Tony on 1/5/15.
 */
public class DateTimeZone extends BaseDate {
    public final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
    public final static SimpleDateFormat FORMATTER_CN = new SimpleDateFormat("yyyy年MM月dd日'T'HH:mm:ssZ", Locale.US);

    /**
     * string to date
     *
     * @param string
     * @return
     */
    public synchronized static DateTimeZone parseFor(String string) {
        java.util.Date date = null;
        try {
            date = FORMATTER.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new DateTimeZone(date);
    }

    /**
     * date to string
     *
     * @param date
     * @return
     */
    public synchronized static String formatFor(BaseDate date) {
        return FORMATTER.format(date);
    }

    // 构造方法
    public DateTimeZone() {
        super();
    }

    public DateTimeZone(long time) {
        super(time);
    }

    public DateTimeZone(java.util.Date date) {
        super(date.getTime());
    }
}
