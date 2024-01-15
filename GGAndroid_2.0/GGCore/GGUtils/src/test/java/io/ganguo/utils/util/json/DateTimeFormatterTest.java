package io.ganguo.utils.util.json;

import com.google.gson.JsonElement;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.utils.util.date.DateTime;
import io.ganguo.utils.util.date.DateTimeFormatter;

/**
 * 单元测试 DateTimeFormatter
 * Created by Lynn on 10/26/16.
 */

public class DateTimeFormatterTest extends BaseTestCase {
    @Test
    @TestDes("Test DateTimeFormatter")
    public void testDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter();

        final String time = "2016-10-25 14:03:01";
        DateTime dateTime = DateTime.parseFor(time);
        JsonElement jsonElement = dateTimeFormatter.serialize(dateTime, null, null);
        asEquals(time, dateTimeFormatter.deserialize(jsonElement, null, null).toDateTime());

        jsonElement = dateTimeFormatter.serialize(dateTime);
        asEquals(time, dateTimeFormatter.deserialize(jsonElement).toDateTime());
    }
}
