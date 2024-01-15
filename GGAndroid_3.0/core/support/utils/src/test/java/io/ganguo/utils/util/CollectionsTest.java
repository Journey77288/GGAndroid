package io.ganguo.utils.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.ganguo.gtest.BaseTestCase;
import io.ganguo.gtest.TestDes;
import io.ganguo.utils.util.Collections;

/**
 * 单元测试 - Collections 工具类
 * Created by Lynn on 10/21/16.
 */

public class CollectionsTest extends BaseTestCase {
    @Test
    @TestDes("测试Collections isEmpty方法")
    public void testEmpty() throws Exception {
        //null case
        Object[] nullArrays = null;
        Collection<Object> nullCollections = null;
        Map<Object, Object> nullMap = null;

        asTrue(Collections.isEmpty(nullArrays));
        asTrue(Collections.isEmpty(nullCollections));
        asTrue(Collections.isEmpty(nullMap));

        //empty case
        Object[] emptyArrays = new Object[0];
        Collection<Object> emptyCollections = new ArrayList<>();
        Map<Object, Object> emptyMap = new HashMap<>();

        asTrue(Collections.isEmpty(emptyArrays));
        asTrue(Collections.isEmpty(emptyCollections));
        asTrue(Collections.isEmpty(emptyMap));

        //common case
        Object[] arrays = new Object[]{1};
        Collection<Object> collections = new ArrayList<>();
        collections.add(1);
        Map<Object, Object> map = new HashMap<>();
        map.put(1, 1);

        asFalse(Collections.isEmpty(arrays));
        asFalse(Collections.isEmpty(collections));
        asFalse(Collections.isEmpty(map));
    }

    /**
     * 对应empty的测试用例
     *
     * @throws Exception
     */
    @Test
    @TestDes("测试Collections isNotEmpty方法")
    public void testNotEmpty() throws Exception {
        //null case
        Collection<Object> nullCollections = null;
        Map<Object, Object> nullMap = null;

        asFalse(Collections.isNotEmpty(nullCollections));
        asFalse(Collections.isNotEmpty(nullMap));

        //empty case
        Collection<Object> emptyCollections = new ArrayList<>();
        Map<Object, Object> emptyMap = new HashMap<>();

        asFalse(Collections.isNotEmpty(emptyCollections));
        asFalse(Collections.isNotEmpty(emptyMap));

        //common case
        Collection<Object> collections = new ArrayList<>();
        collections.add(1);
        Map<Object, Object> map = new HashMap<>();
        map.put(1, 1);

        asTrue(Collections.isNotEmpty(collections));
        asTrue(Collections.isNotEmpty(map));
    }
}
