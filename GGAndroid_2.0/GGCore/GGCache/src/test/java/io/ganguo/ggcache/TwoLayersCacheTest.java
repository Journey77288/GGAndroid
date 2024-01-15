package io.ganguo.ggcache;

import android.util.Log;

import net.bytebuddy.utility.RandomString;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.ganguo.gtest.AndroidTestCase;
import io.ganguo.gtest.TestDes;

import static io.ganguo.ggcache.TestEntity.TEST_BOOLEAN;
import static io.ganguo.ggcache.TestEntity.TEST_CHAR;
import static io.ganguo.ggcache.TestEntity.TEST_FLOAT;
import static io.ganguo.ggcache.TestEntity.TEST_INT;
import static io.ganguo.ggcache.TestEntity.TEST_LONG;
import static io.ganguo.ggcache.TestEntity.TEST_STRING;

/**
 * two layers cache unit test
 * Created by Lynn on 2016/12/23.
 */

@Config(packageName = "io.ganguo.ggcache")
public class TwoLayersCacheTest extends AndroidTestCase {
    private CountDownLatch mLatch;
    private TwoLayersCache mTwoLayersCache;

    @Before
    public void setUp() throws Exception {
        mTwoLayersCache = new TwoLayersCache(getApplicationContext());
        mLatch = new CountDownLatch(1);
    }

    @After
    public void tearDown() throws Exception {
        mTwoLayersCache.close();
    }

    @Test
    @TestDes("test put / get string sync")
    public void testStringSync() throws Exception {
        final String testKey = "test_string_key";
        final String testValue = RandomString.make(100);

        final long start1 = System.currentTimeMillis();
        mTwoLayersCache.put(testKey, testValue);
        final String resString = mTwoLayersCache.getString(testKey);
        final long end1 = System.currentTimeMillis();

        asNotNull(resString);
        asEquals(testValue, resString);
        Log.d("string result", resString);
        logTime("memory string", start1, end1);
    }

    @Test
    @TestDes("test put/get Object")
    public void testObject() throws Exception {
        final TestEntity entity = TestEntity.newTestEntity();
        final String entityKey = "entity_key";

        final long start = System.currentTimeMillis();
        mTwoLayersCache.put(entityKey, entity);
        final long end = System.currentTimeMillis();

        TestEntity resEntity = mTwoLayersCache.get(entityKey, TestEntity.class);
        Log.d("testObject ", resEntity.toString());
        asNotNull(resEntity);
        asEntity(resEntity);
        logTime("test obj", start, end);
    }

    @Test
    @TestDes("test byte[] sync")
    public void testByteArraySync() throws Exception {
        final String byteKey = "byte_array_key";
        final String strKey = "str_byte_key";
        // 5MB 大字节数组
        final byte[] testByteArray = new byte[1024 * 1024 * 5];
        final Random random = new Random();
        random.nextBytes(testByteArray);
        final String testStr = new String(testByteArray);

        final long startTime1 = System.currentTimeMillis();
        mTwoLayersCache.put(byteKey, testByteArray);
        byte[] resByteArray = mTwoLayersCache.getByteArray(byteKey);
        final long endTime1 = System.currentTimeMillis();

        final long startTime2 = System.currentTimeMillis();
        mTwoLayersCache.put(strKey, testStr);
        String resStr = mTwoLayersCache.getString(strKey);
        final long endTime2 = System.currentTimeMillis();

        asNotNull(resByteArray);
        asNotNull(resStr);
        asTrue(Arrays.equals(resByteArray, testByteArray));
        asEquals(resStr, testStr);

        Log.d("byte[] count", "byte array: " + resByteArray.length + " string: " + resStr.length());
        logTime("byte[] runtime", startTime1, endTime1);
        logTime("string runtime", startTime2, endTime2);
    }

