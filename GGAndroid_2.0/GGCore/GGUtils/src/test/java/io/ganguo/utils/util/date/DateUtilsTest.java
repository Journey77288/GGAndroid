package io.ganguo.utils.util.date;

import org.junit.Before;
import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 DateUtils
 * Created by Lynn on 10/25/16.
 */

public class DateUtilsTest extends BaseTestCase {
    @Before
    public void init() {
    }

    @Test
    @TestDes("test isSameWeekWithToday")
    public void testIsSameWeekWithToday() {
        Date today = new Date(System.currentTimeMillis());
        Date beforeWeek = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        asTrue(DateUtils.isSameWeekWithToday(today));
        asFalse(DateUtils.isSameWeekWithToday(beforeWeek));
    }

    @Test
    @TestDes("test isBetweenWithToday")
    public void testIsBetweenWithToday() {
        Date before = new Date(System.currentTimeMillis() - 60 * 1000);
        Date after = new Date(System.currentTimeMillis() + 60 * 1000);
        Date littleAfter = new Date(System.currentTimeMillis() + 1000);

        asTrue(DateUtils.isBetweenWithToday(before, after));
        asFalse(DateUtils.isBetweenWithToday(littleAfter, after));
    }

    @Test
    @TestDes("test getTimeOnWeek")
    public void testGetTimeOnWeek() {
        DateTime date = DateTime.parseFor("2016-10-22 15:00:00");
        asEquals(DateUtils.getTimeOnWeek(date), "2016-10-22 15:00");
    }

    @Test
    @TestDes("test timeDiffFromNow 现在距离目标时间还有多久")
    public void testTimeDiffFromNow() {
        //10秒后， 下面的额外的100ms， 用于来抵消运行误差
        final int sec = 1000;
        final int min = 60 * sec;
        final int hor = 60 * min;
        final int dy = 24 * hor;
        final int runtime = 100;

        //10秒后
        final Date second = new Date(System.currentTimeMillis() + 10 * sec + runtime);
        //10分钟后10秒
        final Date minute = new Date(System.currentTimeMillis() + 10 * min + 10 * sec + runtime);
        //10小时10分钟10秒
        final Date hour = new Date(System.currentTimeMillis() + 10 * hor + 10 * min + 10 * sec + runtime);
        //10天10小时10分钟10秒
        final Date day = new Date(System.currentTimeMillis() + 10 * dy + 10 * hor + 10 * min + 10 * sec + runtime);
        String[] format = new String[]{"天", "时", "分", "秒"};

        asEquals(DateUtils.timeDiffFromNow(second, format), "10秒");
        asEquals(DateUtils.timeDiffFromNow(minute, format), "10分10秒");
        asEquals(DateUtils.timeDiffFromNow(hour, format), "10时10分10秒");
        asEquals(DateUtils.timeDiffFromNow(day, format), "10天10时10分10秒");
    }

    @Test
    @TestDes("test timeDiffToNow 目标时间距离现在有多久")
    public void testTimeDiffToNow() {
        //10秒后， 下面的额外的100ms， 用于来抵消运行误差
        final int sec = 1000;
        final int min = 60 * sec;
        final int hor = 60 * min;
        final int dy = 24 * hor;
        final int runtime = 100;

        //10秒后
        final Date second = new Date(System.currentTimeMillis() - (10 * sec + runtime));
        //10分钟后10秒
        final Date minute = new Date(System.currentTimeMillis() - (10 * min + 10 * sec + runtime));
        //10小时10分钟10秒
        final Date hour = new Date(System.currentTimeMillis() - (10 * hor + 10 * min + 10 * sec + runtime));
        //10天10小时10分钟10秒
        final Date day = new Date(System.currentTimeMillis() - (10 * dy + 10 * hor + 10 * min + 10 * sec + runtime));
        String[] format = new String[]{"天", "时", "分", "秒"};

        asEquals(DateUtils.timeDiffToNow(second, format), "10秒");
        asEquals(DateUtils.timeDiffToNow(minute, format), "10分10秒");
        asEquals(DateUtils.timeDiffToNow(hour, format), "10时10分10秒");
        asEquals(DateUtils.timeDiffToNow(day, format), "10天10时10分10秒");
    }
}
