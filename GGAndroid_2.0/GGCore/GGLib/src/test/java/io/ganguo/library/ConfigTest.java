package io.ganguo.library;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import io.ganguo.gtest.TestDes;

/**
 * 单元测试 Config
 * Created by Lynn on 10/28/16.
 */

public class ConfigTest extends GGLibraryTestBase {

    @Before
    public void setup() {
    }

    @Test
    @TestDes("test Config getSharedPreferences")
    public void testGetDefaultSP() {
        SharedPreferences sharedPreferences = Config.getSharedPreferences();
        asNotNull(sharedPreferences);
    }

    @Test
    @TestDes("test SharedPreferences getString / putString")
    public void testPutAndGetStringOfSP() {
        final String key = "key";
        final String wrongKey = "wrong";
        final String value = "value";

        Config.putString(key, value);

        asEquals(value, Config.getString(key));
        asNull(Config.getString(wrongKey));
    }

    @Test
    @TestDes("test SharedPreferences getInt / putInt")
    public void testPutAndGetIntOfSP() {
        final String key = "key";
        final String wrongKey = "wrong";

        final int value = 1;
        final int defValue = 0;

        Config.putInt(key, value);

        asEquals(value, Config.getInt(key, defValue));
        asEquals(defValue, Config.getInt(wrongKey, defValue));
    }

    @Test
    @TestDes("test SharedPreferences getLong / putLong")
    public void testPutAndGetLongOfSP() {
        final String key = "key";
        final String wrongKey = "wrong";

        final long value = 1L;
        final long defValue = 0L;

        Config.putLong(key, value);

        asEquals(value, Config.getLong(key, defValue));
        asEquals(defValue, Config.getLong(wrongKey, defValue));
    }

    @Test
    @TestDes("test SharedPreferences getBoolean / putBoolean")
    public void testPutAndGetBooleanOfSP() {
        final String key = "key";
        final String wrongKey = "wrong";

        final boolean value = true;
        final boolean defValue = false;

        Config.putBoolean(key, value);

        asTrue(Config.getBoolean(key, defValue));
        asFalse(Config.getBoolean(wrongKey, defValue));
    }

    @Test
    @TestDes("test SharedPreferences containsKey")
    public void testContainsKey() {
        final String stringKey = "stringKey";
        final String intKey = "intKey";
        final String longKey = "longKey";
        final String booleanKey = "booleanKey";
        final String wrongKey = "wrongKey";

        Config.putString(stringKey, "");
        Config.putInt(intKey, 1);
        Config.putLong(longKey, 1L);
        Config.putBoolean(booleanKey, true);

        asTrue(Config.containsKey(stringKey));
        asTrue(Config.containsKey(intKey));
        asTrue(Config.containsKey(longKey));
        asTrue(Config.containsKey(booleanKey));
        asFalse(Config.containsKey(wrongKey));
    }

    @Test
    @TestDes("test SharedPreferences remove")
    public void testRemove() {
        final String stringKey = "stringKey";
        final String intKey = "intKey";
        final String longKey = "longKey";
        final String booleanKey = "booleanKey";

        Config.putString(stringKey, "");
        Config.putInt(intKey, 1);
        Config.putLong(longKey, 1L);
        Config.putBoolean(booleanKey, true);

        asTrue(Config.containsKey(stringKey));
        asTrue(Config.containsKey(intKey));
        asTrue(Config.containsKey(longKey));
        asTrue(Config.containsKey(booleanKey));

        Config.remove(stringKey);

        asFalse(Config.containsKey(stringKey));

        Config.remove(intKey, longKey);

        asFalse(Config.containsKey(intKey));
        asFalse(Config.containsKey(longKey));
        asTrue(Config.containsKey(booleanKey));
    }


    @Test
    @TestDes("test SharedPreferences clearAll")
    public void testClearAll() {
        final String stringKey = "stringKey";
        final String intKey = "intKey";
        final String longKey = "longKey";
        final String booleanKey = "booleanKey";

        Config.putString(stringKey, "");
        Config.putInt(intKey, 1);
        Config.putLong(longKey, 1L);
        Config.putBoolean(booleanKey, true);

        asTrue(Config.containsKey(stringKey));
        asTrue(Config.containsKey(intKey));
        asTrue(Config.containsKey(longKey));
        asTrue(Config.containsKey(booleanKey));

        Config.clearAll();

        asFalse(Config.containsKey(stringKey));
        asFalse(Config.containsKey(intKey));
        asFalse(Config.containsKey(longKey));
        asFalse(Config.containsKey(booleanKey));
    }

    @Test
    @TestDes("test getPath")
    public void testGetPath() {
        String prefixPath = getApplicationContext().getCacheDir() + File.separator;

        asEquals(prefixPath + Config.DATA_PATH,
                Config.getDataPath().toString());

        asEquals(prefixPath + Config.DATA_PATH + File.separator + Config.IMAGES_PATH,
                Config.getImagePath().toString());

        asEquals(prefixPath + Config.DATA_PATH + File.separator + Config.IMAGE_CACHE_PATH,
                Config.getImageCachePath().toString());

        asEquals(prefixPath + Config.DATA_PATH + File.separator + Config.APP_LOG_PATH,
                Config.getLogPath().toString());

        asEquals(prefixPath + Config.DATA_PATH + File.separator + Config.APP_TEMP_PATH,
                Config.getTempPath().toString());
    }
}
