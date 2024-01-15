package io.ganguo.utils.util.json;

import com.google.gson.JsonElement;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.utils.util.date.Date;
import io.ganguo.utils.util.date.DateFormatter;

/**
 * 单元测试 - date formatter
 * Created by Lynn on 10/26/16.
 */

public class DateFormatterTest extends BaseTestCase {
    @Test
    @TestDes("test DateFormatter")
    public void testDateFormatter() {
        DateFormatter dateFormatter = new DateFormatter();
        Date date = Date.parseFor("2016-10-26");

        JsonElement jsonElement = dateFormatter.serialize(date, null, null);
        asEquals(date, dateFormatter.deserialize(jsonElement, null, null));

        jsonElement = dateFormatter.serialize(date);
        asEquals(date, dateFormatter.deserialize(jsonElement));
    }
}
