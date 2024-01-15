package io.ganguo.ggcache;

import android.util.Log;

import org.junit.Test;
import org.robolectric.annotation.Config;

import io.ganguo.gtest.AndroidTestCase;
import io.ganguo.gtest.TestDes;

/**
 * GGCache helper class unit test
 * Created by Lynn on 2016/12/24.
 */

@Config(packageName = "io.ganguo.ggcache")
public class GGCacheTest extends AndroidTestCase {
    @Test
    @TestDes("test two layers cache")
    public void testTwolayersCache() throws Exception {
        asNull(GGCache.cache());
        GGCache.init(getApplicationContext());

        final String key = "test_key";
        final String value = "test_value";

        GGCache.cache().put(key, value);
        final String str = GGCache.cache().getString(key);

        asNotNull(str);
        asEquals(str, value);

        //memory remove key
        GGCache.memory().remove(key);
        //Note: 如果再次获取, value 会尝试加到 memory cache中
        final String strNotMemory = GGCache.cache().getString(key);
        asNotNull(strNotMemory);
        asEquals(strNotMemory, value);

        //disk remove key
        GGCache.disk().remove(key);
        //still here
        final String strNotDisk = GGCache.cache().getString(key);
        asNotNull(strNotDisk);
        asEquals(strNotDisk, value);

        //GGCache.cache().remove(key)
        GGCache.memory().remove(key);
        //total removed
        final String strNotExists = GGCache.cache().getString(key);
        asNull(strNotExists);

        Log.d("two layer", "res: " + str);
    }
}
