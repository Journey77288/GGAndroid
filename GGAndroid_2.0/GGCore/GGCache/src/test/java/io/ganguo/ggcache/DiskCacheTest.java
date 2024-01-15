package io.ganguo.ggcache;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import net.bytebuddy.utility.RandomString;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowBitmap;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.ganguo.ggcache.disk.DiskCache;
import io.ganguo.ggcache.disk.DiskOption;
import io.ganguo.gtest.AndroidTestCase;
import io.ganguo.gtest.TestDes;

import static io.ganguo.ggcache.TestEntity.TEST_BOOLEAN;
import static io.ganguo.ggcache.TestEntity.TEST_CHAR;
import static io.ganguo.ggcache.TestEntity.TEST_FLOAT;
import static io.ganguo.ggcache.TestEntity.TEST_INT;
import static io.ganguo.ggcache.TestEntity.TEST_LONG;
import static io.ganguo.ggcache.TestEntity.TEST_STRING;

/**
 * disk cache unit test
 * Created by Lynn on 2016/12/16.
 */

@Config(packageName = "io.ganguo.ggcache")
public class DiskCacheTest extends AndroidTestCase {
    private DiskCache mDiskCache;
    private CountDownLatch mLatch;

    @Before
    public void setUp() throws Exception {
        mDiskCache = new DiskCache(DiskOption.defaultOption(getApplicationContext()));
        mLatch = new CountDownLatch(1);
    }

    @After
    public void tearDown() throws Exception {
        mDiskCache.close();
    }

    @Test
    @TestDes("test put/get String")
    public void testStringSync() throws Exception {
        String key = "string_key";
        String value = "string_value_value_value";

        final long start1 = System.currentTimeMillis();
        mDiskCache.put(key, value);
        String res = mDiskCache.getString(key);
        final long end1 = System.currentTimeMillis();

        Log.d("testString sync", res);
        asNotNull(res);
        asEquals(res, value);
        logTime("short str", start1, end1);

        final String ranKey = "random_key";
        final String ranValue = RandomString.make(1000);

        final long start2 = System.currentTimeMillis();
        mDiskCache.put(ranKey, ranValue);
        String ranRes = mDiskCache.getString(ranKey);
        final long end2 = System.currentTimeMillis();

        Log.d("testString random sync", ranRes);
        asNotNull(ranRes);
        asEquals(ranRes, ranValue);
        logTime("long str", start2, end2);
    }


    @Test
    @TestDes("test put/get Object")
    public void testObject() throws Exception {
        //Common object
        final TestEntity entity = TestEntity.newTestEntity();
        final String entityKey = "entity_key";

        final long start = System.currentTimeMillis();
        mDiskCache.put(entityKey, entity);
        final long end = System.currentTimeMillis();

        TestEntity resEntity = mDiskCache.get(entityKey, TestEntity.class);
        Log.d("testObject ", resEntity.toString());
        asNotNull(resEntity);
        asEntity(resEntity);
        logTime("test obj", start, end);
    }

    @Test
    @TestDes("test put/get Container Object")
    @SuppressWarnings("unchecked")
    public void testContainerObject() throws Exception {
        //Common object
        final TestContainer<TestEntity> container = new TestContainer<>();
        final TestEntity entity = TestEntity.newTestEntity();
        final TestEntity commonEntity = TestEntity.newTestEntity();
        final String containerKey = "container_key";

        //wrap
        container.containerId = containerKey;
        container.commonData = commonEntity;
        container.data = entity;

        mDiskCache.put(containerKey, container);

        final TestContainer<TestEntity> resContainer = mDiskCache.get(containerKey,
                TestContainer.class, TestEntity.class);
        Log.d("container id ", resContainer.containerId);
        Log.d("container commonData", resContainer.commonData.toString());
        Log.d("container data ", resContainer.data.toString());

        asNotNull(resContainer);
        asEquals(resContainer.containerId, containerKey);
        asEntity(resContainer.commonData);
        asEntity(resContainer.data);
    }

    /**
     * 当缓存量级越大，str 和 byte[] 读写比较, 明显出现速度差异
     */
    @Test
    @TestDes("test byte[] sync")
    public void testByteArray() throws Exception {
        final String byteKey = "byte_array_key";
        final String strKey = "str_byte_key";
        // 5MB 大字节数组
        final byte[] testByteArray = new byte[1024 * 1024 * 5];
        final Random random = new Random();
        random.nextBytes(testByteArray);
        final String testStr = new String(testByteArray);

        final long startTime1 = System.currentTimeMillis();
        mDiskCache.put(byteKey, testByteArray);
        byte[] resByteArray = mDiskCache.getByteArray(byteKey);
        final long endTime1 = System.currentTimeMillis();

        final long startTime2 = System.currentTimeMillis();
        mDiskCache.put(strKey, testStr);
        String resStr = mDiskCache.getString(strKey);
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
    @TestDes("test format bitmap / drawable sync")
    public void testBitmapDrawableSync() throws Exception {
        final String bitmapKey = "test_bitmap_key";
        final int width = 16;
        final int height = 16;
        final int[] colors = new int[width * height];

        Arrays.fill(colors, Color.BLUE);

        final Bitmap bitmapValue = ShadowBitmap.createBitmap(colors,
                width,
                height,
                Bitmap.Config.ARGB_8888);

        asNotNull(bitmapValue);
        asEquals(bitmapValue.getWidth(), width);
        asEquals(bitmapValue.getHeight(), height);
        asEquals(bitmapValue.getConfig(), Bitmap.Config.ARGB_8888);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                asEquals(bitmapValue.getPixel(i, j), colors[0]);
            }
        }

