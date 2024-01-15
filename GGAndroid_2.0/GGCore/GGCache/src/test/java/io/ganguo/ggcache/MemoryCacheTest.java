package io.ganguo.ggcache;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import io.ganguo.ggcache.memory.MemoryCache;
import io.ganguo.ggcache.memory.MemoryOption;
import io.ganguo.gtest.AndroidTestCase;
import io.ganguo.gtest.TestDes;

import static io.ganguo.ggcache.TestEntity.TEST_BOOLEAN;
import static io.ganguo.ggcache.TestEntity.TEST_CHAR;
import static io.ganguo.ggcache.TestEntity.TEST_FLOAT;
import static io.ganguo.ggcache.TestEntity.TEST_INT;
import static io.ganguo.ggcache.TestEntity.TEST_LONG;
import static io.ganguo.ggcache.TestEntity.TEST_STRING;

/**
 * memory cache test
 * Created by Lynn on 2016/12/21.
 */

@Config(packageName = "io.ganguo.ggcache")
public class MemoryCacheTest extends AndroidTestCase {
    private CountDownLatch mLatch;
    private MemoryCache mCache;

    @Before
    public void setUp() throws Exception {
        mCache = new MemoryCache(MemoryOption.defaultOption());
        mLatch = new CountDownLatch(1);
    }

    @After
    public void tearDown() throws Exception {
        mCache.close();
    }

    @Test
    @TestDes("test put / get string sync")
    public void testStringSync() throws Exception {
        final String testKey = "test_string_key";
        final String testValue = RandomString.make(100);

        final long start1 = System.currentTimeMillis();
        mCache.put(testKey, testValue);
        final String resString = mCache.getString(testKey);
        final long end1 = System.currentTimeMillis();

        asNotNull(resString);
        asEquals(testValue, resString);
        Log.d("string result", resString);
        logTime("memory string", start1, end1);
    }

    @Test
    @TestDes("test put / get byte array sync")
    public void testByteArraySync() throws Exception {
        final String testKey = "test_byte_array_key";
        final byte[] testValue = new byte[1000];
        final Random random = new Random();
        random.nextBytes(testValue);

        final long start1 = System.currentTimeMillis();
        mCache.put(testKey, testValue);
        final byte[] resByteArray = mCache.getByteArray(testKey);
        final long end1 = System.currentTimeMillis();

        asNotNull(resByteArray);
        asTrue(Arrays.equals(resByteArray, testValue));
        Log.d("byte[] result", Arrays.toString(resByteArray));
        logTime("memory byte[]", start1, end1);
    }

    @Test
    @TestDes("test put / get bitmap sync")
    public void testBitmapSync() throws Exception {
        final String testKey = "test_bitmap_key";
        final int width = 16;
        final int height = 16;
        final int[] colors = new int[width * height];

        Arrays.fill(colors, Color.BLUE);

        final Bitmap bitmapValue = Bitmap.createBitmap(colors,
                width,
                height,
                Bitmap.Config.ARGB_8888);

        final long start1 = System.currentTimeMillis();
        mCache.put(testKey, bitmapValue);
        final Bitmap resBitmap = mCache.getBitmap(testKey);
        final long end1 = System.currentTimeMillis();

        asNotNull(resBitmap);
        asTrue(bitmapValue.sameAs(resBitmap));
        Log.d("bitmap result", ShadowBitmap.visualize(resBitmap));
        logTime("memory bitmap", start1, end1);
    }

    @Test
    @TestDes("test put / get bitmap sync")
    public void testDrawableSync() throws Exception {
        final String testKey = "test_drawable_key";
        final int width = 16;
        final int height = 16;
        final int[] colors = new int[width * height];
        Arrays.fill(colors, Color.BLUE);
        final Bitmap bitmapValue = Bitmap.createBitmap(colors,
                width,
                height,
                Bitmap.Config.ARGB_8888);
        final Drawable testValue = new BitmapDrawable(getApplicationContext().getResources(), bitmapValue);

        final long start1 = System.currentTimeMillis();
        mCache.put(testKey, testValue);
        final Drawable resDrawable = mCache.getDrawable(testKey);
        final long end1 = System.currentTimeMillis();

        asNotNull(resDrawable);
        asNotNull(resDrawable.getConstantState());
        asTrue(resDrawable.getConstantState().equals(testValue.getConstantState()));
        logTime("memory drawable", start1, end1);
    }

