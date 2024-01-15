package io.ganguo.utils.util.json;

import com.google.gson.JsonElement;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.utils.util.date.DateTimeZone;
import io.ganguo.utils.util.date.DateTimeZoneFormatter;

/**
 * 单元测试 DateTimeZoneFormatter
 * Created by Lynn on 10/26/16.
 */

public class DateTimeZoneFormatterTest extends BaseTestCase {
    @Test
    @TestDes("test DateTimeZoneFormatter")
    public void testDateTimeZoneFormatter() {
        DateTimeZoneFormatter dateTimeZoneFormatter = new DateTimeZoneFormatter();
        //+0800 格林威治 时间 东(正)八区 -> 北京时间
        //Z代表UTC统一时间, 需要设置时区
        final String time = "2016-10-26T14:08:05+0800";
        DateTimeZone dateTimeZone = DateTimeZone.parseFor(time);

        JsonElement jsonElement = dateTimeZoneFormatter.serialize(dateTimeZone, null, null);
        asEquals(time, DateTimeZone.formatFor(dateTimeZoneFormatter.deserialize(jsonElement, null, null)));

        jsonElement = dateTimeZoneFormatter.serialize(dateTimeZone);
        asEquals(time, DateTimeZone.formatFor(dateTimeZoneFormatter.deserialize(jsonElement)));
    }
}
