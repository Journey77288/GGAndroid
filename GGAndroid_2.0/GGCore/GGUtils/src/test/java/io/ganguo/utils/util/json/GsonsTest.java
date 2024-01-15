package io.ganguo.utils.util.json;

import com.google.gson.annotations.SerializedName;

import org.junit.Test;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - Gsons
 * Created by Lynn on 10/25/16.
 */

public class GsonsTest extends BaseTestCase {

    @Test
    @TestDes("test Gsons ToJson")
    public void testToJson() {
        JsonExample jsonExample = new JsonExample();
        jsonExample.stringField = "abc";
        jsonExample.intField = 1;

        String jsonString = "{\"stringField\":\"abc\",\"int_field\":1}";
        String toJson = Gsons.toJson(jsonExample);

        asEquals(toJson, jsonString);
    }

    @Test
    @TestDes("test Gsons FromJson")
    public void testFromJson() {
        String stringValue = "abcc";
        int intValue = 11;
        String jsonString = "{\"stringField\":\"" + stringValue + "\",\"int_field\":" + intValue + "}";

        JsonExample example = Gsons.fromJson(jsonString, JsonExample.class);
        asEquals(example.stringField, stringValue);
        asEquals(example.intField, intValue);
    }

    @Test
    @TestDes("test Gsons toJson 考虑null")
    public void testToJsonNull() {
        JsonExample jsonExample = new JsonExample();
        jsonExample.stringField = null;
        jsonExample.intField = 1;

        String jsonString = "{\"stringField\":null,\"int_field\":1}";
        String toJson = Gsons.toJson(jsonExample);

        asEquals(toJson, jsonString);
    }

    @Test
    @TestDes("test Gsons fromJson 考虑null")
    public void testFromJsonNull() {
        int intValue = 11;
        String jsonString = "{\"stringField\":null,\"int_field\":" + intValue + "}";

        JsonExample example = Gsons.fromJson(jsonString, JsonExample.class);
        asNull(example.stringField);
        asEquals(example.intField, intValue);
    }

    /**
     * 测试用例的对象
     */
    class JsonExample {
        //一般建议使用SerializedName指定key,
        @SerializedName("stringField")
        public String stringField;
        //区别可以通过修改测试用例，报错信息中看出,
        //默认是驼峰变成下划线，大写首字母变小写,
        //int_field
        public int intField;
    }
}
