package io.ganguo.utils.util.date;

import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 Date相关
 * Created by Lynn on 10/25/16.
 */

public class DateTest extends BaseTestCase {
    @Test
    @TestDes("test 自定义Date")
    public void testDate() {
        Date date = Date.parseFor("2016-10-25");
        asEquals(Date.formatFor(date), "2016-10-25");
    }

    @Test
    @TestDes("test DateTime")
    public void testDateTime() {
        DateTime date = DateTime.parseFor("2016-10-25 23:12:01");
        asEquals(DateTime.formatFor(date), "2016-10-25 23:12:01");
    }

    @Test
    @TestDes("test DateTimeZone")
    public void testDateTimeZone() {
        DateTimeZone date = DateTimeZone.parseFor("2016-10-25T23:12:01+0800");
        asEquals(DateTimeZone.formatFor(date), "2016-10-25T23:12:01+0800");
    }

    @Test
    @TestDes("test BaseDate")
    public void testBaseDate() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        //24小时制
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 8);
        calendar.set(Calendar.SECOND, 25);
        BaseDate baseDate = new BaseDate(calendar.getTime());

        asEquals(baseDate.toDate(), "2016-10-25");
        asEquals(baseDate.toDateCN(), "2016年10月25日");
        asEquals(baseDate.toDateTime(), "2016-10-25 03:08:25");
        asEquals(baseDate.toDateTimeCN(), "2016年10月25日 03:08:25");

        Calendar now = Calendar.getInstance();

        if (now.getTimeInMillis() < calendar.getTimeInMillis() ||
                now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            //或者转换时间比当前时间晚
            //同一年内, 不显示年份
            asEquals(baseDate.toFriendly(false), "10月25日");
            asEquals(baseDate.toFriendly(true), "10月25日 03:08");
        } else {
            asEquals(baseDate.toFriendly(false), "2016年10月25日");
            asEquals(baseDate.toFriendly(true), "2016年10月25日 03:08");
        }
    }
}