    @Test
    @TestDes("test remove")
    public void testRemove() throws Exception {
        final String testKey = "test_remove_key";
        final String testValue = RandomString.make(100);

        mTwoLayersCache.put(testKey, testValue);
        final String resString = mTwoLayersCache.getString(testKey);

        asNotNull(resString);
        asEquals(testValue, resString);
        Log.d("string result", resString);

        mTwoLayersCache.remove(testKey);
        final String resAgain = mTwoLayersCache.getString(testKey);
        asNull(resAgain);
    }

    @Test
    @TestDes("test clear")
    public void testClear() throws Exception {
        final String testKey1 = "test_remove_key";
        final String testValue1 = RandomString.make(100);

        final String testKey2 = "test_remove_key2";
        final String testValue2 = RandomString.make(100);

        final String testKey3 = "test_remove_key3";
        final String testValue3 = RandomString.make(100);

        mTwoLayersCache.put(testKey1, testValue1);
        mTwoLayersCache.put(testKey2, testValue2);
        mTwoLayersCache.put(testKey3, testValue3);

        final String resString1 = mTwoLayersCache.getString(testKey1);

        asNotNull(resString1);
        asEquals(testValue1, resString1);
        Log.d("string result 1", resString1);

        final String resString2 = mTwoLayersCache.getString(testKey2);

        asNotNull(resString2);
        asEquals(testValue2, resString2);
        Log.d("string result 2", resString2);

        final String resString3 = mTwoLayersCache.getString(testKey3);

        asNotNull(resString3);
        asEquals(testValue3, resString3);
        Log.d("string result 3", resString3);

        mTwoLayersCache.clear();

        final String resAgain1 = mTwoLayersCache.getString(testKey1);
        final String resAgain2 = mTwoLayersCache.getString(testKey2);
        final String resAgain3 = mTwoLayersCache.getString(testKey3);

        asNull(resAgain1);
        asNull(resAgain2);
        asNull(resAgain3);
    }

    @Test
    @TestDes("test for expired time")
    public void testExpiredTime() throws Exception {
        final String ranStrKey = "expired_time_string_key";
        final String ranStrValue = RandomString.make(100);

        final long start1 = System.currentTimeMillis();
        mTwoLayersCache.put(ranStrKey, ranStrValue, 1000);
        final String ranStrRes = mTwoLayersCache.getString(ranStrKey);
        final long end1 = System.currentTimeMillis();

        Log.d("expired time string", ranStrRes);
        asNotNull(ranStrRes);
        asEquals(ranStrRes, ranStrValue);
        logTime("expired time str", start1, end1);

        mLatch.await(1000, TimeUnit.MILLISECONDS);
        final String nullStrRes = mTwoLayersCache.getString(ranStrKey);
        asNull(nullStrRes);

        final String ranByteArrayKey = "expired_time_byte_key";
        final byte[] ranByteValue = new byte[100];
        final Random random = new Random();
        random.nextBytes(ranByteValue);

        final long start2 = System.currentTimeMillis();
        mTwoLayersCache.put(ranByteArrayKey, ranByteValue, 1000);
        final byte[] ranRes = mTwoLayersCache.getByteArray(ranByteArrayKey);
        final long end2 = System.currentTimeMillis();

        Log.d("expired time byte[]", Arrays.toString(ranRes));
        asNotNull(ranRes);
        asTrue(Arrays.equals(ranRes, ranByteValue));
        logTime("expired time byte[]", start2, end2);

        mLatch.await(1000, TimeUnit.MILLISECONDS);
        final byte[] nullByteRes = mTwoLayersCache.getByteArray(ranByteArrayKey);
        asNull(nullByteRes);
    }


    private void asEntity(TestEntity entity) {
        asEquals(entity.intValue, TEST_INT);
        asEquals(entity.longValue, TEST_LONG);
        asEquals(entity.charValue, TEST_CHAR);
        asEquals(entity.floatValue, TEST_FLOAT);
        asEquals(entity.booleanValue, TEST_BOOLEAN);
        asEquals(entity.strValue, TEST_STRING);
    }
}
