package io.ganguo.utils.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 Validates
 * Created by Lynn on 10/26/16.
 */

public class ValidatesTest extends BaseTestCase {
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
    private static final String DEFAULT_NOT_EMPTY_STRING_EX_MESSAGE = "The validated string is empty";
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";

    /**
     * 表示抛出的异常是否符合预期
     */
    @Test
    @TestDes("test isTrue")
    public void testIsTrue() {
        expect(IllegalArgumentException.class, DEFAULT_IS_TRUE_EX_MESSAGE);
        Validates.isTrue(false);
    }

    @Test
    @TestDes("test NotNull 不带参数")
    public void testNotNull() {
        expect(NullPointerException.class, DEFAULT_IS_NULL_EX_MESSAGE);
        Validates.notNull(null);
    }

    @Test
    @TestDes("test NotNull 带参数")
    public void testNotNullWithArgs() {
        expect(NullPointerException.class, "测试带参: %s%d", "传入参数", 0);
        Validates.notNull(null, "测试带参: %s%d", "传入参数", 0);
    }

    @Test
    @TestDes("test NotEmpty 不带参")
    public void testNotEmpty() {
        expect(NullPointerException.class, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
        //array
        Object[] testArray = null;
        Validates.notEmpty(testArray);

        testArray = new Object[1];
        expect(IllegalArgumentException.class, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
        Validates.notEmpty(testArray);
        //collections
        List<Object> testCollections = null;
        expect(NullPointerException.class, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
        Validates.notEmpty(testCollections);

        testCollections = new ArrayList<>();
        expect(IllegalArgumentException.class, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
        Validates.notEmpty(testArray);
        //map
        Map<Object, Object> testMap = null;
        expect(NullPointerException.class, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
        Validates.notEmpty(testMap);

        testMap = new HashMap<>();
        expect(IllegalArgumentException.class, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
        Validates.notEmpty(testMap);

        //string
        String str = null;
        expect(NullPointerException.class, DEFAULT_NOT_EMPTY_STRING_EX_MESSAGE);
        Validates.notEmpty(str);

        str = "";
        expect(IllegalArgumentException.class, DEFAULT_NOT_EMPTY_STRING_EX_MESSAGE);
        Validates.notEmpty(str);
    }

    @Test
    @TestDes("test NotEmpty 带参")
    public void testNotEmptyWithArgs() {
        expect(NullPointerException.class, "测试带参: %s", "array");
        //array
        Object[] testArray = null;
        Validates.notEmpty(testArray, "测试带参: %s", "array");

        testArray = new Object[1];
        expect(IllegalArgumentException.class, "测试带参: %s", "array");
        Validates.notEmpty(testArray, "测试带参: %s", "array");
        //collections
        List<Object> testCollections = null;
        expect(NullPointerException.class, "测试带参: %s", "collections");
        Validates.notEmpty(testCollections, "测试带参: %s", "collections");

        testCollections = new ArrayList<>();
        expect(IllegalArgumentException.class, "测试带参: %s", "collections");
        Validates.notEmpty(testArray, "测试带参: %s", "collections");

        //map
        Map<Object, Object> testMap = null;
        expect(NullPointerException.class, "测试带参: %s", "map");
        Validates.notEmpty(testMap, "测试带参: %s", "map");

        testMap = new HashMap<>();
        expect(IllegalArgumentException.class, "测试带参: %s", "map");
        Validates.notEmpty(testMap, "测试带参: %s", "map");

        //string
        String str = null;
        expect(NullPointerException.class, "测试带参: %s", "string");
        Validates.notEmpty(str, "测试带参: %s", "string");

        str = "";
        expect(IllegalArgumentException.class, "测试带参: %s", "string");
        Validates.notEmpty(str, "测试带参: %s", "string");
    }
}