    @Test
    @TestDes("test put / get object sync")
    public void testObjectSync() throws Exception {
        final String testKey1 = "test_entity_key";
        final TestEntity testValue1 = TestEntity.newTestEntity();

        final long start1 = System.currentTimeMillis();
        mCache.put(testKey1, testValue1);
        final TestEntity resEntity = mCache.get(testKey1, null);
        final long end1 = System.currentTimeMillis();

        asNotNull(resEntity);
        asEntity(resEntity);
        logTime("memory entity", start1, end1);

        final String testKey2 = "test_container";
        final int width = 16;
        final int height = 16;
        final int[] colors = new int[width * height];

        Arrays.fill(colors, Color.BLUE);

        final Bitmap bitmapValue = Bitmap.createBitmap(colors,
                width,
                height,
                Bitmap.Config.ARGB_8888);
        final TestContainer<Bitmap> containerValue = new TestContainer<>();
        containerValue.data = bitmapValue;
        containerValue.commonData = testValue1;
        containerValue.containerId = testKey2;

        final long start2 = System.currentTimeMillis();
        mCache.put(testKey2, containerValue);
        final TestContainer<Bitmap> resContainer = mCache.get(testKey2);
        final long end2 = System.currentTimeMillis();

        asNotNull(resContainer);
        asEquals(resContainer.containerId, testKey2);
        asEntity(resContainer.commonData);
        asTrue(resContainer.data.sameAs(bitmapValue));

        logTime("memory container", start2, end2);
    }

    @Test
    @TestDes("test remove")
    public void testRemove() throws Exception {
        final String testKey = "test_remove_key";
        final String testValue = RandomString.make(100);

        mCache.put(testKey, testValue);
        final String resString = mCache.getString(testKey);

        asNotNull(resString);
        asEquals(testValue, resString);
        Log.d("string result", resString);

        mCache.remove(testKey);
        final String resAgain = mCache.getString(testKey);
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

        mCache.put(testKey1, testValue1);
        mCache.put(testKey2, testValue2);
        mCache.put(testKey3, testValue3);

        final String resString1 = mCache.getString(testKey1);

        asNotNull(resString1);
        asEquals(testValue1, resString1);
        Log.d("string result 1", resString1);

        final String resString2 = mCache.getString(testKey2);

        asNotNull(resString2);
        asEquals(testValue2, resString2);
        Log.d("string result 2", resString2);

        final String resString3 = mCache.getString(testKey3);

        asNotNull(resString3);
        asEquals(testValue3, resString3);
        Log.d("string result 3", resString3);

        mCache.clear();

        final String resAgain1 = mCache.getString(testKey1);
        final String resAgain2 = mCache.getString(testKey2);
        final String resAgain3 = mCache.getString(testKey3);

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
        mCache.put(ranStrKey, ranStrValue, 1000);
        final String ranStrRes = mCache.getString(ranStrKey);
        final long end1 = System.currentTimeMillis();

        Log.d("expired time string", ranStrRes);
        asNotNull(ranStrRes);
        asEquals(ranStrRes, ranStrValue);
        logTime("expired time str", start1, end1);

        mLatch.await(1000, TimeUnit.MILLISECONDS);
        final String nullStrRes = mCache.getString(ranStrKey);
        asNull(nullStrRes);

        final String ranByteArrayKey = "expired_time_byte_key";
        final byte[] ranByteValue = new byte[100];
        final Random random = new Random();
        random.nextBytes(ranByteValue);

        final long start2 = System.currentTimeMillis();
        mCache.put(ranByteArrayKey, ranByteValue, 1000);
        final byte[] ranRes = mCache.getByteArray(ranByteArrayKey);
        final long end2 = System.currentTimeMillis();

        Log.d("expired time byte[]", Arrays.toString(ranRes));
        asNotNull(ranRes);
        asTrue(Arrays.equals(ranRes, ranByteValue));
        logTime("expired time byte[]", start2, end2);

        mLatch.await(1000, TimeUnit.MILLISECONDS);
        final byte[] nullByteRes = mCache.getByteArray(ranByteArrayKey);
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
