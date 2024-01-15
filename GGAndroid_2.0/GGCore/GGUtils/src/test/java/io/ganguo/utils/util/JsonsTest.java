package io.ganguo.utils.util;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.utils.util.json.Jsons;

/**
 * 单元测试 - Jsons
 * Created by Lynn on 10/24/16.
 */

public class JsonsTest extends BaseTestCase {
    @Before
    public void init() {
        Jsons.isPrintException = false;
    }

    @Test
    @TestDes("test Jsons getLong")
    public void testGetLong() throws Exception {
        final String key = "value";
        final long value = 1000L;
        final String longValue = "{ " + key + " : " + value + " }";

        //TODO mock method
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        Mockito.when(mockJson.getLong(key)).thenReturn(value);

        asEquals(Jsons.getLong(mockJson, key, 1L), value);
        String s = null;
        asEquals(Jsons.getLong(s, key, 1L), 1L);
//        asEquals(Jsons.getLong(longValue, key, 1L), 1000L);
    }

    @Test
    @TestDes("test Jsons getInt")
    public void testGetInt() throws Exception {
        final String key = "value";
        final int value = 1000;
        final String intValue = "{ " + key + " : " + value + " }";

        //TODO mock method
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        Mockito.when(mockJson.getInt(key)).thenReturn(value);

        asEquals(Jsons.getInt(mockJson, key, 1), value);
        String s = null;
        asEquals(Jsons.getInt(s, key, 1), 1);
//        asEquals(Jsons.getLong(longValue, key, 1L), 1000L);
    }

    @Test
    @TestDes("test Jsons getDouble")
    public void testGetDouble() throws Exception {
        final String key = "value";
        final double value = 1000D;
        final String doubleValue = "{ " + key + " : " + value + " }";

        //TODO mock method
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        Mockito.when(mockJson.getDouble(key)).thenReturn(value);

        asEquals(Jsons.getDouble(mockJson, key, 1D), value);
        String s = null;
        asEquals(Jsons.getDouble(s, key, 1D), 1D);
//        asEquals(Jsons.getLong(longValue, key, 1L), 1000L);
    }

    @Test
    @TestDes("test Jsons getString")
    public void testGetString() throws Exception {
        final String key = "value";
        final String value = "abcd";
        final String strValue = "{ " + key + " : " + value + " }";

        //TODO mock method
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        Mockito.when(mockJson.getString(key)).thenReturn(value);

        asEquals(Jsons.getString(mockJson, key, "default"), value);
        String s = null;
        asEquals(Jsons.getString(s, key, "default"), "default");
//        asEquals(Jsons.getLong(longValue, key, 1L), 1000L);
    }

    @Test
    @TestDes("test Jsons containsKey")
    public void testContainsKey() {
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        String trueKey = "true";
        String falseKey = "false";
        Mockito.when(Jsons.containsKey(mockJson, trueKey)).thenReturn(true);
        Mockito.when(Jsons.containsKey(mockJson, falseKey)).thenReturn(false);

        asTrue(Jsons.containsKey(mockJson, trueKey));
        asFalse(Jsons.containsKey(mockJson, falseKey));
    }
}