        final long startTime1 = System.currentTimeMillis();
        mDiskCache.put(bitmapKey, bitmapValue);
        Bitmap resBitmap = mDiskCache.getBitmap(bitmapKey);
        final long endTime1 = System.currentTimeMillis();

        asNotNull(resBitmap);
        Log.d("bitmap detail", " bitmapValue.getByteCount(): " + BitmapUtils.getBitmapByteCount(bitmapValue));
        Log.d("bitmap resBitmap", ShadowBitmap.visualize(resBitmap) + "");
        asEquals("Bitmap for Bitmap (" + width + " x " + height + ") compressed as PNG with quality 100",
                ShadowBitmap.visualize(resBitmap));
        logTime("bitmap put/get runtime", startTime1, endTime1);
        //TODO bitmap 转换 byte[] 在test环境存在问题
    }

    @Test
    @TestDes("test remove key")
    public void testRemove() throws Exception {
        final String testKey = "test_contain_remove_key";
        final String testValue = "test_contain_remove_value";

        final long startTime1 = System.currentTimeMillis();
        mDiskCache.put(testKey, testValue);
        String resValue = mDiskCache.getString(testKey);
        final long endTime1 = System.currentTimeMillis();

        final long startTime2 = System.currentTimeMillis();
        mDiskCache.remove(testKey);
        String afteRemovedrValue = mDiskCache.getString(testKey);
        final long endTime2 = System.currentTimeMillis();

        asNotNull(resValue);
        asEquals(resValue, testValue);
        asNull(afteRemovedrValue);
        logTime("put/get string runtime", startTime1, endTime1);
        logTime("remove runtime", startTime2, endTime2);
    }

    @Test
    @TestDes("test clear all cache records")
    public void testClear() throws Exception {
        final String strKey = "test_close_string_key";
        final String byteArrayKey = "test_close_byte_array_key";
        final String objKey = "test_close_obj_key";

        final String strValue = "test_close_string_value";
        final byte[] byteValue = new byte[1000];
        Random random = new Random();
        random.nextBytes(byteValue);
        final TestEntity objValue = TestEntity.newTestEntity();

        final long startTime1 = System.currentTimeMillis();
        mDiskCache.put(strKey, strValue);
        final String resStr = mDiskCache.getString(strKey);
        final long endTime1 = System.currentTimeMillis();

        final long startTime2 = System.currentTimeMillis();
        mDiskCache.put(byteArrayKey, byteValue);
        final byte[] resBytes = mDiskCache.getByteArray(byteArrayKey);
        final long endTime2 = System.currentTimeMillis();

        final long startTime3 = System.currentTimeMillis();
        mDiskCache.put(objKey, objValue);
        final TestEntity resObj = mDiskCache.get(objKey, TestEntity.class);
        final long endTime3 = System.currentTimeMillis();

        logTime("put / get string", startTime1, endTime1);
        logTime("put / get byte[]", startTime2, endTime2);
        logTime("put / get obj", startTime3, endTime3);

        asNotNull(resStr);
        asNotNull(resBytes);
        asNotNull(resObj);

        asEquals(resStr, strValue);
        asTrue(Arrays.equals(resBytes, byteValue));
        asEntity(resObj);

        mDiskCache.clear();

        final String afterStr = mDiskCache.getString(strKey);
        final byte[] afterBytes = mDiskCache.getByteArray(byteArrayKey);
        final TestEntity afterObj = mDiskCache.get(objKey, TestEntity.class);

        asNull(afterStr);
        asNull(afterBytes);
        asNull(afterObj);

        mDiskCache.put(strKey, strValue);
        String againStr = mDiskCache.getString(strKey);
        asNotNull(againStr);
        asEquals(againStr, strValue);
    }

    @Test
    @TestDes("test for expired time")
    public void testExpiredTime() throws Exception {
        final String ranStrKey = "expired_time_string_key";
        final String ranStrValue = RandomString.make(1000);

        final long start1 = System.currentTimeMillis();
        mDiskCache.put(ranStrKey, ranStrValue, 1000);
        final String ranStrRes = mDiskCache.getString(ranStrKey);
        final long end1 = System.currentTimeMillis();

        Log.d("expired time string", ranStrRes);
        asNotNull(ranStrRes);
        asEquals(ranStrRes, ranStrValue);
        logTime("expired time str", start1, end1);

        mLatch.await(1000, TimeUnit.MILLISECONDS);
        final String nullStrRes = mDiskCache.getString(ranStrKey);
        asNull(nullStrRes);

        final String ranByteArrayKey = "expired_time_byte_key";
        final byte[] ranByteValue = new byte[1000];
        final Random random = new Random();
        random.nextBytes(ranByteValue);

        final long start2 = System.currentTimeMillis();
        mDiskCache.put(ranByteArrayKey, ranByteValue, 1000);
        final byte[] ranRes = mDiskCache.getByteArray(ranByteArrayKey);
        final long end2 = System.currentTimeMillis();

        Log.d("expired time byte[]", Arrays.toString(ranRes));
        asNotNull(ranRes);
        asTrue(Arrays.equals(ranRes, ranByteValue));
        logTime("expired time byte[]", start2, end2);

        mLatch.await(1000, TimeUnit.MILLISECONDS);
        final byte[] nullByteRes = mDiskCache.getByteArray(ranByteArrayKey);
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
